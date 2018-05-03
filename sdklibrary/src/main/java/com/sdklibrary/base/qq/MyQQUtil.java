package com.sdklibrary.base.qq;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/4/28.
 */

public class MyQQUtil {
    public static boolean noExists(String filePath){
        return !isExists(filePath);
    }
    public static boolean isExists(String filePath){
        if(TextUtils.isEmpty(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return file != null && file.exists();
        }
    }
}
