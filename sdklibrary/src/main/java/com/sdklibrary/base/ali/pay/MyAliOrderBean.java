package com.sdklibrary.base.ali.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/8.
 */

public class MyAliOrderBean {
    private String appId;
    private String pid;//商户id
    private String siYao;//私钥


    private String notifyUrl;//通知地址
    private double total_amount;//金额
    private String subject;//主题
    private String body;//支付说明
    private String out_trade_no;//订单号

    //服务器生成orderInfo
    private String  orderInfo;


    public String product_code="QUICK_MSECURITY_PAY";
    public String timestamp;

    public MyAliOrderBean( ) {
        timestamp=dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSiYao() {
        return siYao;
    }

    public void setSiYao(String siYao) {
        this.siYao = siYao;
    }

    private String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
