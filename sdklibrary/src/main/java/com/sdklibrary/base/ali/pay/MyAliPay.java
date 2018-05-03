package com.sdklibrary.base.ali.pay;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;


/**
 * Created by administartor on 2017/9/5.
 */

public class MyAliPay {
    private Activity mContext;
    private static String appId;
    private static String pid;//商户id
    private static String siYao;//私钥

    /***
     *
     * @param APPID 应用id
     * @param PID 商户id
     * @param SIYAO 私钥
     */
    public static void setConfig(String APPID,String PID,String SIYAO){
        appId=APPID;
        pid=PID;
        siYao=SIYAO;
    }
    private MyAliPay(Activity context) {
        this.mContext=context;
    }
    public static MyAliPay newInstance(Activity context) {
        return new MyAliPay(context);
    }
    public void startPay(final MyAliOrderBean myAliOrderBean, final MyAliPayCallback callback) {
        asyncTaskStart(myAliOrderBean,callback);
    }
    private void rxStart(final MyAliOrderBean myAliOrderBean, final MyAliPayCallback callback){
        /*MyRx.start(new MyFlowableSubscriber<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Map<String, String>> flowableEmitter) {
                final String orderInfo;
                if(TextUtils.isEmpty(myAliOrderBean.getAppId())){
                    myAliOrderBean.setAppId(appId);
                }
                if(TextUtils.isEmpty(myAliOrderBean.getPid())){
                    myAliOrderBean.setPid(pid);
                }
                if(TextUtils.isEmpty(myAliOrderBean.getSiYao())){
                    myAliOrderBean.setSiYao(siYao);
                }
                //本地生成orderInfo
                if(TextUtils.isEmpty(myAliOrderBean.getOrderInfo())){
                    Map<String, String> aliMap = OrderInfoUtil2_0.buildOrderParamMap(myAliOrderBean);
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(aliMap);
                    String sign = OrderInfoUtil2_0.getSign(aliMap, myAliOrderBean.getSiYao(), true);
                    orderInfo = orderParam + "&" + sign;
                }else{
                    //服务器生成的orderInfo
                    orderInfo= myAliOrderBean.getOrderInfo();
                }
                PayTask payTask = new PayTask(mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("onPreExecute=====", result.toString());
                flowableEmitter.onNext(result);
                flowableEmitter.onComplete();
            }

            @Override
            public void onNext(Map<String, String> result) {
                PayResult payResult = new PayResult(result);
                *//**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 *//*
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    //支付成功
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    callback.paySuccess(payResult);
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    //支付取消
                    callback.payCancel();
                } else {
                    //支付失败
                    callback.payFail();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                callback.payFail();
            }
        });*/
    }
    private void asyncTaskStart(final MyAliOrderBean myAliOrderBean, final MyAliPayCallback callback){
        PayAsyncTask payAsyncTask=new PayAsyncTask(callback);
        payAsyncTask.execute(myAliOrderBean);
    }
    public class PayAsyncTask extends AsyncTask<MyAliOrderBean,Void,Map<String, String>> {
        private MyAliPayCallback callback;
        public PayAsyncTask(MyAliPayCallback myAliPayCallback) {
            this.callback=myAliPayCallback;
        }
        @Override
        protected Map<String, String> doInBackground(MyAliOrderBean... orderBeen) {
            try {
                MyAliOrderBean myAliOrderBean=orderBeen[0];
                final String orderInfo;
                if(TextUtils.isEmpty(myAliOrderBean.getAppId())){
                    myAliOrderBean.setAppId(appId);
                }
                if(TextUtils.isEmpty(myAliOrderBean.getPid())){
                    myAliOrderBean.setPid(pid);
                }
                if(TextUtils.isEmpty(myAliOrderBean.getSiYao())){
                    myAliOrderBean.setSiYao(siYao);
                }
                //本地生成orderInfo
                if(TextUtils.isEmpty(myAliOrderBean.getOrderInfo())){
                    Map<String, String> aliMap = OrderInfoUtil2_0.buildOrderParamMap(myAliOrderBean);
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(aliMap);
                    String sign = OrderInfoUtil2_0.getSign(aliMap, myAliOrderBean.getSiYao(), true);
                    orderInfo = orderParam + "&" + sign;
                }else{
                    //服务器生成的orderInfo
                    orderInfo= myAliOrderBean.getOrderInfo();
                }
                PayTask payTask = new PayTask(mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("onPreExecute=====", result.toString());
                return result;
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
                callback.payFail();
                return;
            }
            PayResult payResult = new PayResult(result);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                //支付成功
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                callback.paySuccess(payResult);
            } else if (TextUtils.equals(resultStatus, "6001")) {
                //支付取消
                callback.payCancel();
            } else {
                //支付失败
                callback.payFail();
            }
        }
    }
}
