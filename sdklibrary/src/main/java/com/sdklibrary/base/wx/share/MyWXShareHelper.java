package com.sdklibrary.base.wx.share;

import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by Administrator on 2018/5/3.
 */

public class MyWXShareHelper {
    private static MyWXShareHelper helper;
    private MyWXShareCallback callback;

    private MyWXShareHelper() {
    }

    public static MyWXShareHelper getInstance() {
        if (helper == null) {
            synchronized (MyWXShareHelper.class) {
                if (helper == null) {
                    helper = new MyWXShareHelper();
                }
            }
        }
        return helper;
    }

    public void response(int responseCode) {
        if (callback != null) {
            switch (responseCode) {
                //正确返回
                case BaseResp.ErrCode.ERR_OK:
                    callback.shareSuccess();
                    break;
                //取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    callback.shareCancel();
                    break;
                default:
                    callback.shareFail();
                    break;
            }
        }
    }

    public MyWXShareCallback getCallback() {
        return callback;
    }

    public void setCallback(MyWXShareCallback callback) {
        this.callback = callback;
    }
}
