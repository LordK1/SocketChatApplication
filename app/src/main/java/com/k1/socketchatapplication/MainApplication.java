package com.k1.socketchatapplication;

import android.app.Application;
import android.util.Log;

/**
 * Created by k1 on 12/13/15.
 */
public class MainApplication extends Application {

    public static final String TAG = "TAG";
    public static final String BASE_URL = "http://192.168.1.4:3000/";
    public static final String LOGIN_URL = BASE_URL + "/api/login";
    //    public static final String BASE_URL = "https://socketer.herokuapp.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"MainApplication Created !!!");

    }



}
