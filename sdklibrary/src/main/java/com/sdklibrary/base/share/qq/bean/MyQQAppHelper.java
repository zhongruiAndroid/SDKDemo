package com.sdklibrary.base.share.qq.bean;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyQQAppHelper extends MyQQBaseShareHelper {
    private String imageUrl;
    private String imagePath;
    private String arkJson;

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getArkJson() {
        return arkJson;
    }
    public void setArkJson(String arkJson) {
        this.arkJson = arkJson;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
