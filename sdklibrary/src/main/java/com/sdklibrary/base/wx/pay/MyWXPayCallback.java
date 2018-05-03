package com.sdklibrary.base.wx.pay;

/**
 * Created by Administrator on 2018/3/19.
 */

public interface MyWXPayCallback {
    void paySuccess();
    void payFail();
    void payCancel();
}
