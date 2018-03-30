package com.sdklibrary.base.pay.alipay;

/**
 * Created by Administrator on 2018/3/8.
 */

public abstract class AliPayCallback {
    public void onPre(){};
    public void endPay(){};
    public abstract void onSuccess(PayResult result);
    public abstract void onFail();
    public abstract void onCancel();
}
