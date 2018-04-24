package com.sdklibrary.base.share.wx.bean;

import com.sdklibrary.base.share.ShareParam;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyWXTextHelper extends ShareParam {
    private String title;
    private String text;
    private String description;
    public MyWXTextHelper(@MyShareType int scene) {
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
