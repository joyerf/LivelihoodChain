package com.joyerf.livelihoodchain.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.joyerf.livelihoodchain.ContextHolder;
import com.joyerf.livelihoodchain.datastruct.Announcement;
import com.joyerf.livelihoodchain.datastruct.MarketPrice;
import com.joyerf.livelihoodchain.utils.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class PreferenceUtils {
    private Preferences mPreferences;
    private Gson mGson;
    private final static String KEY_ANNOUNCEMENT_CACHE = "announcement_cache";
    private final static String KEY_QUERY_MARKET = "query_market";

    static class LazyHolder{
        private final static PreferenceUtils sInstance = new PreferenceUtils();
    }

    private PreferenceUtils(){
        mPreferences = Preferences.build(ContextHolder.getContext(), "global");
        mGson = new GsonBuilder().create();
    }

    public static PreferenceUtils getInstance(){
        return LazyHolder.sInstance;
    }

    public void setAnnouncement(String json){
        mPreferences.putString(KEY_ANNOUNCEMENT_CACHE, json);
    }

    public Announcement getAnnouncement(){
        String json = mPreferences.getString(KEY_ANNOUNCEMENT_CACHE, null);
        if(json == null){
            return null;
        }
        Announcement announcement = mGson.fromJson(json, Announcement.class);
        return announcement;
    }

    public void setMarketPrices(String json){
        mPreferences.putString(KEY_QUERY_MARKET, json);
    }

    public List<MarketPrice> queryMarket(){
        String json = mPreferences.getString(KEY_QUERY_MARKET, null);
        if(json == null){
            return null;
        }
        return mGson.fromJson(json, new TypeToken<ArrayList<MarketPrice>>() {}.getType());
    }

}
