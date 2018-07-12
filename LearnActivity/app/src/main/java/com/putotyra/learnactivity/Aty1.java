package com.putotyra.learnactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kojirou on 16/5/3.
 */
public class Aty1 extends AppCompatActivity {

    private Button btnClode;
    private TextView tvOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty1);
        btnClode = (Button) findViewById(R.id.btnClose);
        btnClode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("result", "Hello MainActvity");
                setResult(0, data);

                finish();
            }
        });

        tvOut = (TextView) findViewById(R.id.tvOut);
//        tvOut.setText(getIntent().getStringExtra("txt"));

        Bundle data = getIntent().getExtras();
        String txt = data.getString("txt");
        tvOut.setText(txt);
    }

}
