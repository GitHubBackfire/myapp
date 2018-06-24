package com.example.backfire.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by backfire on 2017/9/18.
 */

public class SharePreferenceUtil {

    private static final String NetworkTraffic = "NetworkTraffic";
    private SharePreferenceUtil(){}

    /**
     * 0代表省电，1代表普通模式
     * @param context
     * @return
     */
    public static int getNetworkTraffic(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(NetworkTraffic,-1);
    }
    public static void putNetworkTraffic(Context context,int t) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NetworkTraffic, t);
        editor.commit();
    }
}
