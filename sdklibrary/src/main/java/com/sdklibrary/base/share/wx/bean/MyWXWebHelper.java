package com.sdklibrary.base.share.wx.bean;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.sdklibrary.base.share.ShareParam;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyWXWebHelper extends ShareParam {
    public MyWXWebHelper(@MyShareType int scene) {
        setScene(scene);
    }
    private String title;
    private String description;
    private String url;
    private Bitmap bitmap;
    private int bitmapResId;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getBitmapResId() {
        return bitmapResId;
    }

    public void setBitmapResId(@DrawableRes int bitmapResId) {
        this.bitmapResId = bitmapResId;
    }
}
