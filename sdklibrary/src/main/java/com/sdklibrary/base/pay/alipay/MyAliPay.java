package com.sdklibrary.base.pay.alipay;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.MyRx;

import java.util.Map;

import io.reactivex.FlowableEmitter;


/**
 * Created by administartor on 2017/9/5.
 */

public class MyAliPay {
    private Activity mContext;
    private MyAliPay(Activity context) {
        this.mContext=context;
    }
    public static MyAliPay newInstance(Activity context) {
        return new MyAliPay(context);
    }
    public void startPay(final AliPayOrderBean aliPayOrderBean,final MyAliPayCallback callback) {
        MyRx.start(new MyFlowableSubscriber<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Map<String, String>> flowableEmitter) {
                final String orderInfo;
                //本地生成orderInfo
                if(TextUtils.isEmpty(aliPayOrderBean.getOrderInfo())){
                    Map<String, String> aliMap = OrderInfoUtil2_0.buildOrderParamMap(aliPayOrderBean);
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(aliMap);
                    String sign = OrderInfoUtil2_0.getSign(aliMap,aliPayOrderBean.getSiYao(), true);
                    orderInfo = orderParam + "&" + sign;
                }else{
                    //服务器生成的orderInfo
                    orderInfo=aliPayOrderBean.getOrderInfo();
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

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                callback.payFail();
            }
        });
    }

}
