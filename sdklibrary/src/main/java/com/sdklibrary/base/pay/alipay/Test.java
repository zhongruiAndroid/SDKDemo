package com.sdklibrary.base.pay.alipay;

import android.app.Activity;

import com.sdklibrary.base.pay.wxpay.MyWXPayCallback;
import com.sdklibrary.base.pay.wxpay.MyWXPay;

/**
 * Created by Administrator on 2018/3/8.
 */

public class Test {
    public void aliPay(Activity activity) {
        AliPayOrderBean bean=new AliPayOrderBean();
        MyWXPay.newInstance(activity).startPay(null);
        //或者
        MyWXPay.newInstance(activity).startPay(null, new MyWXPayCallback() {
            @Override
            public void paySuccess() {

            }

            @Override
            public void payFail() {

            }

            @Override
            public void payCancel() {

            }
        });
    }
}
