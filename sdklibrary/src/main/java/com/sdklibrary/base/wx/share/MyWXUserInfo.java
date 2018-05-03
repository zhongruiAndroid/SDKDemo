package com.sdklibrary.base.wx.share;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class MyWXUserInfo {

    /**
     * access_token : 7_oZH2jM09R5XbmNj3KAZzDzvlPhnSr5Svd9s-EOCPFknLFh22WJVqvfsa4uiJu89QrJLP3HUR3m4P3XxlmpN14nRujUoNxUc66PFSZB-0quA
     * expires_in : 7200
     * refresh_token : 7_9iZkECxorwQGmqUH-rq_WhnvgO3mgOoD5MagDHNoO48-MW7vBWMte8GA5YGKQt6u5oMzmL9kJHV3FlkmyVk6rel7nqB1kqeERbzKLYQoCOQ
     * openid : osbnB0xNhmzSjQUxTHE0JwXd4iXE
     * nickname : 哟嚯
     * sex : 0
     * language : zh_CN
     * city :
     * province :
     * country :
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/f4PSm6IbdYAL3xxsb9JrKV9hAmBPDq0DTJBMKhY3qKA3qXHeILM1icS6GibSiagxWmz8XnNjdgv68M4YbqasWic8NQ/132
     * privilege : []
     * scope : snsapi_userinfo
     * unionid : oragc1c8Zl_ci7GBliOigm_k2lxg
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String scope;
    private String unionid;
    private List<?> privilege;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
