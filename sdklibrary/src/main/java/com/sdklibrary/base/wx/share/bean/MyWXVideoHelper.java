package com.sdklibrary.base.wx.share.bean;

import com.sdklibrary.base.ShareParam;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyWXVideoHelper extends MyWXWebHelper {
    public MyWXVideoHelper(@ShareParam.MyShareType int scene) {
        super(scene);
    }
    private int dstWidth=150;
    private int dstHeight=150;
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