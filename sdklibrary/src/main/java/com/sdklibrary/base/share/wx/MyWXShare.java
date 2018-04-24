package com.sdklibrary.base.share.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;

import com.github.rxbus.MyConsumer;
import com.github.rxbus.MyDisposable;
import com.github.rxbus.MyRxBus;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.MyRx;
import com.google.gson.Gson;
import com.sdklibrary.base.WXEvent;
import com.sdklibrary.base.share.BaseShare;
import com.sdklibrary.base.share.wx.bean.MyWXImageHelper;
import com.sdklibrary.base.share.wx.bean.MyWXTextHelper;
import com.sdklibrary.base.share.wx.bean.MyWXVideoHelper;
import com.sdklibrary.base.share.wx.bean.MyWXWebHelper;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import io.reactivex.FlowableEmitter;

/**
 * Created by Administrator on 2018/3/9.
 */

public class MyWXShare extends BaseShare {
    private IWXAPI api;
    private static String appId;
    private static String appSecret;
    private MyDisposable disposable;
    private MyDisposable loginDisposable;

    public static String getAppId() {
        return appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static void setAppId(String appID, String secret) {
        appId = appID;
        appSecret = secret;
    }

    private MyWXShare(Context context) {
        this(context, null);
    }

    private MyWXShare(Context context, String appId) {
        this.context = context;
        if (!TextUtils.isEmpty(appId)) {
            this.appId = appId;
        }
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, this.appId);
        }
    }

    public static MyWXShare newInstance(Context context) {
        return new MyWXShare(context);
    }

    public static MyWXShare newInstance(Context context, String appId) {
        return new MyWXShare(context, appId);
    }

    /*IWXAPI api
      api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);*/
    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 微信分享视频
     *
     * @param helper
     */
    public void shareVideo(MyWXWebHelper helper) {
        shareVideo(helper,null);
    }
    public void shareVideo(MyWXWebHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        msg.thumbData = MyWXUtil.bmpToByteArray(bmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = helper.getScene();
        subscribeShare(callback);
        api.sendReq(req);
    }

    /**
     * 微信分享视频
     *
     * @param helper
     */
    public void shareVideo(MyWXVideoHelper helper) {
        shareVideo(helper,null);
    }
    public void shareVideo(MyWXVideoHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();
        msg.thumbData = MyWXUtil.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = helper.getScene();
        subscribeShare(callback);
        api.sendReq(req);
    }

    /**
     * 微信分享网页
     *
     * @param helper
     */
    public void shareWeb(MyWXWebHelper helper) {
        shareWeb(helper, null);
    }

    public void shareWeb(MyWXWebHelper helper, final MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = helper.getUrl();

        final WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();
        Bitmap bitmap;
        if (helper.getBitmap() == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bitmap = helper.getBitmap();
        }
        msg.thumbData = MyWXUtil.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webPage");
        req.message = msg;
        /*
        * WXSceneSession = 0;会话
        * WXSceneTimeline = 1;朋友圈
        * WXSceneFavorite = 2;收藏
        * */
        req.scene = helper.getScene();

        subscribeShare(callback);
        api.sendReq(req);
    }

    private void shareCancel(MyWXShareCallback callback) {
        if (callback != null) {
            callback.shareCancel();
        }
    }
    private void shareCancel(MyWXLoginCallback callback) {
        if (callback != null) {
            callback.loginCancel();
        }
    }

    private void subscribeShare(final MyWXShareCallback callback) {
        if (callback != null) {
            //正确返回
            disposable = MyRxBus.getInstance().getEvent(WXEvent.class, new MyConsumer<WXEvent>() {
                @Override
                public void onAccept(WXEvent wxEvent) {
                    if(wxEvent.flag==WXEvent.wx_share){
                        switch (wxEvent.responseCode) {
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
                        MyRxBus.getInstance().dispose(disposable);
                    }
                }
            });
        }
    }

    /**
     * 微信分享音乐
     *
     * @param helper
     */
    public void shareAudio(MyWXWebHelper helper) {
        shareAudio(helper,null);
    }
    public void shareAudio(MyWXWebHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();

        Bitmap bitmap;
        if (helper.getBitmap() == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bitmap = helper.getBitmap();
        }

        msg.thumbData = MyWXUtil.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = helper.getScene();
        subscribeShare(callback);
        api.sendReq(req);
    }

    /**
     * 微信分享音乐
     *
     * @param helper
     */
    public void shareAudio(MyWXVideoHelper helper) {
        shareAudio(helper,null);
    }
    public void shareAudio(MyWXVideoHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = helper.getUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = helper.getTitle();
        msg.description = helper.getDescription();

        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();

        msg.thumbData = MyWXUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = helper.getScene();

        subscribeShare(callback);
        api.sendReq(req);
    }

    /**
     * 微信分享图片
     *
     * @param helper
     */
    public void shareImage(MyWXImageHelper helper) {
        shareImage(helper,null);
    }
    public void shareImage(MyWXImageHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        Bitmap bmp;
        if (helper.getBitmap() == null) {
            bmp = BitmapFactory.decodeResource(context.getResources(), helper.getBitmapResId());
        } else {
            bmp = helper.getBitmap();
        }
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, helper.getDstWidth(), helper.getDstHeight(), true);
        bmp.recycle();
        msg.thumbData = MyWXUtil.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("image");
        req.message = msg;
        req.scene = helper.getScene();
        subscribeShare(callback);
        api.sendReq(req);
    }

    /**
     * 微信分享文本
     *
     * @param helper
     */
    public void shareText(MyWXTextHelper helper) {
        shareText(helper,null);

    }
    public void shareText(MyWXTextHelper helper, MyWXShareCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        WXTextObject textObj = new WXTextObject();
        textObj.text = helper.getText();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;

        if (helper.getTitle() != null) {
            msg.title = helper.getTitle();
        }
        msg.description = helper.getDescription();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = helper.getScene();

        subscribeShare(callback);
        api.sendReq(req);
    }

    public boolean isInstall() {
        return api.isWXAppInstalled();
    }

    private boolean notShare(Context context, IWXAPI api) {
        /* boolean b = api.registerApp(app_id);
        if(b==false){
            Toast.makeText(mContext,"注册微信失败，无法打开微信!",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        boolean notShare = false;
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "亲,您还没有安装微信APP哦!", Toast.LENGTH_SHORT).show();
            notShare = true;
        } else {
            if (!api.isWXAppSupportAPI()) {
                Toast.makeText(context, "亲,当前版本不支持微信相关功能!", Toast.LENGTH_SHORT).show();
                notShare = true;
            }
        }
        return notShare;
    }

    /*******************************************************登录***********************************************************************/

    public void login() {
        login(null);
    }
    public void login(final MyWXLoginCallback callback) {
        if (notShare(context, api)) {
            shareCancel(callback);
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk";
        if (callback != null) {
            //正确返回
            loginDisposable = MyRxBus.getInstance().getEvent(WXEvent.WXLoginEvent.class, new MyConsumer<WXEvent.WXLoginEvent>() {
                @Override
                public void onAccept(WXEvent.WXLoginEvent wxEvent) {
                        switch (wxEvent.responseCode) {
                            //正确返回
                            case BaseResp.ErrCode.ERR_OK:
                                getUserInfo(wxEvent.authCode,callback);
                                break;
                            //取消
                            case BaseResp.ErrCode.ERR_USER_CANCEL:
                                callback.loginCancel();
                                break;
                            default:
                                callback.loginFail();
                                break;
                        }
                        MyRxBus.getInstance().dispose(loginDisposable);
                }
            });
        }
        api.sendReq(req);
    }

    private void getUserInfo(final String authCode,final MyWXLoginCallback callback) {
       MyRx.start(new MyFlowableSubscriber<MyWXUserInfo>() {
           @Override
           public void subscribe(FlowableEmitter<MyWXUserInfo> myFlowableEmitter) {
               Map<String,String>map=new HashMap<String,String>();
               map.put("appid",MyWXShare.getAppId());
               map.put("secret",MyWXShare.getAppSecret());
               map.put("code",authCode);
               map.put("grant_type","authorization_code");
               try {
                   String content = getReultForHttpPost(loginUrl, map);
                   MyWXUserInfo loginData = new Gson().fromJson(content, MyWXUserInfo.class);

                   Map<String,String>infoMap=new HashMap<String,String>();
                   infoMap.put("access_token",loginData.getAccess_token());
                   infoMap.put("openid",loginData.getOpenid());
                   infoMap.put("lang","zh_CN");

                   String infoContent = getReultForHttpPost(getInfoUrl, infoMap);

                   MyWXUserInfo myWxUserInfo = new Gson().fromJson(infoContent, MyWXUserInfo.class);

                   myWxUserInfo.setScope(loginData.getScope());
                   myWxUserInfo.setAccess_token(loginData.getAccess_token());
                   myWxUserInfo.setExpires_in(loginData.getExpires_in());
                   myWxUserInfo.setRefresh_token(loginData.getRefresh_token());

                   myFlowableEmitter.onNext(myWxUserInfo);
                   myFlowableEmitter.onComplete();
               } catch (IOException e) {
                   myFlowableEmitter.onError(new Throwable("微信登录获取用户信息失败"));
                   e.printStackTrace();
               }
           }

           @Override
           public void onNext(MyWXUserInfo userInfo) {
               callback.loginSuccess(userInfo);
           }

           @Override
           public void onError(Throwable t) {
               super.onError(t);
               callback.loginFail();
           }
       });
    }
    //登录
    private final String loginUrl  ="https://api.weixin.qq.com/sns/oauth2/access_token";
    //获取用户信息
    private final String getInfoUrl="https://api.weixin.qq.com/sns/userinfo";
    private String getReultForHttpPost(String url,Map<String,String>map) throws ClientProtocolException, IOException {
        HttpPost httpPost=new HttpPost(url);
        List<NameValuePair>list=new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));//编者按：与HttpGet区别所在，这里是将参数用List传递
        String result="";
        HttpResponse response=new DefaultHttpClient().execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();
            result= EntityUtils.toString(entity, HTTP.UTF_8);
        }
        return result;
    }


}
