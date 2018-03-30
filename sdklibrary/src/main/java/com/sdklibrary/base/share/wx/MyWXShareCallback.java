package com.sdklibrary.base.share.wx;

/**
 * Created by Administrator on 2018/3/19.
 */

public interface MyWXShareCallback {
    void shareSuccess();
    void shareFail();
    void shareCancel();
}
