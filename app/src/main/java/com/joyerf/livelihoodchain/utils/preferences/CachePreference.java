package com.joyerf.livelihoodchain.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class CachePreference {

    private final SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;

    public CachePreference(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = preferences.edit();
    }

    public boolean putString(String key, String value) {
        mEditor.putString(key,value).apply();
        return true;
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key,defaultValue);
    }

    public boolean putInt(String key, int value) {
        mEditor.putInt(key,value).apply();
        return true;
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key,defaultValue);
    }

    public boolean putFloat(String key, float value) {
        mEditor.putFloat(key,value).apply();
        return true;
    }

    public float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key,defaultValue);
    }

    public boolean putBoolean(String key, boolean value) {
        mEditor.putBoolean(key,value).apply();
        return true;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key,defaultValue);
    }

    public boolean putLong(String key, long value) {
        mEditor.putLong(key,value).apply();
        return true;
    }

    public Long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

    public boolean clear() {
        return mEditor.clear().commit();
    }

    public boolean removeKey(String key) {
        return mEditor.remove(key).commit();
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

}
