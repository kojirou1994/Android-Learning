package com.putotyra.learnbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    public static final String ACTION = "com.putotyra.learnbroadcast.intent.action.MyBC";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        System.out.println("onReceive, data = " + intent.getStringExtra("txt"));
    }
}
