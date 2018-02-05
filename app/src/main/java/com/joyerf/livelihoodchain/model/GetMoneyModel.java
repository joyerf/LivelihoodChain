package com.joyerf.livelihoodchain.model;

import android.text.TextUtils;
import android.util.Log;

import com.joyerf.livelihoodchain.datastruct.ChainAccount;
import com.joyerf.livelihoodchain.datastruct.ChainMoney;
import com.joyerf.livelihoodchain.db.GreenDaoManager;
import com.joyerf.livelihoodchain.gen.ChainAccountDao;

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
        void onGetMoney(String accountName, ChainMoney money);
    }

    public void getMoney(final String accountName, final OnGetMoneyListener listener){
        if(TextUtils.isEmpty(accountName)){
            return;
        }
        final Request request = new Request.Builder()
                .url(DOMAIN_URL + GET_MONEY + accountName)
                .header(TOKEN_KEY, QSUNIPAY_TOKEN)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onGetMoney(accountName, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("GetMoneyModel", accountName + " onResponse " + responseStr);
                ChainMoney money = gson.fromJson(responseStr, ChainMoney.class);
                modifyChainAccountMoney(accountName, money);
                listener.onGetMoney(accountName, money);
            }
        });
    }

    private void modifyChainAccountMoney(final String accountName, ChainMoney money){
        if(money == null || TextUtils.isEmpty(money.getBalance())){
            return;
        }
        money.setAccount(accountName);
        GreenDaoManager.getInstance().getDaoSession().insertOrReplace(money);
        //写入账号信息
        ChainAccountDao accountDao = GreenDaoManager.getInstance().getDaoSession().getChainAccountDao();
        ChainAccount account = accountDao.queryBuilder().where(ChainAccountDao.Properties.Account.eq(money.getAccount())).build().unique();
        if(account != null){
            account.setMoney(money.getBalance());
            accountDao.update(account);
        }
    }
}
