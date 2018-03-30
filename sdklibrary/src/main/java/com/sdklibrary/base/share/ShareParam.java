package com.sdklibrary.base.share;

import android.support.annotation.IntDef;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/3/12.
 */

public class ShareParam {
    /*
     * WXSceneSession = 0;会话
     * WXSceneTimeline = 1;朋友圈
     * WXSceneFavorite = 2;收藏
     * */
    public static final int friend= SendMessageToWX.Req.WXSceneSession;
    public static final int friendCircle= SendMessageToWX.Req.WXSceneTimeline;
    public static final int favorite= SendMessageToWX.Req.WXSceneFavorite;
    public static final int QQ= 12345;
    public static final int QZONE = 12346;

    @IntDef({friend,friendCircle,favorite,QQ, QZONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MyShareType{};

}
