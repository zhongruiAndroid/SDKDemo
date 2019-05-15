package com.sdklibrary.base.wx.share.bean;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.sdklibrary.base.ShareParam;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyWXSmallProgramHelper extends ShareParam {
    public MyWXSmallProgramHelper(@MyShareType int scene) {
        setScene(scene);
    }
    private String title;
    private String description;
    private Bitmap bitmap;
    private int bitmapResId;

    private String webpageUrl ;
    private String userName;
    private String path;
    private boolean withShareTicket;
    private int miniprogramType=WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;


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


    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isWithShareTicket() {
        return withShareTicket;
    }

    public void setWithShareTicket(boolean withShareTicket) {
        this.withShareTicket = withShareTicket;
    }

    public int getMiniprogramType() {
        return miniprogramType;
    }

    public void setMiniprogramType(int miniprogramType) {
        this.miniprogramType = miniprogramType;
    }
}
