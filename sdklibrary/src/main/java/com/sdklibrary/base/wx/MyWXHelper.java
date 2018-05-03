package com.sdklibrary.base.wx;

import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.share.inter.ResponseCallback;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by Administrator on 2018/5/3.
 */

public class MyWXHelper {
    private static MyWXHelper helper;
    private MyWXCallback callback;
    private ResponseCallback responseCallback;

    private MyWXHelper() {
    }

    public static MyWXHelper getInstance() {
        if (helper == null) {
            synchronized (MyWXHelper.class) {
                if (helper == null) {
                    helper = new MyWXHelper();
                }
            }
        }
        return helper;
    }

    public void response(int responseCode,String authCode) {
        if(responseCallback!=null){
            switch (responseCode) {
                //正确返回
                case BaseResp.ErrCode.ERR_OK:
                    responseCallback.onSuccess(authCode);
                    break;
                //取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    responseCallback.onCancel();
                    break;
                default:
                    responseCallback.onFail();
                    break;
            }
        }
    }
    public void response(int responseCode) {
        if (callback != null) {
            switch (responseCode) {
                //正确返回
                case BaseResp.ErrCode.ERR_OK:
                    callback.onSuccess();
                    break;
                //取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    callback.onCancel();
                    break;
                default:
                    callback.onFail();
                    break;
            }
        }
    }

    public MyWXCallback getCallback() {
        return callback;
    }

    public void setCallback(MyWXCallback callback) {
        this.callback = callback;
    }

    public ResponseCallback getResponseCallback() {
        return responseCallback;
    }

    public void setResponseCallback(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }
}
