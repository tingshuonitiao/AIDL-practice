package com.aidl.tsnt.server;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tingshuonitiao on 2017/7/28.
 */

public class Dog implements Parcelable {
    int age;

    public Dog(int age) {
        this.age = age;
    }

    protected Dog(Parcel in) {
        age = in.readInt();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
    }
}
