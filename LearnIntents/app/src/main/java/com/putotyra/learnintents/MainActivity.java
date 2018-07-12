package com.putotyra.learnintents;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private Button btnStartAty1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartAty1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setComponent(new ComponentName("com.putotyra.learnintents", "com.putotyra.learnintents.Aty1"));

//                Intent i = new Intent("com.putotyra.learnintents.intent.action.Aty1");
//                startActivity(i);
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("NO EXTERNAL STORAGE PERMISSION");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "1.jpg");
                System.out.println(f);
                System.out.println(f.isFile());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(f), "image/*");
                startActivity(i);
            }
        });

        findViewById(R.id.btnDel10086).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("tel:10086"));
                startActivity(i);
            }
        });

        findViewById(R.id.btnOpenUrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
                startActivity(i);
            }
        });
    }
}
