package com.sdklibrary.base.qq.share;

import com.sdklibrary.base.qq.share.bean.MyQQUserInfo;

public interface MyQQLoginCallback {
    void loginSuccess(MyQQUserInfo userInfo);
    void loginFail();
    void loginCancel();
}