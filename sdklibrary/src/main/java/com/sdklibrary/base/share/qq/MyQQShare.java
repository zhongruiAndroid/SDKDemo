package com.sdklibrary.base.share.qq;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.MyRx;
import com.google.gson.Gson;
import com.sdklibrary.base.share.BaseShare;
import com.sdklibrary.base.share.ShareParam;
import com.sdklibrary.base.share.qq.bean.MyQQAppHelper;
import com.sdklibrary.base.share.qq.bean.MyQQAudioHelper;
import com.sdklibrary.base.share.qq.bean.MyQQImageHelper;
import com.sdklibrary.base.share.qq.bean.MyQQUserBean;
import com.sdklibrary.base.share.qq.bean.MyQQUserInfo;
import com.sdklibrary.base.share.qq.bean.MyQQWebHelper;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

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
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import io.reactivex.FlowableEmitter;

import static cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory.TAG;

/**
 * Created by Administrator on 2018/3/9.
 */

public class MyQQShare extends BaseShare{

    /*
    *
    * qq分享配置onActivityResult
    * if (requestCode == Constants.REQUEST_QQ_SHARE) {
    *     Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    * }
    *
    * qq登录配置onActivityResult
    * if (requestCode == Constants.REQUEST_LOGIN ||requestCode == Constants.REQUEST_APPBAR) {
    *       Tencent.onActivityResultData(requestCode, resultCode, data,listener);
    * }
    *
    *<activity
    *       android:name="com.tencent.tauth.AuthActivity"
    *       android:noHistory="true"
    *       android:launchMode="singleTask">
    *       <intent-filter>
    *           <action android:name="android.intent.action.VIEW" />
    *           <category android:name="android.intent.category.DEFAULT" />
    *           <category android:name="android.intent.category.BROWSABLE" />
    *           <data android:scheme="tencent[appid]" />
    *       </intent-filter>
    *   </activity>
    *
    *   <activity
    *       android:name="com.tencent.connect.common.AssistActivity"
    *       android:screenOrientation="behind"
    *       android:theme="@android:style/Theme.Translucent.NoTitleBar"
    *       android:configChanges="orientation|keyboardHidden">
    *   </activity>
    * */
    private static String appId;
    public boolean isInstall(){
        if(mTencent==null){
            mTencent  = Tencent.createInstance(appId,context);
        }
        return mTencent.isQQInstalled(context);
    }
    private Tencent mTencent;

    private MyQQShare(Context context) {
        this(context,null);
    }
    private MyQQShare(Context context, String appId) {
        this.context=context;
        if(!TextUtils.isEmpty(appId)){
            this.appId=appId;
        }
        if(mTencent==null){
            mTencent  = Tencent.createInstance(this.appId,context);
        }
    }
    public static void setAppId(String appID) {
        appId=appID;
    }
    public static MyQQShare newInstance(Context context) {
        return new MyQQShare(context);
    }
    public static MyQQShare newInstance(Context context, String appId) {
        return new MyQQShare(context,appId);
    }


    /*******************************************************分享***********************************************************************/
    public void shareToQZone(MyQQWebHelper helper, MyQQShareListener listener) {
        shareWeb(helper,listener);
    }
    public void shareWeb(MyQQWebHelper helper, MyQQShareListener listener) {
        final Bundle params = new Bundle();
        //默认web形式分享
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, helper.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  helper.getDescription());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  helper.getUrl());
        if(TextUtils.isEmpty(helper.getImagePath())){
            //网络图片
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,helper.getImageUrl());
        }else{//本地图片
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,helper.getImagePath());
        }
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, helper.getAppName());
        /*分享额外选项，两种类型可选
        （默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
         MyQQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
         Tencent.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮*/
        if(helper.getScene()== ShareParam.QZONE){
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        mTencent.shareToQQ((Activity) context, params,listener);
    }

    public void shareImage(MyQQImageHelper helper, MyQQShareListener listener) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,helper.getImagePath());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,helper.getAppName());
        //图片分享
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ((Activity)context, params,listener);
    }
    public void shareAudio(MyQQAudioHelper helper, MyQQShareListener listener) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, helper.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  helper.getDescription());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, helper.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, helper.getImageUrl());
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, helper.getAudioUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, helper.getAppName());
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ((Activity)context, params,listener);
    }

    /**
     * 应用分享
     * @param helper
     * @param listener
     */
    public void shareApp(MyQQAppHelper helper, MyQQShareListener listener) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, helper.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, helper.getDescription());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,helper.getImageUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, helper.getAppName());
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, helper.getArkJson());
        mTencent.shareToQQ((Activity)context, params,listener);
    }


    /*******************************************************登录***********************************************************************/

    private final String SCOPE="all";
    public void login(final MyQQLoginCallback callback){
        //登录之后拿到
        mTencent.login((Activity) context, SCOPE, new IUiListener() {
            @Override
            public void onComplete(Object response) {
                if (null == response) {
                    callback.loginFail();
                    return;
                }
                Log.i("MyQQShare-login","==="+new Gson().toJson(response));
                JSONObject jsonResponse = (JSONObject) response;
                if (null != jsonResponse && jsonResponse.length() == 0) {
                    callback.loginFail();
                    return;
                }
                initOpenidAndToken((JSONObject) response);
                getQQUserInfo((JSONObject) response,callback);
            }
            @Override
            public void onError(UiError uiError) {
                callback.loginFail();
            }
            @Override
            public void onCancel() {
                callback.loginCancel();
            }
        });
    }
    public Tencent getTencent(){
        return mTencent;
    }
    public boolean ready(Context context) {
        if (mTencent == null) {
            return false;
        }
        boolean ready = mTencent.isSessionValid()
                && mTencent.getQQToken().getOpenId() != null;
        if (!ready) {
            Toast.makeText(context, "login and get openId first, please!",
                    Toast.LENGTH_SHORT).show();
        }
        return ready;
    }
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    public void getQQUserInfo(final JSONObject values,final MyQQLoginCallback callback){
        if(ready(context)){
            UserInfo mInfo = new UserInfo( context,mTencent.getQQToken());
            mInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object obj) {
                    Log.i("MyQQShare-getUserInfo","==="+new Gson().toJson(obj));
                    final MyQQUserInfo userInfo=new Gson().fromJson(values.toString(), MyQQUserInfo.class);

                    MyQQUserBean userBean=new Gson().fromJson(obj.toString(), MyQQUserBean.class);

                    userInfo.setUserImageUrl(userBean.getFigureurl_qq_2());
                    userInfo.setNickname(userBean.getNickname());
                    userInfo.setSex(userBean.getGender());
                    userInfo.setProvince(userBean.getProvince());
                    userInfo.setCity(userBean.getCity());
                    userInfo.setYear(userBean.getYear());
                    userInfo.setIs_yellow_vip(userBean.getIs_yellow_vip());
                    userInfo.setVip(userBean.getVip());
                    userInfo.setYellow_vip_level(userBean.getYellow_vip_level());
                    userInfo.setLevel(userBean.getLevel());
                    userInfo.setIs_yellow_year_vip(userBean.getIs_yellow_year_vip());

                    MyRx.start(new MyFlowableSubscriber<MyQQUserInfo>() {
                        @Override
                        public void subscribe(@NonNull FlowableEmitter<MyQQUserInfo> flowableEmitter) {
                            Map<String,String>map=new HashMap<String,String>();
                            try {
                                String jsonp = getResultForHttpGet(getInfoUrl, mTencent.getAccessToken());
                                Log.i(TAG+"===","resultForHttpGet==="+jsonp);
                                String json = jsonp.substring(jsonp.indexOf("(") + 1, jsonp.lastIndexOf(")"));
                                MyQQUserInfo jsonInfo=new Gson().fromJson(json, MyQQUserInfo.class);
                                if(jsonInfo.getError()!=0){
                                    userInfo.setError(jsonInfo.getError());
                                    userInfo.setError_description(jsonInfo.getError_description());
                                }else{
                                    userInfo.setClient_id(jsonInfo.getClient_id());
                                    userInfo.setUnionid(jsonInfo.getUnionid());
                                }
                                flowableEmitter.onNext(userInfo);
                                flowableEmitter.onComplete();
                            } catch (IOException e) {
                                e.printStackTrace();
                                callback.loginFail();
                            }
                        }
                        @Override
                        public void onNext(MyQQUserInfo userInfo) {
                            callback.loginSuccess(userInfo);
                        }
                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            callback.loginFail();
                        }
                    });

                }
                @Override
                public void onError(UiError uiError) {
                    callback.loginFail();
                }
                @Override
                public void onCancel() {
                    callback.loginCancel();
                }
            });
        }else{
            callback.loginFail();
        }
    }
    //获取QQ用户unionid
    private final String getInfoUrl="https://graph.qq.com/oauth2.0/me";
    //?access_token=081120E6BD39504E6C3090FA85E87A47&unionid=1
    private String getReultForHttpPost(String url,Map<String,String> map) throws ClientProtocolException, IOException {
        HttpPost httpPost=new HttpPost(url);
        List<NameValuePair> list=new ArrayList<NameValuePair>();
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
    public String getResultForHttpGet(String url,String accessToken) throws ClientProtocolException, IOException{
        //服务器  ：服务器项目  ：servlet名称
        String uri=url+"?access_token="+accessToken+"&unionid=1";
        //name:服务器端的用户名，pwd:服务器端的密码
        //注意字符串连接时不能带空格
        String result="";
        HttpGet httpGet=new HttpGet(uri);
        //取得HTTP response
        HttpResponse response=new DefaultHttpClient().execute(httpGet);
        //若状态码为200
        if(response.getStatusLine().getStatusCode()==200){
            //取出应答字符串
            HttpEntity entity=response.getEntity();
            result=EntityUtils.toString(entity, HTTP.UTF_8);
        }
        return result;
    }
}
