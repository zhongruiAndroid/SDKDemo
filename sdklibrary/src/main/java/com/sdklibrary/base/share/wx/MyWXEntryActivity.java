package com.sdklibrary.base.share.wx;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.rxbus.MyRxBus;
import com.sdklibrary.base.WXEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public abstract class MyWXEntryActivity extends Activity implements IWXAPIEventHandler {

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
    public void onResp(BaseResp resp) { //在这个方法中处理微信传回的数据
        //微信分享操作
        if(resp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//分享
            Log.i("onResp","微信分享操作.....");
            MyRxBus.getInstance().post(new WXEvent(WXEvent.wx_share,resp.errCode));
            switch (resp.errCode) { //根据需要的情况进行处理
                case BaseResp.ErrCode.ERR_OK:
                    //正确返回
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //用户取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //认证被否决
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    //发送失败
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    //不支持错误
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    //一般错误
                    break;
                default:
                    //其他不可名状的情况
                    break;
            }
        }else if(resp.getType()== ConstantsAPI.COMMAND_SENDAUTH){//登陆
            Log.i("onResp","微信登陆操作.....");
//            MyRxBus.getInstance().post(new WXEvent(WXEvent.wx_login,resp.errCode,resp));
            int respCode=resp.errCode;
            String authCode=null;
            // 微信登陆操作
            switch (resp.errCode) { //根据需要的情况进行处理
                case BaseResp.ErrCode.ERR_OK:
                    //正确返回
                    SendAuth.Resp authResp = (SendAuth.Resp) resp;
                    authCode = authResp.code;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //用户取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //认证被否决
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    //发送失败
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    //不支持错误
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    //一般错误
                    break;
                default:
                    //其他不可名状的情况
                    break;
            }
            MyRxBus.getInstance().post(new WXEvent.WXLoginEvent(respCode,authCode));
        }
//        Log.i("===","1onResp==="+resp.transaction);
//        Log.i("===","2onResp==="+resp.getType());
//        Log.i("===","3onResp==="+resp.openId);
        //形参resp 有下面两个个属性比较重要
        //1.resp.errCode
        //2.resp.transaction则是在分享数据的时候手动指定的字符创,用来分辨是那次分享(参照4.中req.transaction)
        finish();
    }

    @Override
    public void onReq(BaseReq req) {
//        Log.i("===","onReq1==="+req.transaction);
//        Log.i("===","onReq2==="+req.getType());
//        Log.i("===","onReq3==="+req.openId);
        //......这里是用来处理接收的请求,暂不做讨论
    }

}

