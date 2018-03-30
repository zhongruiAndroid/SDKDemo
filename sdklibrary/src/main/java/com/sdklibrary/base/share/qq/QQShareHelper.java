package com.sdklibrary.base.share.qq;

import com.sdklibrary.base.share.ShareParam;

/**
 * Created by Administrator on 2018/3/7.
 */

public class QQShareHelper extends ShareParam{
/*
    @IntDef({QQ, QZONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MyShareType{};
*/
    /**
     * 页面链接
     */
    public String url;
    /**
     * 标题
     */
    public String title;
    /**
     * 摘要
     */
    public String description;
    public String appName;
    //分享到哪里
    public int scene;

    public void setScene(@MyShareType int scene){
        this.scene=scene;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getScene() {
        return scene;
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

    public static class QQWebHelper extends QQShareHelper {
        public QQWebHelper(@MyShareType int scene) {
            setScene(scene);
        }
        public String imageUrl;
        public String imagePath;

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
    public static class QQImageHelper extends QQShareHelper {
        public String imagePath;
        public String getImagePath() {
            return imagePath;
        }
        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

    }
    public static class QQAudioHelper extends QQShareHelper {
        public String imageUrl;
        public String audioUrl;

        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public String getAudioUrl() {
            return audioUrl;
        }
        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }
    }
    public static class QQAppHelper extends QQShareHelper {
        public String imageUrl;
        public String audioUrl;
        public String arkJson;

        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public String getAudioUrl() {
            return audioUrl;
        }
        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }
        public String getArkJson() {
            return arkJson;
        }
        public void setArkJson(String arkJson) {
            this.arkJson = arkJson;
        }
    }
}
