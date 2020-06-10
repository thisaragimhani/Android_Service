package com.example.onlinebankapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class Postmaster extends Service {
    @androidx.annotation.Nullable
    private BroadcastReceiver broadcastReceiver;
    private static final String TAG = "17020621";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        String tmp = extras.getString("name");
        System.out.println("=====================================================");
        System.out.println(tmp);
        System.out.println("=====================================================");
        broadcastReceiver = new ConnectionReceiver();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED" );
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED" );
        registerReceiver(broadcastReceiver, intentFilter);
    }


}

