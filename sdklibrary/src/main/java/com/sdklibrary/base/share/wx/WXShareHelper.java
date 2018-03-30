package com.sdklibrary.base.share.wx;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.sdklibrary.base.share.ShareParam;

/**
 * Created by Administrator on 2018/3/7.
 */

public class WXShareHelper extends ShareParam {
    /*
    * WXSceneSession = 0;会话
    * WXSceneTimeline = 1;朋友圈
    * WXSceneFavorite = 2;收藏
    * */
/*
    @IntDef({friend,friendCircle,favorite})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MyShareType{};
*/

    //分享到哪里
    public int scene;

    public void setScene(@MyShareType int scene){
        this.scene=scene;
    }

    public int getScene() {
        return scene;
    }

    public static class WebHelperWX extends WXShareHelper {
        public WebHelperWX(@MyShareType int scene) {
            setScene(scene);
        }
        public String title;
        public String description;
        public String url;
        public Bitmap bitmap;
        public int bitmapResId;

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

    public static class VideoHelperWX extends WebHelperWX {
        public VideoHelperWX(@MyShareType int scene) {
            super(scene);
        }
        public int dstWidth=150;
        public int dstHeight=150;
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
    }
    public static class ImageHelperWX extends WXShareHelper {
        public Bitmap bitmap;
        public int bitmapResId;
        public int dstWidth=150;
        public int dstHeight=150;
        public ImageHelperWX(@MyShareType int scene) {
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

        public void setBitmapResId(int bitmapResId) {
            this.bitmapResId = bitmapResId;
        }
    }
    public static class TextHelperWX extends WXShareHelper {
        public String title;
        public String text;
        public String description;
        public TextHelperWX(@MyShareType int scene) {
            setScene(scene);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
