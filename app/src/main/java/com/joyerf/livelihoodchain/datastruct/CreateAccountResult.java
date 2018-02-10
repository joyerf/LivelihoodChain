package com.joyerf.livelihoodchain.datastruct;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiezongchang on 2018/1/28.
 */

public class CreateAccountResult implements Parcelable {
    public String account;

    public String code;

    public String key_name;

    public String key_content;


    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.code);
        dest.writeString(this.key_name);
        dest.writeString(this.key_content);
        dest.writeString(this.msg);
    }

    public CreateAccountResult() {
    }

    protected CreateAccountResult(Parcel in) {
        this.account = in.readString();
        this.code = in.readString();
        this.key_name = in.readString();
        this.key_content = in.readString();
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<CreateAccountResult> CREATOR = new Parcelable.Creator<CreateAccountResult>() {
        @Override
        public CreateAccountResult createFromParcel(Parcel source) {
            return new CreateAccountResult(source);
        }

        @Override
        public CreateAccountResult[] newArray(int size) {
            return new CreateAccountResult[size];
        }
    };
}
