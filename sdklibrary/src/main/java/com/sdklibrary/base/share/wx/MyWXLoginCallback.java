package com.sdklibrary.base.share.wx;

/**
 * Created by Administrator on 2018/3/19.
 */

public interface MyWXLoginCallback {
    void loginSuccess(WXUserInfo userInfo);
    void loginFail();
    void loginCancel();
}
