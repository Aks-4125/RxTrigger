package com.android.aks4125.rxtrigger;

import android.app.Application;

import com.android.aks4125.rxtrigger.trigger.RxTrigger;

/**
 * Created by akashb on 14-11-2017.
 */

public class App extends Application {

    private RxTrigger rxTrigger;
    private App mApp;

    public App getApp() {
        return (App) getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rxTrigger = new RxTrigger();
    }


    public RxTrigger reactiveTrigger() {
        return rxTrigger;
    }


}
