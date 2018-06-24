package com.example.backfire.myapp.utils;

import android.content.Context;

/**
 * Created by backfire on 2018/6/23.
 */

public class AppUtil {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
