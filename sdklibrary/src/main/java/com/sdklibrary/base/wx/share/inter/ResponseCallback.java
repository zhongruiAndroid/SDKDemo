package com.sdklibrary.base.wx.share.inter;

/**
 * Created by Administrator on 2018/5/3.
 */

public interface ResponseCallback {
    void onSuccess(String authCode);
    void onFail();
    void onCancel();
}
