package com.feng.roomstatusview;

import android.app.Application;

/**
 * Created by feng on 2017/6/15.
 */

public class MyApplication extends Application {


    private static Application sInstance;

    public static Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }
}
