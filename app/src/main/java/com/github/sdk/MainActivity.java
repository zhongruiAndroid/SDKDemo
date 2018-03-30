package com.github.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sdklibrary.base.share.ShareParam;
import com.sdklibrary.base.share.wx.MyWXShare;
import com.sdklibrary.base.share.wx.MyWXShareCallback;
import com.sdklibrary.base.share.wx.WXShareHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_wx_friend_share,
            tv_wx_circle_share,
            tv_wx_login,
            tv_qq_friend_share,
            tv_qq_qzone_share,
            tv_qq_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wx_friend_share:
                //微信好友分享
                WXShareHelper.WebHelperWX helper=new WXShareHelper.VideoHelperWX(ShareParam.friend);
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
                break;
            case R.id.tv_wx_circle_share:
                //微信朋友圈分享

                break;
            case R.id.tv_wx_login:
                //微信登录

                break;
            case R.id.tv_qq_friend_share:
                //QQ好友分享

                break;
            case R.id.tv_qq_qzone_share:
                //QQ空间分享
                break;
            case R.id.tv_qq_login:
                //QQ登录
                break;
        }
    }
}
