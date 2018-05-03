package com.sdklibrary.base.wx.pay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sdklibrary.base.wx.MyWXHelper;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public abstract class MyWXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    protected abstract int getContentView();
    protected abstract void initView();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/
        if(getContentView()!=0){
            setContentView(getContentView());
        }
        api = WXAPIFactory.createWXAPI(this, MyWXShare.getAppId());
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode){
                case BaseResp.ErrCode.ERR_OK:
                    //支付成功
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //充值已取消
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    //充值失败
                    break;
            }
            MyWXHelper.getInstance().response(resp.errCode);
//            MyRxBus.getInstance().post(new WXEvent(WXEvent.wx_pay,resp.errCode));
        }
		finish();
    }
}