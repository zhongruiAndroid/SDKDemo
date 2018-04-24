package com.sdklibrary.base.pay.wxpay;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by administartor on 2017/8/28.
 */

public class MyWXOrderBean {
    /**
     * 应用ID(必填)
     * 微信开放平台审核通过的应用APPID
     */
    private String appId;
    /**
     * 商户号(必填)
     * 微信支付分配的商户号
     */
    private String mch_id;
    private String miyao;

    /**
     * 通知地址(必填)
     * 用于本地生成支付订单信息
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     */
    private String notifyUrl;
    /**
     * 商品描述(必填)
     * 比如：腾讯充值中心-QQ会员充值
     */
    private String body;
    /**
     * 商户订单号(必填)
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
     */
    private String out_trade_no;
    /**
     * 总金额(必填)单位：分
     * 订单总金额，单位为分，详见支付金额
     */
    private int totalFee;
    /**
     * 终端IP(必填)
     * 用户端实际ip
     */
    public  String IP;//终端IP
    /**
     * 随机字符串(必填)
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    public  String nonceStr;//随机数


    //用于服务器生成订单号和签名
    private String prepayId;//微信支付订单号
    /**
     * 签名(必填)
     */
    private String sign;//微信支付签名
    private String packageValue="Sign=WXPay";
    private String timeStamp;

    public MyWXOrderBean() {
        this.nonceStr=WXUtils.getNonceStr();
    }

    public void setIP(Context context) {
        if(TextUtils.isEmpty(this.IP)){
            this.IP = WXUtils.getIP(context);
        }
    }

    public String getAppId() {
        return appId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getMiyao() {
        return miyao;
    }

    public void setMiyao(String miyao) {
        this.miyao = miyao;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
}
