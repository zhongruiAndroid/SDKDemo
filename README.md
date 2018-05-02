# <h2 id="top">SDK集成</h2>
**基于Rxjava2.X实现部分功能,如果项目中使用了Rxjava1.X请勿使用以下功能**  

* [支付宝](#zfb)
* [微信](#wx)
	* [相关配置](#wx)
	* [1.支付](#wx1)
	* [2.分享](#wx2)
	* [2.登录](#wx3)
* [QQ](#qq)
	* [相关配置](#qq)
	* [1.分享](#qq1)
	* [2.登录](#qq2)
	  
[ ![Download](https://api.bintray.com/packages/zhongrui/mylibrary/sdklibrary/images/download.svg) ](https://bintray.com/zhongrui/mylibrary/sdklibrary/_latestVersion)<--版本号
```
compile 'com.github:sdklibrary:版本号看上面'
```
# <h2 id="zfb">支付宝支付</h2>

```xml
<!--支付宝权限-->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<!--支付宝支付Activity配置-->
<activity
    android:name="com.alipay.sdk.app.H5PayActivity"
    android:configChanges="orientation|keyboardHidden|navigation|screenSize"
    android:exported="false"
    android:screenOrientation="behind"
    android:windowSoftInputMode="adjustResize|stateHidden">
</activity>
<activity
    android:name="com.alipay.sdk.app.H5AuthActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:exported="false"
    android:screenOrientation="behind"
    android:windowSoftInputMode="adjustResize|stateHidden">
</activity>
```

```java
/********************************app本地生成支付订单信息传参******************************************/
MyAliOrderBean aliBean=new MyAliOrderBean();
aliBean.setAppId(appid);
aliBean.setPid(pid);
aliBean.setSiYao(rsa2);
//如果在application初始化上述三个参数,则可以不用每次调用set方法赋值
//MyAliPay.setConfig(appid,pid,siyao);
aliBean.setNotifyUrl(notifyUrl);
aliBean.setOut_trade_no(orderNo);
//订单金额(单位:元)
aliBean.setTotal_amount(totalPrice);
aliBean.setSubject("XXX订单");
aliBean.setBody("XXX订单交易");

/********************************服务器生成支付订单信息传参*********************************************/
MyAliOrderBean aliBean=new MyAliOrderBean();
aliBean.setOrderInfo(orderInfo);

/*如果getOrderInfo()不为空则为服务器生成订单,否则属于app本地生成订单信息*/
MyAliPay.newInstance(mContext).startPay(aliBean, new MyAliPayCallback() {
    @Override
    public void paySuccess(PayResult result) {
        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。(官方原话)
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
```  

| √:必传参数<br/>×:非必传参数<br/>o:可在application初始化 | 说明                                                                       | 本地生成支付订单 | 服务器生成支付订单 |
|-----------------------|----------------------------------------------------------------------------|:----------------:|:------------------:|
| setAppId              | 开发者应用id|         √-o        |          ×         |
| setNotifyUrl          | 支付宝通知商户服务器的地址                                                 |         √        |          ×         |
| setPid                | 商户号                                                                     |         √-o        |          ×         |
| setSiYao              | 私钥                                                                       |         √-o        |          ×         |
| setOut_trade_no       | 自己服务器生成的订单号                                                     |         √        |          ×         |
| setTotal_amount       | 订单金额(单位:元)精确到小数点后两位<br/>取值范围[0.01,100000000]               |         √        |          ×         |
| setSubject            | 商品的标题/交易标题/订单标题/订单关键字等                                  |         √        |          ×         |
| setBody               | 订单具体描述信息                                                           |         √        |          ×         |
| setOrderInfo          | 请求支付的订单信息                                                         |         ×        |          √         |


**上述参数中的appid,pid,siyao可在application中调用MyAliPay.setConfig(APPID,PID,SIYAO)设置**
#### 支付宝官方混淆规则
```java
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
```
<br/>  

# <h2 id="wx">微信SDK</h2>  
**[返回目录](#top)** 
```xml
<!--微信所需权限-->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<!--微信登录和分享Activity配置-->
<activity
    android:name="包名.wxapi.WXEntryActivity"
    android:configChanges="keyboardHidden|orientation|screenSize"
    android:exported="true"
    android:screenOrientation="portrait"
    android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
<!--微信支付Activity配置-->
<activity 
    android:name="包名.wxapi.WXPayEntryActivity"
    android:theme="@android:style/Theme.Translucent.NoTitleBar"
    >
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <!--这里填写自己的appid,貌似不写也可以-->
        <data android:scheme="wx957fd60b70d04b60"/>
    </intent-filter>
</activity>
```
<br/>  

```java
//在项目包名下新建一个wxapi文件夹，里面新建WXEntryActivity(用于登录和分享)，WXPayEntryActivity(用于支付)
//分别继承MyWXEntryActivity和MyWXPayEntryActivity

//登录分享
public class WXEntryActivity extends MyWXEntryActivity{
    @Override
    protected int getContentView() {
        return 0;
    }
    @Override
    protected void initView() {
        
    }
}
//支付
public class WXPayEntryActivity extends MyWXPayEntryActivity {
    @Override
    protected int getContentView() {
        return 0;
    }
    @Override
    protected void initView() {
        
    }   
}
```
#### 微信官方混淆规则
```java
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}
```  
#### <h2 id="wx1">微信支付</h2>
    
**[返回目录](#top)** 
```java
/********************************app本地生成支付订单信息传参******************************************/
MyWXOrderBean wxOrderBean=new MyWXOrderBean();
wxOrderBean.setAppId(appId);
wxOrderBean.setMch_id(mch_id);
wxOrderBean.setMiyao(miyao);
//如果在application初始化上述三个参数,则可以不用每次调用set方法赋值
//MyWXPay.setConfig(appId,mch_id,miyao);
wxOrderBean.setNotifyUrl(notifyUrl);
wxOrderBean.setBody(body);
wxOrderBean.setOut_trade_no(out_trade_no);
//订单金额，单位：分
wxOrderBean.setTotalFee(totalFee);
//ip和nonceStr两个参数在支付之前自动赋值,可以不用手动赋值
wxOrderBean.ip="设备ip地址";
wxOrderBean.nonceStr="随机数";

/********************************服务器生成支付订单信息传参*********************************************/
MyWXOrderBean wxOrderBean=new MyWXOrderBean();
wxOrderBean.setAppId(appId);
wxOrderBean.setMch_id(mch_id);
wxOrderBean.setMiyao(miyao);
wxOrderBean.setPrepayId(prepayId);
wxOrderBean.setSign(sign);
//packageValue默认为Sign=WXPay
wxOrderBean.setPackageValue(packageValue);
wxOrderBean.setTimeStamp(timeStamp);

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

//如果支付时不添加回调则需要按照微信官方的做法在WXPayEntryActivity中重写onResp(BaseResp resp)方法处理
MyWXPay.newInstance(this).startPay(wxOrderBean);
```
**如果微信分享、支付、登录不成功，请仔细检查相关配置(微信开放平台是否配置相关信息，应用包名、应用签名是否配置正确)和app是否进行签名**

| √:必传参数<br/>×:非必传参数<br/>o:可在application初始化 | 说明                                                                         | 本地生成支付订单 | 服务器生成支付订单 |
|-------------------------|------------------------------------------------------------------------------|:----------------:|:------------------:|
| setAppId                | 开发者应用id |         √-o        |          √-o         |
| setMch_id               | 商户号                                                                       |         √-o        |          √-o         |
| setMiyao                | 密钥                                                                         |         √-o        |          √-o         |
| setNotifyUrl            | 微信通知商户服务器的地址                                                     |         √        |          ×         |
| setOut_trade_no         | 自己服务器生成的订单号                                                       |         √        |          ×         |
| setTotalFee             | 订单金额(单位:分)                                                            |         √        |          ×         |
| setBody                 | 订单具体描述信息                                                             |         √        |          ×         |
| ip                      | 设备ip                                                                       |         √        |          ×         |
| nonceStr                | 随机数                                                                       |         √        |          ×         |
| setPrepayId             | 微信支付订单号                                                               |         ×        |          √         |
| setSign                 | 请求微信支付所需要的签名(不是app签名)                                        |         ×        |          √         |
| setPackageValue         | 默认值:Sign=WXPay                                                            |         ×        |          √         |
| setTimeStamp            | 时间戳                                                                       |         ×        |          √         |
  
**上述参数中的APPID,MCH_ID,MIYAO可在application中调用MyWXPay.setConfig(APPID,MCH_ID,MIYAO)设置**  

#### <h2 id="wx2">微信分享</h2>  
**[返回目录](#top)** 
```java
//分享网页
MyWXWebHelper helper=new MyWXWebHelper(scene);
//scene参数说明
//分享好友：ShareParam.friend：
//分享朋友圈：ShareParam.friendCircle
//收藏：ShareParam.favorite

helper.setBitmap(bitmap);
//或者helper.setBitmapResId(R.mipmap.ic_launcher);
helper.setUrl("目标网址");
helper.setTitle("分享的标题");
helper.setDescription("分享的内容");
MyWXShare.newInstance(this).shareWeb(helper, new MyWXShareCallback() {
    @Override
    public void shareSuccess() {
	//分享成功
    }
    @Override
    public void shareFail() {
	//分享失败
    }
    @Override
    public void shareCancel() {
	//分享取消
    }
});
```

  
  | MyWXWebHelper(分享网页)              | 说明     | 是否必填 |
|-----------------------------|----------|:--------:|
| setUrl                      | 网页url  |     √    |
| setTitle                    | 网页标题 |     √    |
| setDescription              | 网页描述 |     √    |
| setBitmap或者setBitmapResId | 缩略图     |     √    |
| 图片大小不能超过32K |          |          |
  
| MyWXTextHelper(分享文本) | 说明                     | 是否必填 |
|----------------|--------------------------|:--------:|
| setText        | 文本内容                 |     √    |
| setTitle       | 文本标题                 |     ×    |
| setDescription | 文本描述(默认为文本内容) |     ×    |
  
  | MyWXImageHelper(分享图片)            | 说明     | 是否必填 |
|---------------------------|----------|:--------:|
| setBitmap或setBitmapResId | 图片     |     √    |
| setDstWidth(默认150)      | 缩略图宽度 |     √    |
| setDstHeight(默认150)     | 缩略图高度 |     √    |
  
  | MyWXVideoHelper(分享视频)            | 说明       | 是否必填 |
|---------------------------|------------|:--------:|
| setUrl                    | 视频url    |     √    |
| setTitle                  | 视频标题   |     √    |
| setDescription            | 视频描述   |     √    |
| setBitmap或setBitmapResId | 缩略图     |     √    |
| setDstWidth(默认150)      | 缩略图宽度 |     √    |
| setDstHeight(默认150)     | 缩略图高度 |     √    |

  
  | MyWXWebHelper(分享音乐)   | 说明       | 是否必填 |
|---------------------------|------------|:--------:|
| setUrl                    | 音乐url    |     √    |
| setTitle                  | 音乐标题   |     √    |
| setDescription            | 音乐描述   |     √    |
| setBitmap或setBitmapResId | 缩略图     |     √    |
| MyWXVideoHelper           |            |          |
| setDstWidth(默认150)      | 缩略图宽度 |     √    |
| setDstHeight(默认150)     | 缩略图高度 |     √    |
    
    
#### <h2 id="wx3">微信登录</h2>  
**[返回目录](#top)** 
```java
/*判断是否安装微信*/
MyWXShare.newInstance(this).isInstall()
/*不用支付功能secret可传null,建议放到application初始化*/
MyWXShare.setAppId(appid,secret);
MyWXShare.newInstance(this).login(new MyWXLoginCallback() {
    @Override
    public void loginSuccess(MyWXUserInfo userInfo) {
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
```
| MyWXUserInfo |                  返回用户信息说明                                                                           |
|------------------|---------------------------------------------------------------------------------------------|
| access_token     | 接口调用凭证                                                                                |
| refresh_token    | 用户刷新access_token                                                                        |
| expires_in       | access_token接口调用凭证超时时间，单位（秒）                                                |
| openid           | 授权用户唯一标识(同一账户,ios和android登录会返回不同openid)                          |
| unionid          | 用户统一标识,针对一个微信开放平台帐号下的应用,同一用户(ios和android登录)返回的unionid是唯一的 |
| nickname         | 普通用户昵称                                                                                |
| headimgurl       | 用户头像,用户没有头像时该项为空                                                             |
| sex              | 普通用户性别,0未设置性别,1为男性,2为女性                                                    |
| country          | 国家,如中国为CN                                                                             |
| province         | 普通用户个人资料填写的省份                                                                  |
| city             | 普通用户个人资料填写的城市                                                                  |
| scope            | 用户授权的作用域,使用逗号（,）分隔                                                          |
| language         | 国家地区语言版本,zh_CN 简体,zh_TW 繁体,en 英语,默认为zh-CN                                  |
| privilege        | 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）                                     |
  
 
  
# <h2 id="qq">QQSDK</h2>  
**[返回目录](#top)** 
```java
//在分享或者登录的Activity中配置,建议在父类统一配置,不配置会导致回调函数不执行
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    MyQQActivityResult.onActivityResult(requestCode,resultCode,data);
}
```
```xml
<!--QQ权限配置-->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<activity
    android:name="com.tencent.tauth.AuthActivity"
    android:noHistory="true"
    android:launchMode="singleTask">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="tencent[你的appid]" />
        <!--例如-->
        <!--<data android:scheme="tencent88888888" />-->
    </intent-filter>
</activity>
<activity
    android:name="com.tencent.connect.common.AssistActivity"
    android:screenOrientation="behind"
    android:theme="@android:style/Theme.Translucent.NoTitleBar"
    android:configChanges="orientation|keyboardHidden">
</activity>

```
#### <h2 id="qq1">QQ分享</h2>  
**[返回目录](#top)** 

```java
//返回Tencent类的实例:MyQQShare.newInstance(this).getTencent();
//检查是否有安装QQ
MyQQShare.newInstance(this).isInstall()

MyQQWebHelper helper=new MyQQWebHelper(scene);
//scene参数说明
//QQ好友：ShareParam.QQ
//空间：ShareParam.QZONE
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
```
| MyQQWebHelper--(图文分享) | 说明                                  | 是否必填 |
|-------------------------|---------------------------------------|:--------:|
| setTitle                | 标题                                  |     √    |
| setDescription          | 摘要                                  |     ×    |
| setUrl                  | 页面链接                              |     √    |
| setImagePath            | 图片(手机本地图片路径或者网络图片url) |     ×    |
  
  | MyQQImageHelper(图片分享) | 说明                                  |是否必填 |
|---------------------------|---------------------------------------|:--------:|
| setImagePath              | 需要分享的本地图片路径(不能是网络图片url)                |     √    |
  
  | MyQQAudioHelper(音乐分享) | 说明                                                | 是否必填 |
|---------------------------|-----------------------------------------------------|:--------:|
| setTitle                  | 标题                                                |     √    |
| setDescription            | 摘要                                                |     ×    |
| setUrl                    | 页面跳转链接                                        |     √    |
| setAudioUrl               | 音乐文件的远程链接, 以URL的形式传入, 不支持本地音乐 |     √    |
| setImagePath               | 分享图片的URL或者本地路径                           |     ×    |
| setAppName                | 应用名称                                            |     ×    |
  
    
    
#### <h2 id="qq2">QQ登录</h2>  
**[返回目录](#top)** 
```java
/*建议放到application初始化*/
MyQQShare.setAppId(appid);
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
```

| MyQQUserInfo | 返回用户信息说明                                                                                             |
|--------------|--------------------------------------------------------------------------------------------------------------|
| access_token | 接口调用凭证                                                                                                 |
| pay_token    |                                                                                                              |
| expires_in   | access_token接口调用凭证超时时间，单位（秒）                                                                 |
| openid       | 授权用户唯一标识(同一账户,ios和android登录会返回不同openid)                                                  |
| unionid      | 用户统一标识,ios和android数据未申请打通时为空,打通之后不为空,且同一用户(ios和android登录)返回的unionid是唯一的 |
| nickname     | 用户昵称                                                                                                     |
| userImageUrl | 用户头像                                                                                                     |
| sex          | 性别"男"或"女"                                                                                               |
