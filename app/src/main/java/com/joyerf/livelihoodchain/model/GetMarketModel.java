package com.joyerf.livelihoodchain.model;

import com.google.gson.reflect.TypeToken;
import com.joyerf.livelihoodchain.datastruct.MarketPrice;
import com.joyerf.livelihoodchain.utils.PreferenceUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiezongchang on 2018/2/7.
 */

public class GetMarketModel extends BaseModel implements Constants{
    public interface OnGetMarketListener{
        void onGetMarket(List<MarketPrice> prices);
    }

    public void getMarket(final OnGetMarketListener listener){
        final Request request = new Request.Builder()
                .url(DOMAIN_URL + QUERY_MARKET)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onGetMarket(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
//                Logger.t("getMarket").json(responseStr);
                List<MarketPrice> priceList = gson.fromJson(responseStr, new TypeToken<ArrayList<MarketPrice>>() {}.getType());
                if(priceList != null && priceList.size() > 0){
                    listener.onGetMarket(priceList);
                    PreferenceUtils.getInstance().setMarketPrices(responseStr);
                } else {
                    listener.onGetMarket(null);
                }
            }
        });
    }
}
