package com.joyerf.livelihoodchain.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class BaseModel {
    protected static OkHttpClient okHttpClient = new OkHttpClient();
    protected static Gson gson = new GsonBuilder().create();
}
