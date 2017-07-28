package com.aidl.tsnt.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tingshuonitiao on 2017/7/28.
 */

public class RemoteService extends Service {
    private List<Dog> mDogs = new ArrayList<>();

    Binder mBinder = new IMyAidl.Stub() {

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }

        @Override
        public List<Dog> addDog(Dog dog) throws RemoteException {
            mDogs.add(dog);
            return mDogs;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
