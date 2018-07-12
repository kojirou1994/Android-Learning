package com.bb.ui;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bb.R;
import com.bb.util.Constants;

import edu.self.LoginActivity;
import edu.self.UpdateUserInfoActivity;
import edu.self.component.AppException;
import edu.self.component.Connect;
import edu.self.model.UserInfo;
import edu.self.utils.AppContext;



/**
 * 系统启动类，显示操作
 * @author Administrator
 *
 */
public class MainActivity extends Activity {

 
    private LinearLayout oneRow,    threeRow  ;
	private ProgressDialog dialog ;  
	 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    	dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载用户信息...请稍候");
		dialog.show();

		new Thread(){
			public void run() {
				loadAllUserInfo();
				runOnUiThread(new Runnable() {
					public void run() {  
						for (UserInfo userinfo: list) {
							if (userinfo.getUserId().equals(Constants.userId )) {
								AppContext.userinfo = userinfo; 
								load();
							}  
						} 
						dialog.dismiss();
					}
				});
			};
		}.start();
		
    }

    
    private void load(){
    	
        oneRow = (LinearLayout)findViewById(R.id.one_row);
        oneRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { 
                Intent i = new Intent(MainActivity.this, CuotiListActivity.class);
                startActivity(i);
            }
        });

        LinearLayout two_row = (LinearLayout)findViewById(R.id.two_row);
        two_row.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
        	   showDialog(MainActivity.this); 
           }
        });
        
        threeRow = (LinearLayout)findViewById(R.id.three_row);
        threeRow.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {

               Intent i = new Intent(MainActivity.this, AlarmActivity.class);
               startActivity(i);
           }
        });

        LinearLayout four_row = (LinearLayout)findViewById(R.id.four_row);
        four_row.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
             Intent i = new Intent(MainActivity.this, UpdateUserInfoActivity.class);
             startActivity(i);
           }
        });
 
        
        LinearLayout exit_row = (LinearLayout)findViewById(R.id.exit_row);
        exit_row.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
             Intent i = new Intent(MainActivity.this, LoginActivity.class);
             startActivity(i);
             finish();
           }
        }); 
        
    }
    

	private void showDialog( Context context ){
		
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompts_search, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);
		
		final EditText et_search_name = (EditText) promptsView.findViewById(R.id.search_name); 
		
		alertDialogBuilder
			.setCancelable(false)
			.setTitle("查询")
			.setPositiveButton("提交",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int pid) {

		             Intent all = new Intent( MainActivity.this, CuotiListActivity.class); 
		             all.putExtra( "type",  et_search_name.getText().toString()  );
		             startActivity(all);
			    }
		});
		
		alertDialogBuilder.setNegativeButton("取消",  
	            new DialogInterface.OnClickListener() {  
	                public void onClick(DialogInterface dialog, int whichButton) {  
	                     
	                }  
		  });
	
		AlertDialog alertDialog = alertDialogBuilder.create(); 
		alertDialog.show(); 
	}
	
	
    
  
  	private List<UserInfo> list;

      /**
  	 * 加载所有用户的信息
  	 */
  	private void loadAllUserInfo() {
  		list = new ArrayList<UserInfo>();
  		Connect connect = new Connect(AppContext.SERVER_USERS, AppContext.HTTP_POST);
  		try {
  			byte[] data = connect.queryServer(null);
  			JSONObject object = new JSONObject(new String(data, "gb2312"));
  			JSONArray userArray = object.getJSONArray("users");
  			for (int i = 0;i < userArray.length();i++) {
  				JSONObject userObject = userArray.getJSONObject(i);
  				UserInfo userinfo = new UserInfo();
  				userinfo.setUid(userObject.getInt("uid"));
  				userinfo.setPassword(userObject.getString("password"));
  				userinfo.setUserId(userObject.getString("userId"));
  				userinfo.setUserName(userObject.getString("userName"));
  				userinfo.setAddress(userObject.getString("address"));
  				userinfo.setPhone(userObject.getString("phone"));
  				list.add(userinfo);
  			}
  		} catch (AppException e) {
  			e.printStackTrace();
  		} catch (JSONException e) {
  			e.printStackTrace();
  		} catch (UnsupportedEncodingException e) {
  			e.printStackTrace();
  		}
  	}

	
}
