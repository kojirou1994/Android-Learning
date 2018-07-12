package com.bb.ui;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bb.R;


/**
 *  课程提醒设置
 * @author Administrator
 *
 */
public class AlarmActivity extends Activity {
	
	TextView onetextview;
	TextView twotextview;	
	TextView threetextview;	
	Button onebutton;

	Dialog dialog = null;
//	SharedPreferences是一个临时存储空间,具体百度
	private SharedPreferences sharedPreferences; 
	
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.alert_clock); 

		onebutton=(Button) findViewById(R.id.onebutton);
		onebutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				弹出设置dialog
				dialog();
			}
		});
 
		onetextview = (TextView) findViewById(R.id.onetextview);
		twotextview = (TextView) findViewById(R.id.twotextview);
		threetextview = (TextView) findViewById(R.id.threetextview);
//		从SharedPreferences获取alarm_record空间
		sharedPreferences = getSharedPreferences("alarm_record", Activity.MODE_PRIVATE); 
//		闹钟管理器
		AlarmManager aManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE); 
//		设置提醒接收类
		Intent intent = new Intent(this,AlarmReceiver.class); 
		intent.setAction("AlarmReceiver");

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0); 
//		定时时间,60s=1分钟
		aManager.setRepeating(AlarmManager.RTC, 0, 60*1000, pendingIntent); 
	}
	
	public void dialog(){
		View view = getLayoutInflater().inflate(R.layout.shijian, null);// 

		final TimePicker timePicker=(TimePicker)view.findViewById(R.id.timepicker); 
		final  EditText  oneeditext=(EditText)view.findViewById(R.id.oneeditext);
		final  EditText  twoeditext=(EditText)view.findViewById(R.id.twoeditext);
		timePicker.setIs24HourView(true);

		new AlertDialog.Builder(this)
			.setTitle("设置")
			.setView(view)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	
				public void onClick(DialogInterface dialog, int which) { 
		
					String timeStr = String.valueOf(timePicker.getCurrentHour())+":"+String.valueOf(timePicker.getCurrentMinute()); 
					
					onetextview.setText("您设置的时间为: "+timeStr); 
					twotextview.setText("您设置的标题为: "+oneeditext.getText().toString()); 
					threetextview.setText("您设置的内容为: "+twoeditext.getText().toString()); 
					
//					将设置的时间,标题,内容保存到SharedPreferences中
					sharedPreferences.edit().putString(timeStr, timeStr).commit(); 
					sharedPreferences.edit().putString("title", oneeditext.getText().toString()).commit(); 
					sharedPreferences.edit().putString("content", twoeditext.getText().toString()).commit(); 
				} 

		}).setNegativeButton("取消", null).show(); 

	}

    
}