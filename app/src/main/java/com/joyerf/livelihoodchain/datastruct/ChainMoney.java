package com.joyerf.livelihoodchain.datastruct;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class ChainMoney {
    public String balance;

    public String getBalance(){
        return balance;
    }

    @Override
    public String toString() {
        return "ChainMoney{" +
                "balance='" + balance + '\'' +
                '}';
    }
}
