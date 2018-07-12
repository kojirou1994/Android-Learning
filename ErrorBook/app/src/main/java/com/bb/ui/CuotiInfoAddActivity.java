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

import edu.self.UpActivity;
import edu.self.utils.AppContext;

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bb.util.Constants;
import com.bb.R;


/**
 * 添加
 *
 */
public class CuotiInfoAddActivity extends Activity {

	private EditText ed_mingcheng; 
	private EditText ed_tupian; 
	private EditText ed_yuanyin; 
	private EditText ed_leixing; 
	private EditText ed_jieda; 
	private EditText ed_silu; 
	private EditText ed_fangfa; 
	private EditText ed_yonghu; 
	private EditText ed_shijian; 
  
	private Button btnOk; 
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cuoti_info_add);
		
		ed_mingcheng = (EditText) this.findViewById(R.id.mingcheng);

		ed_tupian = (EditText) this.findViewById(R.id.tupian);
		ed_tupian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( CuotiInfoAddActivity.this, UpActivity.class  );
				startActivityForResult( intent , 300 );
			}
		});
		
		
		ed_yuanyin = (EditText) this.findViewById(R.id.yuanyin);
		ed_leixing = (EditText) this.findViewById(R.id.leixing);
		ed_jieda = (EditText) this.findViewById(R.id.jieda);
		ed_silu = (EditText) this.findViewById(R.id.silu);
		ed_fangfa = (EditText) this.findViewById(R.id.fangfa);
		ed_yonghu = (EditText) this.findViewById(R.id.yonghu);
		ed_shijian = (EditText) this.findViewById(R.id.shijian);
	     
		btnOk = (Button) findViewById(R.id.button1);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showDialog(0);
				new Thread(){
					public void run() {
						try {
							JSONObject jsonObject = new JSONObject();
						    jsonObject.put("mingcheng", ed_mingcheng.getText().toString());
						    jsonObject.put("tupian", ed_tupian.getText().toString());
						    jsonObject.put("yuanyin", ed_yuanyin.getText().toString());
						    jsonObject.put("leixing", ed_leixing.getText().toString());
						    jsonObject.put("jieda", ed_jieda.getText().toString());
						    jsonObject.put("silu", ed_silu.getText().toString());
						    jsonObject.put("fangfa", ed_fangfa.getText().toString());
						    jsonObject.put("yonghu", AppContext.userinfo.getUserName());
							jsonObject.put("shijian", "");
						     
						    jsonObject.put("uid", Constants.userId );
							
							
							HttpPost post = new HttpPost( Constants.SERVER + "/cuoti.do?method=saveJson");
							
							List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
							params.add(new BasicNameValuePair("cuoti", jsonObject.toString()));
							
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
												CuotiInfoAddActivity.this.finish();
												Toast.makeText(CuotiInfoAddActivity.this, "添加成功", Toast.LENGTH_LONG).show();
											} else {
												Toast.makeText(CuotiInfoAddActivity.this, "添加失败", Toast.LENGTH_LONG).show();
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
