package com.joyerf.livelihoodchain.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class ToastUtils {

    public static void showToast(final Context context, final CharSequence charSequence){
        if(context != null){
            if(ThreadUtils.isMainThread()) {
                Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
            } else {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public static void showToast(final Context context, final int resId){
        if(context != null){
            if(ThreadUtils.isMainThread()) {
                Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
            } else {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
