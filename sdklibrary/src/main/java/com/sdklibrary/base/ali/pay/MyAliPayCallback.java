package com.sdklibrary.base.ali.pay;

/**
 * Created by Administrator on 2018/3/19.
 */

public interface MyAliPayCallback {
    void paySuccess(PayResult result);
    void payFail();
    void payCancel();
}
