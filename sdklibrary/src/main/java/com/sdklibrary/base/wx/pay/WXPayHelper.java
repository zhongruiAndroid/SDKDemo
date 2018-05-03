package com.sdklibrary.base.wx.pay;

import com.sdklibrary.base.wx.share.MyWXUtil;

import java.util.Random;

/**
 * Created by Administrator on 2018/3/7.
 */

public class WXPayHelper {
    /**
     * 应用ID(必填)
     * 微信开放平台审核通过的应用APPID
     */
    private String AppID;
    /**
     * 商户号(必填)
     * 微信支付分配的商户号
     */
    private String mchId;
    /**
     * 随机字符串(必填)
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str= getNonceStr();
    /**
     * 签名(必填)
     */
    private String sign;
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
    private int total_fee;
    /**
     * 终端IP(必填)
     * 用户端实际ip
     */
    private String spbill_create_ip;
    /**
     * 通知地址(必填)
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     */
    private String notify_url;
    /**
     * 交易类型(必填)
     * 固定值：APP
     */
    private String trade_type="APP";
    //////////////////////必填项10个，非必填项10个/////////////////////
    /**
     * 设备号
     * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
     */
    public String device_info="WEB";
    /**
     * 签名类型
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    public String sign_type="MD5";
    /**
     * 商品详情
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     */
    public String detail;
    /**
     *附加数据
     *附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    public String attach;
    /**
     *货币类型
     *符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    public String fee_type="CNY";
    /**
     *交易起始时间
     *订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    public String time_start;
    /**
     *交易结束时间
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则
     *建议：最短失效时间间隔大于1分钟
     */
    public String time_expire;
    /**
     *订单优惠标记
     *订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     */
    public String goods_tag;
    /**
     *指定支付方式
     *no_credit--指定不能使用信用卡支付
     */
    public String limit_pay="no_credit";
    /**
     *场景信息
     *该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
     {
     "store_id": "", //门店唯一标识，选填，String(32)
     "store_name":"”//门店名称，选填，String(64)
     }
     */
    public String scene_info;


    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonce_str() {
        return nonce_str;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }
    /**
     * 随机字符串，32位
     * @return
     */
    public String getNonceStr() {
        Random random = new Random();
        return MyWXUtil.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

}
