package com.sdklibrary.base.share.qq;

import com.sdklibrary.base.share.qq.bean.QQUserInfo;

public interface MyQQLoginCallback {
    void loginSuccess(QQUserInfo userInfo);
    void loginFail();
    void loginCancel();
}