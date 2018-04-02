# SDKDemo
# 支付宝支付  

| √:必传参数<br/>×:非必参数<br/>o:可在application初始化 | 说明                                                                       | 本地生成支付订单 | 服务器生成支付订单 |
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
### 支付宝支付示例
```
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

```
/********************************app本地生成支付订单信息传参******************************************/
AliPayOrderBean aliBean=new AliPayOrderBean();
aliBean.setAppId(appid);
aliBean.setPid(pid);
aliBean.setSiYao(rsa2);
//如果在application初始化上述三个参数,则可以不用每次调用set方法赋值
//MyAliPay.setConfig(appid,pid,siyao);
aliBean.setNotifyUrl(notifyUrl);
aliBean.setOut_trade_no(orderNo);
aliBean.setTotal_amount(totalPrice);
aliBean.setSubject("XXX订单");
aliBean.setBody("XXX订单交易");

/********************************服务器生成支付订单信息传参*********************************************/
AliPayOrderBean aliBean=new AliPayOrderBean();
aliBean.setOrderInfo(orderInfo);

/*如果getOrderInfo()不为空则为服务器生成订单,否则属于app本地生成订单信息*/
MyAliPay.newInstance(mContext).startPay(aliBean, new MyAliPayCallback() {
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
```
<br/>  

# 微信支付  
### 微信支付示例
```
/********************************app本地生成支付订单信息传参******************************************/
WXOrderBean wxOrderBean=new WXOrderBean();
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
//IP和nonceStr两个参数在支付之前自动赋值,可以不用手动赋值
wxOrderBean.IP="设备ip地址";
wxOrderBean.nonceStr="随机数";

/********************************服务器生成支付订单信息传参*********************************************/
WXOrderBean wxOrderBean=new WXOrderBean();
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
<br/>
//如果支付时不添加回调则需要按照微信官方的做法在WXPayEntryActivity中处理
MyWXPay.newInstance(this).startPay(wxOrderBean);
```
**如果微信分享、支付、登录不成功，请仔细检查相关配置(微信开放平台是否配置相关信息，应用包名、应用签名是否配置正确)和app是否进行签名**
