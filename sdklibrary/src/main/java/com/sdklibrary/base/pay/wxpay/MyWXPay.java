package com.sdklibrary.base.pay.wxpay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.github.rxbus.MyConsumer;
import com.github.rxbus.MyDisposable;
import com.github.rxbus.MyRxBus;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.MyRx;
import com.sdklibrary.base.WXEvent;
import com.sdklibrary.base.share.wx.Util;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import io.reactivex.FlowableEmitter;

public class MyWXPay {
    /*
    *
    * <activity
            android:name=".wxapi.MyWXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme.NoActionBar" />
        <activity android:name=".wxapi.MyWXPayEntryActivity">
            <intent-filter>
                <action android:name="android.intent.action.VIEW"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:scheme="wx84ef2f811b19ad6b"/>
            </intent-filter>
        </activity>
    *
    * */
    private Context mContext;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private PayReq req;
    private static IWXAPI api;
//    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;
    private WXOrderBean orderBean;
    private MyDisposable payDisposable;


    private static String appId;
    private static String miYao;
    private static String mch_id;
    /***
     *
     * @param APPID 应用id
     * @param MIYAO 商户id
     * @param MCH_ID 密钥
     */
    public static void setConfig(String APPID,String MIYAO,String MCH_ID){
        appId=APPID;
        miYao=MIYAO;
        mch_id=MCH_ID;
    }

    private MyWXPay(Context context) {
        mContext=context;
    }
    public static MyWXPay newInstance(Context context) {
        return new MyWXPay(context);
    }
    public void startPay(WXOrderBean bean) {
        startPay(bean,null);
    }
    public void startPay(WXOrderBean bean,final MyWXPayCallback callback) {
        if(TextUtils.isEmpty(bean.getAppId())){
            bean.setAppId(appId);
        }
        if(TextUtils.isEmpty(bean.getMiyao())){
            bean.setMiyao(miYao);
        }
        if(TextUtils.isEmpty(bean.getMch_id())){
            bean.setMch_id(mch_id);
        }
        api = WXAPIFactory.createWXAPI(mContext,bean.getAppId());
        api.registerApp(bean.getAppId());
        orderBean =bean;
        req = new PayReq();
        sb = new StringBuffer();
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mContext,"亲,您还没有安装微信APP哦!",Toast.LENGTH_SHORT).show();
        } else {
            if (!api.isWXAppSupportAPI()) {
                Toast.makeText(mContext,"亲,当前版本不支持微信支付功能!",Toast.LENGTH_SHORT).show();
            } else {
                //用sign判断是否是服务器生成微信支付订单号和sign，
                if(!TextUtils.isEmpty(bean.getSign())){
                    requestPayForWebSign(bean,callback);
                }else{
                    MyRx.start(new MyFlowableSubscriber<Map<String, String>>() {
                        @Override
                        public void subscribe(@NonNull FlowableEmitter<Map<String, String>> flowableEmitter) {
                            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
                            String entity = genProductArgs();
                            byte[] buf = Util.httpPost(url, entity);
                            String content = new String(buf);
                            Log.e("orion", content);
                            Map<String, String> xml = decodeXml(content);
                            flowableEmitter.onNext(xml);
                            flowableEmitter.onComplete();
                        }
                        @Override
                        public void onNext(Map<String, String> result) {
                            if(result.get("return_code")!=null&&"FAIL".equals(result.get("return_code"))){
                                Toast.makeText(mContext,"微信支付订单生成失败",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(result.get("err_code_des")!=null){
                                Toast.makeText(mContext,result.get("err_code_des"),Toast.LENGTH_SHORT).show();
                            }
                            Log.e("Tag", "生成预支付订单:" + result.get("prepay_id"));
                            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
                            requestPay(result.get("prepay_id"),callback);
                        }
                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            if(callback!=null){
                                callback.payFail();
                            }
                        }
                    });
                }
            }
        }
    }
    /**
     * 生成支付参数 requestPay(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
     *
     * @throws
     * @since 1.0.0
     */
    private void requestPayForWebSign(WXOrderBean bean, final MyWXPayCallback callback) {
        req.appId =bean.getAppId();
        req.partnerId =bean.getMch_id();
        req.prepayId = bean.getPrepayId();
        req.packageValue = bean.getPackageValue();
        req.nonceStr = bean.getNonceStr();
        req.timeStamp =bean.getTimeStamp();
        req.sign =bean.getSign();
        sb.append("sign\n" + req.sign + "\n\n");
        payDisposable = MyRxBus.getInstance().getEvent(WXEvent.class, new MyConsumer<WXEvent>() {
            @Override
            public void onAccept(WXEvent wxEvent) {
                if(wxEvent.flag==WXEvent.wx_pay){
                    switch (wxEvent.responseCode) {
                        //正确返回
                        case BaseResp.ErrCode.ERR_OK:
                            callback.paySuccess();
                            break;
                        //取消
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            callback.payCancel();
                            break;
                        default:
                            callback.payFail();
                            break;
                    }
                    MyRxBus.getInstance().dispose(payDisposable);
                }
            }
        });
        api.sendReq(req);
    }
    private void requestPay(String prepayid, final MyWXPayCallback callback) {
        req.appId =orderBean.getAppId();
        req.partnerId =orderBean.getMch_id();
        req.prepayId = prepayid;
        req.packageValue = "Sign=MyWXPay";
        req.nonceStr = genNonceStr();
        String timeStamp=String.valueOf(genTimeStamp());
        req.timeStamp = timeStamp;
        Log.i("wxpay===","timeStamp==="+timeStamp);

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
        //正确返回
        //取消
        payDisposable = MyRxBus.getInstance().getEvent(WXEvent.class, new MyConsumer<WXEvent>() {
            @Override
            public void onAccept(WXEvent wxEvent) {
                if(wxEvent.flag==WXEvent.wx_pay){
                    switch (wxEvent.responseCode) {
                        //正确返回
                        case BaseResp.ErrCode.ERR_OK:
                            callback.paySuccess();
                            break;
                        //取消
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            callback.payCancel();
                            break;
                        default:
                            callback.payFail();
                            break;
                    }
                    MyRxBus.getInstance().dispose(payDisposable);
                }
            }
        });

        api.sendReq(req);
        Log.e("orion_genPayReq", signParams.toString());

    }

    /**
     * 生成随机字符串 getNonceStr(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genNonceStr() {
        Random random = new Random();
        return Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(orderBean.getMiyao());

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion_genAppSign", appSign);
        return appSign;
    }
    public double getPrice(double num){
        BigDecimal b = new BigDecimal(num);
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    // ///////////////////////////////二/////////////////////////////////////////
    private String genProductArgs() {
        String outTradeNo=genOutTradNo();
        String notifyUrl = orderBean.getNotifyUrl();
        Log.i("===","notifyUrl==="+notifyUrl);
        Log.i("====","===="+((int)getPrice(orderBean.getTotalFee()))+""+"");
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", orderBean.getAppId()));
            packageParams.add(new BasicNameValuePair("body", orderBean.getBody()));
            packageParams.add(new BasicNameValuePair("mch_id", orderBean.getMch_id()));
            packageParams.add(new BasicNameValuePair("nonce_str", orderBean.nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));
            packageParams.add(new BasicNameValuePair("out_trade_no", orderBean.getOut_trade_no()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", orderBean.IP));
//            if(BuildConfig.DEBUG){
//                packageParams.add(new BasicNameValuePair("total_fee", 1+""));
//            }else{
                packageParams.add(new BasicNameValuePair("total_fee", orderBean.getTotalFee()+""));
//            }
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            // 微信支付金额
             /*  packageParams.add(new BasicNameValuePair("total_fee", "1"));
               PreferenceUtils.setPrefString(mContext, "Sum_money", "0.01");*/
//          PreferenceUtils.setPrefString(mContext, "Sum_money", count_price);

            String sign = genPackageSign(packageParams);
            Log.i("===","sign==="+sign);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            // 改变拼接之后xml字符串格式
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
        } catch (Exception e) {
            Log.e("tag_e", "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取商户订单号 genOutTradNo(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genOutTradNo() {
        Random random = new Random();
        return Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名 genPackageSign(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param params
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(orderBean.getMiyao());
        Log.e("url", sb.toString());
        String packageSign = Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion_genPackageSign", packageSign);
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        Log.e("orion_toXml", sb.toString());
        return sb.toString();
    }

    // //////////////////////////////////////////////////////////////////////////////
    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion_decodeXml_e", e.toString());
        }
        return null;
    }
}
