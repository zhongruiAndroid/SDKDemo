package com.sdklibrary.base.qq.share;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public abstract class MyQQShareListener implements IUiListener {
    public abstract void doComplete(Object values);
    public abstract void doError(UiError e);
    public abstract void doCancel();
    @Override
    public void onComplete(Object response) {
        doComplete(response);
    }
    @Override
    public void onError(UiError e) {
//			MyWXUtil.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
//			MyWXUtil.dismissDialog();
        doError(e);
    }
    @Override
    public void onCancel() {
        doCancel();
//			MyWXUtil.toastMessage(MainActivity.this, "onCancel: ");
//			MyWXUtil.dismissDialog();
//            if (isServerSideLogin) {
//                isServerSideLogin = false;
//            }
    }
}