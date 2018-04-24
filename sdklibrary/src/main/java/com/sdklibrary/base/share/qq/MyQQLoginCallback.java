package com.sdklibrary.base.share.qq;

import com.sdklibrary.base.share.qq.bean.MyQQUserInfo;

public interface MyQQLoginCallback {
    void loginSuccess(MyQQUserInfo userInfo);
    void loginFail();
    void loginCancel();
}