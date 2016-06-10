package com.crystal.startservicefromanother.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.crystal.startservicefromanother.IAppServiceRemoteBinder;

/**
 * Created by xpchi on 2016/6/10.
 */
public class AppService extends Service{

    private static final String TAG = AppService.class.getSimpleName();

    private AppServiceRemoteBinder mAppServiceRemoteBinder;

    private String data;

    private boolean isRunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAppServiceRemoteBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppServiceRemoteBinder = new AppServiceRemoteBinder();
        Log.d(TAG, "service has created");
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning){
                    Log.d(TAG, "data is " + data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppServiceRemoteBinder = null;
        Log.d(TAG, "service has destroied");
    }

    public class AppServiceRemoteBinder extends IAppServiceRemoteBinder.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void passMsg(String msg) throws RemoteException {
            data = msg;
        }
    }
}
