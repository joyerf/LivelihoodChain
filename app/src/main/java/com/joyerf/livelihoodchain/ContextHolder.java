package com.joyerf.livelihoodchain;

import android.app.Application;
import android.content.Context;

/**
 * ApplicationContext持有者
 * Created by keitacai on 15/8/10.
 */
public class ContextHolder {
    private static Context sApplicationContext;

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     * @param context
     */
    public static void setContext(Context context) {
        sApplicationContext = context;
    }

    /**
     * 获取应用ApplicationContext
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (sApplicationContext == null) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return sApplicationContext;
    }
}
