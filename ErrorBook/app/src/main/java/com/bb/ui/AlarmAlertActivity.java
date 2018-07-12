package com.bb.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bb.R;



/* 实际跳出闹铃Dialog的Activity */
public class AlarmAlertActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println(" AlarmAlertActivity 1111 ");
		Bundle  bundle = getIntent().getExtras()  ;
		
		/* 跳出的闹铃警示 */
		new AlertDialog.Builder(AlarmAlertActivity.this)
				.setIcon(R.drawable.clock)
				.setTitle( bundle.getString("title") )
				.setMessage( bundle.getString("content") )
				.setPositiveButton("关闭",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								AlarmAlertActivity.this.finish();
							}
						}).show();
		
	}
	
	
}
