package com.joyerf.livelihoodchain.model;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public interface Constants {
    String DOMAIN_URL = "http://183.60.143.87:8060";
    String TOKEN_KEY = "qsunipay-token";
    String QS_UNI_PAY_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    String CREATE_ACCOUNT_PATH = "/wallet/createWallet";
    String GET_MONEY = "/wallet/balance?account=";
    String NEW_ACTIVITY = "/activity/newActivity";
    String QUERY_MARKET = "/transaction/queryMarket";
}
