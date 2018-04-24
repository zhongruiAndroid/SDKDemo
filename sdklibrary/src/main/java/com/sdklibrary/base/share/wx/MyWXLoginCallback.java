package com.sdklibrary.base.share.wx;

/**
 * Created by Administrator on 2018/3/19.
 */

public interface MyWXLoginCallback {
    void loginSuccess(MyWXUserInfo userInfo);
    void loginFail();
    void loginCancel();
}
