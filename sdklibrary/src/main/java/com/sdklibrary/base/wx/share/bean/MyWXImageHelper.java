package com.sdklibrary.base.wx.share.bean;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.sdklibrary.base.ShareParam;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyWXImageHelper extends ShareParam {
    private Bitmap bitmap;
    private int bitmapResId;
    private int dstWidth=150;
    private int dstHeight=150;
    public MyWXImageHelper(@MyShareType int scene) {
        setScene(scene);
    }
    public int getDstWidth() {
        return dstWidth;
    }
    public void setDstWidth(int dstWidth) {
        this.dstWidth = dstWidth;
    }

    public int getDstHeight() {
        return dstHeight;
    }

    public void setDstHeight(int dstHeight) {
        this.dstHeight = dstHeight;
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
