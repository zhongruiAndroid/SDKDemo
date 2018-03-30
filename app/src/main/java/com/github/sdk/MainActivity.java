package com.github.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sdklibrary.base.share.ShareParam;
import com.sdklibrary.base.share.qq.MyQQLoginListener;
import com.sdklibrary.base.share.qq.MyQQShare;
import com.sdklibrary.base.share.qq.MyQQShareListener;
import com.sdklibrary.base.share.qq.QQShareHelper;
import com.sdklibrary.base.share.wx.MyWXShare;
import com.sdklibrary.base.share.wx.MyWXShareCallback;
import com.sdklibrary.base.share.wx.WXShareHelper;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*建议放在application中初始化,secret用于微信登录,不集成登录可传null*/
        MyWXShare.setAppId(weixing_id,weixing_AppSecret);
        MyQQShare.setAppId(qq_id);

        initView();
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
                QQShareHelper.QQWebHelper qqHelper=new QQShareHelper.QQWebHelper(ShareParam.QQ);
                qqHelper.setTitle("QQ分享标题");
                qqHelper.setDescription("QQ分享内容");
                qqHelper.setUrl("www.baidu.com");
                qqHelper.setImageUrl("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=4b0d814ddf3f8794c7f2407cb3726591/6c224f4a20a44623d0bfc7979322720e0df3d7ca.jpg");

                MyQQShare.newInstance(this).shareWeb(qqHelper, new MyQQShareListener() {
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
                break;
            case R.id.tv_qq_qzone_share:
                //QQ空间分享
                break;
            case R.id.tv_qq_login:
                //QQ登录
                break;
        }
    }

    private void shareToWX(int scene) {
        //微信好友分享
        WXShareHelper.WebHelperWX helper=new WXShareHelper.VideoHelperWX(scene);
        helper.setBitmapResId(R.mipmap.ic_launcher);
        helper.setUrl("www.baidu.com");
        helper.setTitle("分享标题");
        helper.setDescription("分享内容");
        MyWXShare.newInstance(this).shareWeb(helper, new MyWXShareCallback() {
            @Override
            public void shareSuccess() {

            }
            @Override
            public void shareFail() {

            }
            @Override
            public void shareCancel() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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