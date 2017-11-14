package com.qinlei.test;

import android.app.Application;

/**
 * Created by qinlei
 * Created on 2017/11/14
 * Created description :
 */

public class App extends Application {
    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
