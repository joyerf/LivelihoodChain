package com.joyerf.livelihoodchain.model;

import com.joyerf.livelihoodchain.datastruct.CreateAccountResult;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class CreateAccountModel extends BaseModel implements Constants {

    public interface OnCreateAccountListener {
        void onCreateAccount(CreateAccountResult account);
    }

    public void createAccount(String pwd, final OnCreateAccountListener listener){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{\"password\":\"" + pwd + "\"}");
        final Request request = new Request.Builder()
                .url(DOMAIN_URL + CREATE_ACCOUNT_PATH)
                .header(TOKEN_KEY, QS_UNI_PAY_TOKEN)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onCreateAccount(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Logger.t("CreateAccountModel").json(responseStr);
                CreateAccountResult account = gson.fromJson(responseStr, CreateAccountResult.class);
                listener.onCreateAccount(account);
            }
        });
    }
}
