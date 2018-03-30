package com.sdklibrary.base.pay.wxpay;

import android.content.Context;

/**
 * Created by administartor on 2017/8/28.
 */

public class WXOrderBean {
    private String notifyUrl;//通知地址
    private String appId;//appid
    private String mch_id;//mch_id
    private String miyao;//密钥
    private String body;//描述
    private String out_trade_no;//订单号
    private int totalFee;//总金额,单位：分
    public  String IP;//终端IP
    public  String nonceStr;//随机数


    //用于服务器生成订单号和签名
    private String prepayId;//微信支付订单号
    private String sign;//微信支付签名
    private  String packageValue="Sign=WXPay";
    private  String timeStamp;

    public WXOrderBean(Context context) {
        this.IP = WXUtils.getIP(context);
        this.nonceStr=WXUtils.getNonceStr();
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
