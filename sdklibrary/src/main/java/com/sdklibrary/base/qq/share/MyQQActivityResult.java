package com.sdklibrary.base.qq.share;

import android.content.Intent;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyQQActivityResult {
    public static void onActivityResult(int requestCode, int resultCode, Intent data){
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
            Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
                @Override
                public void onComplete(Object o) {

                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }
    }
}
