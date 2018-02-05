package com.joyerf.livelihoodchain.datastruct;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jiezongchang on 2018/1/28.
 */
@Entity
public class ChainMoney {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String account;

    @Property(nameInDb = "balance")
    private String balance;

    @Generated(hash = 604583666)
    public ChainMoney(Long id, String account, String balance) {
        this.id = id;
        this.account = account;
        this.balance = balance;
    }

    @Generated(hash = 781292915)
    public ChainMoney() {
    }

    public String getBalance(){
        return balance;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "ChainMoney{" +
                "account='" + account + "\'," +
                "balance='" + balance + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
