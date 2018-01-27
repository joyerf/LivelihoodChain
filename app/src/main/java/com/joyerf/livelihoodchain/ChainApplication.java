package com.joyerf.livelihoodchain;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.joyerf.livelihoodchain.db.GreenDaoManager;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class ChainApplication extends MultiDexApplication {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }

    public static Context getContext(){
        return sContext;
    }
}
