package com.example.onlinebankapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "broadcastReceiver Triggered", Toast.LENGTH_SHORT)
                .show();
    }
}
