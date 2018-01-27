package com.joyerf.livelihoodchain.model;

import android.widget.Toast;

import com.joyerf.livelihoodchain.MainActivity;
import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.datastruct.ChainMoney;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class GetMoneyModel extends BaseModel implements  Constants {
    public interface OnGetMoneyListener{
        void onGetMoney(ChainMoney money);
    }

    public void getMoney(String accountName, final OnGetMoneyListener listener){
        final Request request = new Request.Builder()
                .url(DOMAIN_URL + GET_MONEY + accountName)
                .header(TOKEN_KEY, QSUNIPAY_TOKEN)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onGetMoney(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ChainMoney money = gson.fromJson(response.body().string(), ChainMoney.class);
                listener.onGetMoney(money);
            }
        });
    }
}
