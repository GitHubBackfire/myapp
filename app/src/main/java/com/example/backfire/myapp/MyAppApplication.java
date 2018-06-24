package com.example.backfire.myapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by backfire on 2018/6/23.
 */

public class MyAppApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getmContext() {
        return mContext;
    }
}


