package com.github.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sdklibrary.base.ShareParam;
import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.ali.pay.MyAliPayCallback;
import com.sdklibrary.base.ali.pay.PayResult;
import com.sdklibrary.base.qq.share.MyQQActivityResult;
import com.sdklibrary.base.qq.share.MyQQLoginCallback;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.qq.share.MyQQShareListener;
import com.sdklibrary.base.qq.share.bean.MyQQAppHelper;
import com.sdklibrary.base.qq.share.bean.MyQQAudioHelper;
import com.sdklibrary.base.qq.share.bean.MyQQImageHelper;
import com.sdklibrary.base.qq.share.bean.MyQQUserInfo;
import com.sdklibrary.base.qq.share.bean.MyQQWebHelper;
import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.pay.MyWXOrderBean;
import com.sdklibrary.base.wx.pay.MyWXPay;
import com.sdklibrary.base.wx.share.MyWXLoginCallback;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.sdklibrary.base.wx.share.MyWXUserInfo;
import com.sdklibrary.base.wx.share.bean.MyWXImageHelper;
import com.sdklibrary.base.wx.share.bean.MyWXTextHelper;
import com.sdklibrary.base.wx.share.bean.MyWXVideoHelper;
import com.sdklibrary.base.wx.share.bean.MyWXWebHelper;
import com.tencent.tauth.UiError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_wx_friend_share,
            tv_wx_circle_share,
            tv_wx_login,
            tv_qq_friend_share,
            tv_qq_qzone_share,
            tv_qq_login;
    public static final String weixing_id = "";
    public static final String weixing_AppSecret = "";
    public static final String qq_id = "1106315352";
    public static final String qq_key = "xyTTuuJGORswRS6I";
    private MyQQWebHelper qqHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*建议放在application中初始化,secret用于微信登录,不集成登录可传null*/
        MyWXShare.setAppId(weixing_id,weixing_AppSecret);
        MyQQShare.setAppId(qq_id);

        initView();

        MyAliOrderBean bean;
    }

    private void initView() {
        tv_wx_friend_share = (TextView) findViewById(R.id.tv_wx_friend_share);
        tv_wx_friend_share.setOnClickListener(this);

        tv_wx_circle_share = (TextView) findViewById(R.id.tv_wx_circle_share);
        tv_wx_circle_share.setOnClickListener(this);

        tv_wx_login = (TextView) findViewById(R.id.tv_wx_login);
        tv_wx_login.setOnClickListener(this);

        tv_qq_friend_share = (TextView) findViewById(R.id.tv_qq_friend_share);
        tv_qq_friend_share.setOnClickListener(this);

        tv_qq_qzone_share = (TextView) findViewById(R.id.tv_qq_qzone_share);
        tv_qq_qzone_share.setOnClickListener(this);

        tv_qq_login = (TextView) findViewById(R.id.tv_qq_login);
        tv_qq_login.setOnClickListener(this);
    }


    /*如果微信分享、支付、登录不成功，请仔细检查相关配置(微信开放平台是否配置相关信息,应用包名、应用签名是否配置正确)和app是否进行签名*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wx_friend_share:
                //好友分享
                if(TextUtils.isEmpty(weixing_id)){
                    Toast.makeText(this,"请填写自己的微信appid",Toast.LENGTH_SHORT).show();
                    return;
                }

                shareToWX(ShareParam.friend);
                break;
            case R.id.tv_wx_circle_share:
                //微信朋友圈分享
                if(TextUtils.isEmpty(weixing_id)){
                    Toast.makeText(this,"请填写自己的微信appid",Toast.LENGTH_SHORT).show();
                    return;
                }

                shareToWX(ShareParam.friendCircle);
                break;
            case R.id.tv_wx_login:
                //微信登录
                if(TextUtils.isEmpty(weixing_id)||TextUtils.isEmpty(weixing_AppSecret)){
                    Toast.makeText(this,"请填写自己的微信appid和appsecret",Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
            case R.id.tv_qq_friend_share:
                //QQ好友分享
                qqHelper=new MyQQWebHelper(ShareParam.QQ);
                qqHelper.setTitle("QQ分享标题");
                qqHelper.setDescription("QQ分享内容");
                qqHelper.setUrl("http://47.92.28.70:1800/index.html");
                qqHelper.setImagePath("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=4b0d814ddf3f8794c7f2407cb3726591/6c224f4a20a44623d0bfc7979322720e0df3d7ca.jpg");

                MyQQShare.newInstance(this).shareWeb(qqHelper, new MyQQShareListener() {
                    @Override
                    public void doComplete(Object values) {
                        Log.i("===","==="+values);
                    }
                    @Override
                    public void doError(UiError e) {
                        Log.i("===","===doError");
                    }
                    @Override
                    public void doCancel() {
                        Log.i("===","===doCancel");
                    }
                });
                break;
            case R.id.tv_qq_qzone_share:
                //QQ空间分享
                qqHelper = new MyQQWebHelper(ShareParam.QZONE);
                qqHelper.setTitle("QQ分享标题");
                qqHelper.setDescription("QQ分享内容");
                qqHelper.setUrl("http://47.92.28.70:1800/index.html");
                qqHelper.setImagePath("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=4b0d814ddf3f8794c7f2407cb3726591/6c224f4a20a44623d0bfc7979322720e0df3d7ca.jpg");

                MyQQShare.newInstance(this).shareWeb(qqHelper, new MyQQShareListener() {
                    @Override
                    public void doComplete(Object values) {
                        Log.i("===","==="+values);
                    }
                    @Override
                    public void doError(UiError e) {
                        Log.i("===","===doError");
                    }
                    @Override
                    public void doCancel() {
                        Log.i("===","===doCancel");
                    }
                });
                break;
            case R.id.tv_qq_login:
                //QQ登录
                MyQQShare.newInstance(this).login(new MyQQLoginCallback() {
                    @Override
                    public void loginSuccess(MyQQUserInfo userInfo) {
                        Log.i("===","==="+userInfo.toString());
                    }
                    @Override
                    public void loginFail() {
                        Log.i("===","===loginFail");

                    }
                    @Override
                    public void loginCancel() {
                        Log.i("===","===loginCancel");

                    }
                });
                break;
        }
    }

    private void shareToWX(int scene) {
        //微信好友分享
        MyWXVideoHelper helper=new MyWXVideoHelper(scene);
        helper.setBitmap(null);
        helper.setBitmapResId(R.mipmap.ic_launcher);
        helper.setUrl("www.baidu.com");
        helper.setTitle("分享标题");
        helper.setDescription("分享内容");
        MyWXShare.newInstance(this).shareWeb(helper, new MyWXCallback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onFail() {

            }
            @Override
            public void onCancel() {
                MyAliOrderBean a=new MyAliOrderBean();
                MyWXOrderBean b=new MyWXOrderBean();

/*如果getOrderInfo()null,否则属于app本地生成订单信息*/
                MyAliPay.newInstance(null).startPay(null, new MyAliPayCallback() {
                    @Override
                    public void paySuccess(PayResult result) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //支付成功
                    }
                    @Override
                    public void payFail() {
                        //支付失败
                    }
                    @Override
                    public void payCancel() {
                        //支付取消
                    }
                });
                MyWXPay.newInstance(null).startPay(null, new MyWXCallback() {
                    @Override
                    public void onSuccess() {
                        //支付成功
                    }
                    @Override
                    public void onFail() {
                        //支付失败
                    }
                    @Override
                    public void onCancel() {
                        //支付取消
                    }
                });
                
            }
        });
    }


    /*public void a(){
        MyWXOrderBean wxOrderBean=new MyWXOrderBean();
        wxOrderBean.setAppId(appId);
        wxOrderBean.setMch_id(mch_id);
        wxOrderBean.setMiyao(miyao);

        wxOrderBean.setPrepayId(prepayId);
        wxOrderBean.setSign(sign);
        //packageValue默认为Sign=WXPay
        wxOrderBean.setPackageValue(packageValue);
        wxOrderBean.setTimeStamp(timeStamp);
        *//*prepayId
sign
packageValue
timeStamp*//*

        wxOrderBean.setNotifyUrl(NotifyUrl);
        wxOrderBean.setBody(Body);
        wxOrderBean.setOut_trade_no(Out_trade_no);
        //单位：分
        wxOrderBean.setTotalFee(TotalFee);
        //自动生成,可以不用赋值
        wxOrderBean.ip="设备ip地址";
        //自动生成,可以不用赋值
        wxOrderBean.nonceStr="随机数";
        MyWXPay.newInstance(this).startPay(wxOrderBean);
        MyWXPay.newInstance(this).startPay(wxOrderBean, new MyWXPayCallback() {
            @Override
            public void paySuccess() {
                //支付成功
            }
            @Override
            public void payFail() {
                //支付失败
            }
            @Override
            public void payCancel() {
                //支付取消
            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //qq分享配置
        MyQQActivityResult.onActivityResult(requestCode, resultCode, data);
    }


    public void text(){
        MyWXTextHelper helper=new MyWXTextHelper(ShareParam.friend);
        helper.setTitle("文本标题");
        helper.setText("文本内容");
        helper.setDescription("文本描述");
        MyWXShare.newInstance(this).shareText(helper,null);
    }
    public void img(){
        MyWXImageHelper helper=new MyWXImageHelper(ShareParam.friend);
        helper.setBitmapResId(R.drawable.textclear);
        helper.setBitmap(null);
        helper.setDstWidth(150);
        helper.setDstHeight(150);
    }
    public void qqLogin(){
        MyQQShare.newInstance(this).isInstall();
        MyQQShare.newInstance(this).login(new MyQQLoginCallback() {
            @Override
            public void loginSuccess(MyQQUserInfo userInfo) {
                //登录成功
            }
            @Override
            public void loginFail() {
                //登录失败
            }
            @Override
            public void loginCancel() {
                //取消登录
            }
        });
    }
    public void qqShareApp(){
        MyQQAppHelper helper=new MyQQAppHelper();
        helper.setTitle("");
        helper.setDescription("");
        helper.setImagePath("");
        helper.setAppName("");
        helper.setArkJson("");
        MyQQShare.newInstance(this).shareApp(helper, new MyQQShareListener() {
            @Override
            public void doComplete(Object o) {
                //分享成功
            }
            @Override
            public void doError(UiError uiError) {
                //分享失败
            }
            @Override
            public void doCancel() {
                //取消分享
            }
        });
    }
    public void qqShareAudio(){
        MyQQAudioHelper helper=new MyQQAudioHelper();
        helper.setTitle("");
        helper.setDescription("");
        helper.setUrl("");
        helper.setAppName("");
        helper.setAudioUrl("");
        helper.setImagePath("");
        MyQQShare.newInstance(this).shareAudio(helper, new MyQQShareListener() {
            @Override
            public void doComplete(Object o) {
                //分享成功
            }
            @Override
            public void doError(UiError uiError) {
                //分享失败
            }
            @Override
            public void doCancel() {
                //取消分享
            }
        });
    }
    public void qqShareImg(){
        MyQQImageHelper helper=new MyQQImageHelper();
        helper.setImagePath(null);
        MyQQShare.newInstance(this).shareImage(helper, new MyQQShareListener() {
            @Override
            public void doComplete(Object o) {
                //分享成功
            }
            @Override
            public void doError(UiError uiError) {
                //分享失败
            }
            @Override
            public void doCancel() {
                //取消分享
            }
        });
    }
    public void qqShare(){
        MyQQShare.newInstance(this).getTencent();
        MyQQWebHelper helper=new MyQQWebHelper(ShareParam.QZONE);
        helper.setTitle("");
        helper.setDescription("");
        helper.setImagePath("");
        helper.setUrl("");
        MyQQShare.newInstance(this).shareWeb(helper, new MyQQShareListener() {
            @Override
            public void doComplete(Object o) {
                //分享成功
            }
            @Override
            public void doError(UiError uiError) {
                //分享失败
            }
            @Override
            public void doCancel() {
                //取消分享
            }
        });
    }
    public void wxLogin(){
        MyWXShare.newInstance(this).login(new MyWXLoginCallback() {
            @Override
            public void loginSuccess(MyWXUserInfo userInfo) {

            }
            @Override
            public void loginFail() {

            }
            @Override
            public void loginCancel() {

            }
        });
    }
    public void a(){
//        MyWXVideoHelper helper=new MyWXVideoHelper(ShareParam.friend);
//        helper.setUrl("");

        MyWXWebHelper helper=new MyWXVideoHelper(ShareParam.friend);
        helper.setUrl("网页url");
        helper.setTitle("网页标题");
        helper.setDescription("网页描述");
        helper.setBitmap(null);
        MyWXShare.newInstance(this).shareAudio(helper, new MyWXCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onCancel() {

            }
        });

        MyWXShare.newInstance(this).shareWeb(helper,  new MyWXCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

}
