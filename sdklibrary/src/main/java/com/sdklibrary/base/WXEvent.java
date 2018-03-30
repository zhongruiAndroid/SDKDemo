package com.sdklibrary.base;

/**
 * Created by Administrator on 2018/3/19.
 */

public class WXEvent {
    public int responseCode;
    public int flag;
    public static final int wx_login=1000;
    public static final int wx_share=1001;
    public static final int wx_pay=1002;
    public WXEvent(int flag,int responseCode) {
        this.responseCode = responseCode;
        this.flag=flag;
    }
    public static class WXLoginEvent{
        public int responseCode;
        public String authCode;

        public WXLoginEvent(int responseCode,String authCode) {
            this.authCode = authCode;
            this.responseCode = responseCode;
        }
    }
}
