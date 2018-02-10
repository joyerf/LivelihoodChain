package com.joyerf.livelihoodchain.utils.preferences;

import android.content.Context;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 应用程序设置信息。
 * <p>
 * 原则上如果不是应用全局的设置，每个模块内部的配置使用自己的配置。使用 {@link Preferences#build(Context, String)}
 * 访问配置。
 * </p>
 */
public class Preferences extends CachePreference {

    /** 默认配置文件名。 */
    public static final String SP_NAME = "livelihoodchain";

    private static final ConcurrentHashMap<String, Preferences> cache = new ConcurrentHashMap<String, Preferences>();

    private Preferences(Context context, String cfgName) {
        super(context, cfgName);
    }

    /**
     * 构建 Preferences 实例。
     *
     * @param cfgName 配置文件名称
     */
    public static synchronized Preferences build(Context context, String cfgName) {
        if (cache.containsKey(cfgName)) {
            return cache.get(cfgName);
        } else {
            Preferences preferences = new Preferences(context, cfgName);
            cache.put(cfgName, preferences);
            return preferences;
        }
    }

    /**
     * 使用默认名称("map_pref") 构建 Preferences 实例。
     */
    public static Preferences build(Context context) {
        return build(context, SP_NAME);
    }

}
