package com.sdklibrary.base.share.qq.bean;

import com.sdklibrary.base.share.ShareParam;

/**
 * Created by Administrator on 2018/4/24.
 */

public abstract class MyQQBaseShareHelper extends ShareParam {
    /**
     * 页面链接
     */
    private String url;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String description;
    private String appName;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
