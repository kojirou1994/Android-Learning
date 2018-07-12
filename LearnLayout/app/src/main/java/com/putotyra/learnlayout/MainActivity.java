package com.putotyra.learnlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private LinearLayout mainLayout;
    private View.OnClickListener btnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainLayout.removeView(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        for (int i = 0; i < 5; i++) {
            btn = new Button(this);
            btn.setText("Remove ME " + i);
            btn.setOnClickListener(btnClickHandler);
//            mainLayout.addView(btn);
            mainLayout.addView(btn, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

    }
}
