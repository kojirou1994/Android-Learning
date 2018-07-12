package com.bb.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bb.R; 
import com.bb.util.Constants;

import edu.self.UpActivity;
import edu.self.utils.AppContext;



/**
 * 添加
 *
 */
public class TestActivity extends Activity {

	private EditText ed_mingcheng; 
	
	private EditText ed_miaoshu; 
	private EditText ed_tupian; 
  
	private Button btnOk; 
	

	private Spinner mySpinner; 
    private String leibie ;
	
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_tupian);
		 
		ed_mingcheng = (EditText) this.findViewById(R.id.mingcheng);


		mySpinner = (Spinner) this.findViewById(R.id.fenlei);
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
            
                String[] languages = getResources().getStringArray(R.array.languages);
                leibie = languages[pos];
                Toast.makeText( TestActivity.this, "你点击的是:"+languages[pos], 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
            
        });
		
		ed_tupian = (EditText) this.findViewById(R.id.tupian);
		ed_tupian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( TestActivity.this, UpActivity.class  );
				startActivityForResult( intent , 300 );
			}
		});

		
		ed_miaoshu = (EditText) this.findViewById(R.id.miaoshu);

	     
		btnOk = (Button) findViewById(R.id.button1);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showDialog(0);
				new Thread(){
					public void run() {
						try {
							JSONObject jsonObject = new JSONObject();
						    jsonObject.put("yonghu", AppContext.userinfo.getUserName()  );
						    jsonObject.put("mingcheng", ed_mingcheng.getText().toString());
						    jsonObject.put("luduan", "" );
						    jsonObject.put("fenlei", leibie );
						    jsonObject.put("miaoshu", ed_miaoshu.getText().toString());
						    jsonObject.put("tupian", ed_tupian.getText().toString());
 
						    jsonObject.put("shijian", "" );
						    jsonObject.put("issh", "否" );
						     
						    jsonObject.put("uid", Constants.userId );
							
							
							HttpPost post = new HttpPost( Constants.SERVER + "/guzhang.do?method=saveJson");
							
							List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
							params.add(new BasicNameValuePair("guzhang", jsonObject.toString()));
							
							post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
							post.getParams().setBooleanParameter( CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
							HttpResponse response = (HttpResponse) new DefaultHttpClient().execute(post);
							
							if (response != null) {
								if (200 == response.getStatusLine().getStatusCode()) {
									InputStream is = response.getEntity().getContent();
									Reader reader = new BufferedReader(new InputStreamReader(is));
									StringBuilder builder = new StringBuilder((int) response.getEntity().getContentLength());
									char[] temp = new char[4000];
									int len = 0;
									while ((len = reader.read(temp)) != -1) {
										builder.append(temp, 0, len);
									}
									reader.close();
									is.close();
									final String content = builder.toString();
									response.getEntity().consumeContent();
									runOnUiThread(new Runnable() {
										public void run() {
											removeDialog(0);
											if ( !content.trim().equals("ERROR") ) {
												TestActivity.this.finish();
												Toast.makeText(TestActivity.this, "添加成功", Toast.LENGTH_LONG).show();
											} else {
												Toast.makeText(TestActivity.this, "添加失败", Toast.LENGTH_LONG).show();
											}
										}
									});
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
	}
	
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if( resultCode == RESULT_OK ){
			if( requestCode == 300 ){
				if( data != null ){
					Bundle bundle = data.getExtras();
					ed_tupian.setText( bundle.getString("imgPath").trim() ) ;
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	 
	
	
	
}
