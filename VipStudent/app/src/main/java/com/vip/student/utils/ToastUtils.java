package com.vip.student.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by WuYue on 2015/12/15.
 */
public class ToastUtils {
    private static int DURATION_SHORT=2000;
    private static int DURATION_LONG=3000;

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration) {
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration==Toast.LENGTH_SHORT?DURATION_SHORT:DURATION_LONG);

        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
