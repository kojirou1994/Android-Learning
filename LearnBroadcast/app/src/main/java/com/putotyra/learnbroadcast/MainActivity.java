package com.putotyra.learnbroadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button btnSendBroadcast, btnRegisterBCR, btnUnregisterBCR;

    private final MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendBroadcast = (Button) findViewById(R.id.btnSendBreadcast);
        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, MyReceiver.class);
                Intent i = new Intent(MyReceiver.ACTION);
                i.putExtra("txt", "Hello Receiver");

                sendBroadcast(i);
            }
        });

        btnRegisterBCR = (Button) findViewById(R.id.btnRegisterBCR);
        btnUnregisterBCR = (Button) findViewById(R.id.btnUnregisterBCR);

        btnRegisterBCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(myReceiver, new IntentFilter(MyReceiver.ACTION));
            }
        });

        btnUnregisterBCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(myReceiver);
            }
        });
    }
}
