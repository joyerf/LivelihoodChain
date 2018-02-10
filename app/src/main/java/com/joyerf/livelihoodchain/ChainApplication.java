package com.joyerf.livelihoodchain;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.orhanobut.logger.Logger;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class ChainApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ContextHolder.setContext(this);
        GreenDaoManager.getInstance();
        Logger.init("Chain");
    }
}
