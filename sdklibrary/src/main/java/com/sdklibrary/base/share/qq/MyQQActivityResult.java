package com.sdklibrary.base.share.qq;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyQQActivityResult {
    public static void a(){
        //qq分享配置
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new MyQQShareListener() {
                @Override
                public void doComplete(Object values) {
                }
                @Override
                public void doError(UiError e) {
                }
                @Override
                public void doCancel() {
                }
            });
        }
        //qq登录配置
        if (requestCode == Constants.REQUEST_LOGIN ||requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new MyQQLoginListener() {
                @Override
                public void doComplete(String response) {
                }
                @Override
                public void doError(UiError e) {
                }
                @Override
                public void doCancel() {
                }
            });
        }
    }
}
