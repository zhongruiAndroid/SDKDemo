package com.sdklibrary.base.share.qq.bean;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyQQWebHelper extends MyQQBaseShareHelper {
    public MyQQWebHelper(@MyShareType int scene) {
        setScene(scene);
    }
    private String imageUrl;
    private String imagePath;

    public String getImageUrl() {
        return imageUrl;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}