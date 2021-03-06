package com.sdklibrary.base.wx.pay;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.wx.MyWXHelper;
import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.share.MyWXUtil;
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
    private MyWXOrderBean orderBean;
//    private MyDisposable payDisposable;


    private static String appId;
    private static String mch_id;
    private static String miYao;


    public static String noInstallWXMsg="亲,您还没有安装微信APP哦!";
    public static String notPayMsg ="亲,当前版本不支持微信支付功能!";
    /***
     *
     * @param APPID 应用id
     * @param MCH_ID 商户id
     * @param MIYAO 密钥
     */
    public static void setConfig(String APPID,String MCH_ID,String MIYAO){
        appId=APPID;
        mch_id=MCH_ID;
        miYao=MIYAO;
    }

    private MyWXPay(Context context) {
        mContext=context;
    }
    public static MyWXPay newInstance(Context context) {
        return new MyWXPay(context);
    }
    public void startPay(MyWXOrderBean bean) {
        startPay(bean,null);
    }
    public void startPay(MyWXOrderBean bean, final MyWXCallback callback) {
        if(TextUtils.isEmpty(bean.getAppId())){
            bean.setAppId(appId);
        }
        if(TextUtils.isEmpty(bean.getMch_id())){
            bean.setMch_id(mch_id);
        }
        if(TextUtils.isEmpty(bean.getMiyao())){
            bean.setMiyao(miYao);
        }
        if(bean!=null){
            if(TextUtils.isEmpty(bean.ip)){
                bean.ip = WXUtils.getIP(mContext);
            }
        }
        api = WXAPIFactory.createWXAPI(mContext,bean.getAppId());
        api.registerApp(bean.getAppId());
        orderBean =bean;
        req = new PayReq();
        sb = new StringBuffer();
        if (!api.isWXAppInstalled()) {
            if(!TextUtils.isEmpty(noInstallWXMsg)){
                Toast.makeText(mContext,noInstallWXMsg,Toast.LENGTH_SHORT).show();
            }
            if(callback!=null){
                callback.onFail();
            }
        } else {
           /* if (!api.isWXAppSupportAPI()) {
                if(!TextUtils.isEmpty(notPayMsg)){
                    Toast.makeText(mContext, notPayMsg,Toast.LENGTH_SHORT).show();
                }
                if(callback!=null){
                    callback.onFail();
                }
            } else {*/
                //用sign判断是否是服务器生成微信支付订单号和sign，
                if(!TextUtils.isEmpty(bean.getSign())){
                    requestPayForWebSign(bean,callback);
                }else{
                    PayAsyncTask payAsyncTask=new PayAsyncTask(callback);
                    payAsyncTask.execute();
                }
//            }
        }
    }
    /**
     * 生成支付参数 requestPay(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
     *
     * @throws
     * @since 1.0.0
     */
    private void requestPayForWebSign(MyWXOrderBean bean, final MyWXCallback callback) {
        req.appId =bean.getAppId();
        req.partnerId =bean.getMch_id();
        req.prepayId = bean.getPrepayId();
        req.packageValue = bean.getPackageValue();
        req.nonceStr = bean.getNonceStr();
        req.timeStamp =bean.getTimeStamp();
        req.sign =bean.getSign();
        sb.append("sign\n" + req.sign + "\n\n");
        MyWXHelper.getInstance().setCallback(callback);
        api.sendReq(req);
    }
    private void requestPay(String prepayid,MyWXCallback callback) {
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

        MyWXHelper.getInstance().setCallback(callback);

        api.sendReq(req);
        Log.e("orion_genPayReq", signParams.toString());

    }

    public class PayAsyncTask extends AsyncTask<MyAliOrderBean,Void,Map<String, String>> {
        private MyWXCallback callback;
        public PayAsyncTask(MyWXCallback myAliPayCallback) {
            this.callback=myAliPayCallback;
        }
        @Override
        protected Map<String, String> doInBackground(MyAliOrderBean... orderBeen) {
            try {
                String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
                String entity = genProductArgs();
                byte[] buf = MyWXUtil.httpPost(url, entity);
                String content = new String(buf);
                Log.e("WXTag", content);
                Map<String, String> xml = decodeXml(content);
                return xml;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            super.onPostExecute(result);
            if(result==null){
                //支付失败
                callback.onFail();
                return;
            }
            String returnCode=result.get("return_code");//返回状态码 SUCCESS/FAIL
            String returnMsg =result.get("return_msg");  //返回信息 OK/异常信息
            String resultCode=result.get("result_code");//结果状态码 SUCCESS/FAIL

            if("SUCCESS".equalsIgnoreCase(returnCode)&&"SUCCESS".equalsIgnoreCase(resultCode)){
                Log.e("WXTag", "生成预支付订单:" + result.get("prepay_id"));
                sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
                requestPay(result.get("prepay_id"),callback);
            }else if("FAIL".equalsIgnoreCase(returnCode)){
                Log.e("WXTag","微信支付返回结果:"+returnMsg);
                if(callback!=null){
                    callback.onFail();
                }
            }else{
                String err_code_des=result.get("err_code_des");//错误代码描述
                Log.e("WXTag","微信支付返回结果:"+err_code_des);
                if(callback!=null){
                    callback.onFail();
                }
            }

        }
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
        return MyWXUtil.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
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
        String appSign = MyWXUtil.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion_genAppSign", appSign);
        return appSign;
    }
    private double getPrice(double num){
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
            packageParams.add(new BasicNameValuePair("spbill_create_ip", orderBean.ip));
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
        return MyWXUtil.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
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
        String packageSign = MyWXUtil.getMessageDigest(sb.toString().getBytes()).toUpperCase();
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
    private Map<String, String> decodeXml(String content) {
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
