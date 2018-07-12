package com.bb.ui; 


import java.util.Calendar; 
import android.app.Activity; 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.SharedPreferences; 
import android.os.Bundle;



public class AlarmReceiver extends BroadcastReceiver { 

	/** 
	 * 
	* 通过广播进行扫描，是否到达时间后再响起闹铃 
	* 
	* */ 
	@Override 
	public void onReceive(Context context, Intent intent) { 
		
		SharedPreferences sharedPreferences = context.getSharedPreferences("alarm_record", Activity.MODE_PRIVATE); 
		
		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); 
		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)); 

//		根据当前时间,从SharedPreferences获取值,判断是否到达时间
		String time = sharedPreferences.getString(hour+":"+minute, null);//小时与分
		String title = sharedPreferences.getString("title",null);
		String content = sharedPreferences.getString("content",null);
		
		if( time != null ){//判断是否为空，然后通过创建，  
		    /* create Intent，调用AlarmAlertActivity报警 */
		    Intent i = new Intent(context, AlarmAlertActivity.class);
		    Bundle bundleRet = new Bundle();
		    bundleRet.putString("title", title );
		    bundleRet.putString("content", content );
		    i.putExtras(bundleRet);
		    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    context.startActivity(i);
		} 
		 
	} 

	
} 
