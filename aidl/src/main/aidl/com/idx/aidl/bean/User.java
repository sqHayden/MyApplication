package com.idx.aidl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hayden on 18-6-19.
 */

public class User implements Parcelable {

    private String account;
    private int password;

    public User(String account, int password) {
        this.account = account;
        this.password = password;
    }

    protected User(Parcel in) {
        account = in.readString();
        password = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(account);
        dest.writeInt(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password=" + password +
                '}';
    }
}
