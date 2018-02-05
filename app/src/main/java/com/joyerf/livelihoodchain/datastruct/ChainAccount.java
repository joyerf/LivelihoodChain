package com.joyerf.livelihoodchain.datastruct;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jiezongchang on 2018/1/28.
 */

@Entity
public class ChainAccount {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    String account;

    @Property(nameInDb = "key_name")
    String key_name;

    @Property(nameInDb = "key_content")
    String key_content;

    @Property(nameInDb = "money")
    String money;

    @Generated(hash = 1384628016)
    public ChainAccount() {
    }

    @Keep
    public ChainAccount(CreateAccountResult result) {
        this.account = result.account;
        this.key_name = result.key_name;
        this.key_content = result.key_content;
    }

    @Generated(hash = 1111649233)
    public ChainAccount(Long id, String account, String key_name,
            String key_content, String money) {
        this.id = id;
        this.account = account;
        this.key_name = key_name;
        this.key_content = key_content;
        this.money = money;
    }

    @Override
    public String toString() {
        return "ChainAccount{" +
                "account='" + account + '\'' +
                ", key_name='" + key_name + '\'' +
                ", key_content='" + key_content + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getKey_name() {
        return this.key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }

    public String getKey_content() {
        return this.key_content;
    }

    public void setKey_content(String key_content) {
        this.key_content = key_content;
    }

    public String getMoney(){
        return this.money;
    }

    public void setMoney(String money){
        this.money = money;
    }
}
