package com.joyerf.livelihoodchain.utils;

import android.os.Handler;
import android.os.Looper;

public final class ThreadUtils {
    private static Thread sMainThread = Looper.getMainLooper().getThread();
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    private ThreadUtils() {
        // static usage.
    }

    /**
     * <p>Causes the Runnable to be added to the <b>MAIN</b> message queue.
     * The runnable will be run on the user interface thread.</p>
     *
     * @param r The Runnable that will be executed.
     * @see #postDelayed
     * @see #removeCallbacks
     */
    public static void post(Runnable r) {
        sMainHandler.post(r);
    }

    /**
     * <p>Causes the Runnable to be added to the <b>MAIN</b> message queue, to be run
     * after the specified amount of time elapses.
     * The runnable will be run on the user interface thread.</p>
     *
     * @param r           The Runnable that will be executed.
     * @param delayMillis The delay (in milliseconds) until the Runnable
     *                    will be executed.
     * @see #post
     * @see #removeCallbacks
     */
    public static void postDelayed(Runnable r, long delayMillis) {
        sMainHandler.postDelayed(r, delayMillis);
    }

    /**
     * <p>Removes the specified Runnable from the <b>MAIN</b> message queue.</p>
     *
     * @param r The Runnable to remove from the message handling queue
     * @see #post
     * @see #postDelayed
     */
    public static void removeCallbacks(Runnable r) {
        sMainHandler.removeCallbacks(r);
    }

    /**
     * Run this runnable in  <b>MAIN</b> ui thread.
     *
     * @param runnable Runnable to run.
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * Whether current thread is <b>MAIN</b> ui thread.
     */
    public static boolean isMainThread() {
        return sMainThread == Thread.currentThread();
    }

    /**
     * <p>Get the <b>MAIN</b> handler.</p>
     *
     * @return Main handler.
     */
    public static Handler getMainHandler() {
        return sMainHandler;
    }
}
