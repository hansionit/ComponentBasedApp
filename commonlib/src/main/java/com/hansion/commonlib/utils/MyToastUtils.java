package com.hansion.commonlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.hansion.commonlib.BaseApplication;

/**
 * Description：
 * Author: Hansion
 * Time: 2018/1/25 15:12
 */
public class MyToastUtils {

    private static Toast mToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void showShortToast(int resourceID) {
        String msg = BaseApplication.Companion.getAppContext().getApplicationContext().getResources().getString(resourceID);// 用于显示的文字
        showToast(msg, Toast.LENGTH_SHORT);


    }


    public static void showShortToast(final String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }


    public static void showLongToast(int resourceID) {
        String msg = BaseApplication.Companion.getAppContext().getApplicationContext().getResources().getString(resourceID);// 用于显示的文字
        showToast(msg, Toast.LENGTH_LONG);
    }


    public static void showLongToast(final String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    private static void showToast(final String msg, final int duration) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        sHandler.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                Context context = BaseApplication.Companion.getAppContext().getApplicationContext();
                if (mToast == null) {
                    mToast = Toast.makeText(context, msg, duration);
                } else {
                    mToast.cancel();
                    mToast = Toast.makeText(context, msg, duration);
                }
                mToast.show();
            }
        });
    }

}
