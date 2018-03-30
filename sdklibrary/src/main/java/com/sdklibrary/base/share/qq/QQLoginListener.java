package com.sdklibrary.base.share.qq;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public abstract class QQLoginListener implements IUiListener {
    public void doResult(Object response) {
    }
    public abstract void doComplete(String response);
    public abstract void doError(UiError e);
    public abstract void doCancel();
    @Override
    public void onComplete(Object response) {
        doResult(response);
        if (null == response) {
//                Util.showResultDialog(MainActivity.this, "返回为空", "登录失败");
            doError(null);
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
//                Util.showResultDialog(MainActivity.this, "返回为空", "登录失败");
            doError(null);
            return;
        }
//			Util.showResultDialog(MainActivity.this, response.toString(), "登录成功");
        // 有奖分享处理
//            handlePrizeShare();
        doComplete(response.toString());
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