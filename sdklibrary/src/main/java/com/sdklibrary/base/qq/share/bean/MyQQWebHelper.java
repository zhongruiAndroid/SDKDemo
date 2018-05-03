package com.sdklibrary.base.qq.share.bean;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyQQWebHelper extends MyQQBaseShareHelper {
    public MyQQWebHelper(@MyShareType int scene) {
        setScene(scene);
    }
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}