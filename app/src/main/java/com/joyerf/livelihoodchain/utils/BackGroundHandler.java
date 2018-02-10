package com.joyerf.livelihoodchain.utils;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BackGroundHandler创建后台线程，并创建了Looper、消息队列，由于后台线程事件分发和处理
 *
 * @author joycejie
 */
public class BackGroundHandler extends Handler {
    public BackGroundHandler(String name) {
        this(name, 1);
    }

    public BackGroundHandler(String name, int priority) {
        super(startHandlerThread(name, priority).getLooper());
    }

    private static HandlerThread startHandlerThread(String name, int priority) {
        final AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
        HandlerThread ht = new HandlerThread(name, priority) {
            protected void onLooperPrepared() {
                localAtomicBoolean.compareAndSet(false, true);
            }
        };
        ht.start();
        while (!localAtomicBoolean.get()) ;
        return ht;
    }
}
