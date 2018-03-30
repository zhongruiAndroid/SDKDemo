package com.sdklibrary.base.share.qq;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public abstract class QQShareListener implements IUiListener {
    public abstract void doComplete(Object values);
    public abstract void doError(UiError e);
    public abstract void doCancel();
    @Override
    public void onComplete(Object response) {
        doComplete(response);
    }
    @Override
    public void onError(UiError e) {
//			Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
//			Util.dismissDialog();
        doError(e);
    }
    @Override
    public void onCancel() {
        doCancel();
//			Util.toastMessage(MainActivity.this, "onCancel: ");
//			Util.dismissDialog();
//            if (isServerSideLogin) {
//                isServerSideLogin = false;
//            }
    }
}