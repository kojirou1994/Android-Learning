package edu.self;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bb.R;
import com.bb.ui.CuotiInfoAddActivity;

import edu.self.utils.AppContext;

/**
 * 
 *  
		ed_tupian = (EditText) this.findViewById(R.id.tupian);
		ed_tupian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( GuzhangInfoAddActivity.this, UpActivity.class  );
				startActivityForResult( intent , 300 );
			}
		});

		
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
		
		
		
 * @author bj
 *
 */
public class UpActivity extends Activity {
	

	private static final int REQ_CODE_CAMERA = 100 ;
	private static final int REQ_CODE_PICTURES = 101 ; 
	
	private String imgPath ;

	private EditText etImgPath ;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.up);
      
        etImgPath = (EditText) findViewById(R.id.tupian);
        
		( (Button) findViewById(R.id.submit)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});

		((Button) findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				UpActivity.this.finish();
			}
		});
		
        uploadByPhoto();
    }
    

	private ProgressDialog dialog;
	
    public void submit(){
      	
		if ( etImgPath.getText().toString().trim().equals("") ) {
			Toast.makeText(UpActivity.this, "图片不能为空", Toast.LENGTH_LONG).show();
			return;
		}

    	dialog = new ProgressDialog(this);
		dialog.setMessage("上传中...请稍候");
		dialog.show();
		
		new Thread(){
			public void run() {
				try {
					JSONObject jsonObject = new JSONObject(); 
					MultipartEntity mpEntity = new MultipartEntity();   
				  
					if ( imgPath!= null && !imgPath.equals("")) {   
			            FileBody file = new FileBody(new File(imgPath));   
			            mpEntity.addPart("photo", file);   
			        }  

					mpEntity.addPart("resource", new StringBody( jsonObject.toString(), Charset.forName(HTTP.UTF_8) ) );   
					  
					HttpPost post = new HttpPost(AppContext.SERVER + "/up");
					 
					// 发送请求体   
			        post.setEntity(mpEntity);  

					post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
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
							
							System.out.println( content + "===imgPath===" + imgPath );
							response.getEntity().consumeContent();
							
							runOnUiThread(new Runnable() {
								public void run() { 

										dialog.dismiss();
									
										Intent intent = new Intent( UpActivity.this, CuotiInfoAddActivity.class);  
						                intent.putExtra("imgPath", content);  
										UpActivity.this.setResult( RESULT_OK , intent );
										UpActivity.this.finish();
								}
							});
						}else{
//							runOnUiThread(new Runnable() {
//								public void run() {
//									dialog.dismiss();
//									Toast.makeText( UpActivity.this, "失败！", Toast.LENGTH_LONG).show();
//								}
//							});
						}
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
    	
    }
    
    

    public void uploadByPhoto(){			
    		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
    		startActivityForResult(i, REQ_CODE_PICTURES);
    }
    
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK) {
    		switch(requestCode) {
    			case REQ_CODE_CAMERA:
    				 
    				switch (resultCode) {
    					case Activity.RESULT_OK://照相完成点击确定            
    					String sdStatus = Environment.getExternalStorageState();                   
    					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用      
    	                  Log.v("TestFile",        "SD card is not avaiable/writeable right now.");   
    	                  return;     
    					}
    					Bundle bundle = data.getExtras();     
    					Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式     
    					FileOutputStream b = null;    
    					File file = new File("/sdcard/pk4fun/");     
    					file.mkdirs();// 创建文件夹，名称为pk4fun     // 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。细心的同学还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现sd卡中myimage这个文件夹下，会保存刚刚调用相机拍出来的照片，照片名称不会重复。     
    					String str = null;    
    					Date date = null;     
    					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串    
    					date = new Date(resultCode);     
    					str = format.format(date);     
    					String fileName = "/sdcard/myImage/" + str + ".jpg";     

//    					sendBroadcast(fileName);

    	                 try {     
    	                	 b = new FileOutputStream(fileName);     
    	                	 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件   

    	                 } catch (FileNotFoundException e) {     
    	                	 e.printStackTrace();     
    	                 } finally {    
    	                	 try {    
    	                		 b.flush();     
    	                		 b.close();      
    	                	 } catch (IOException e) {   
    	                		 e.printStackTrace();   
    	                	 }    
    	                 }    
    	                 break;
    	            case Activity.RESULT_CANCELED:// 取消
    	            
    	                break;
    	            } 

//    				uploadImage(photoTemp);
    				break;
    				
    			case REQ_CODE_PICTURES:
    				
//    				Uri uri = data.getData();
//    				ContentResolver cr = this.getContentResolver();
//    				//get the physical path of the image
//    				Cursor c = cr.query(uri, null, null, null, null);
//    				c.moveToFirst();
//    				photoTemp = c.getString(c.getColumnIndex("_data"));
//    				uploadImage(photoTemp);
    				
    				  switch (resultCode) {
    		            case Activity.RESULT_OK: {
    		                Uri uri = data.getData();
    		                ContentResolver cr = this.getContentResolver();   
    		                Cursor cursor = cr.query(uri, null,  null, null, null);
    		                cursor.moveToFirst();
    		                String imgNo = cursor.getString(0); // 图片编号
//    		                String imgPath = cursor.getString(1); // 图片文件路径
    		                
    		                imgPath = cursor.getString(1); // 图片文件路径
    		                
    		        		Toast.makeText( UpActivity.this, imgPath , Toast.LENGTH_LONG).show();
    		        		
    		                String imgSize = cursor.getString(2); // 图片大小
    		                String imgName = cursor.getString(3); // 图片文件名
    		           
//	    		           	 try {  
//	    		           	     Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
//	    		           	     ImageView imageView = (ImageView) findViewById(R.id.updateresource_pic_path);  
//	    		           	     /* 将Bitmap设定到ImageView */  
//	    		           	     imageView.setImageBitmap(bitmap);  
//	    		           	 } catch (FileNotFoundException e) {  
//	    		           	     Log.e("Exception", e.getMessage(),e);  
//	    		           	 }  
    		                
    		                etImgPath.setText( imgPath );
    		                
	    		             cursor.close();
	    		                
    		           	  
    		                // Options options = new BitmapFactory.Options();
    		                // options.inJustDecodeBounds = false;
    		                // options.inSampleSize = 10;
    		                // Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
    		                
    		            }
    		            break;
    		            case Activity.RESULT_CANCELED:// 取消
    		                
    		                break;
    		            }

    				  
    				break;
    			default:
    				break;
    		};
    	} 
    	
    	
    	 
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
    public void uploadUseCamera(){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder( UpActivity.this); 
		
		builder.setTitle("选择图片"); 
		builder.setItems( new String[] { "拍照", "图片库" }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) { 
                	if( item == 0){ 
                		
                		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
                		Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                		startActivityForResult(intent, REQ_CODE_CAMERA);
                		  
//                        Toast.makeText(getApplicationContext(), "拍照", Toast.LENGTH_SHORT).show();
                	}else{
                		
//            			Intent intent = new Intent();
//            			/*Open the page of select pictures and set the type to image*/
//            			intent.setType("image/*");
//            			intent.setAction(Intent.ACTION_GET_CONTENT);
//            			startActivityForResult(intent, REQ_CODE_PICTURES);
            			
                		Intent i = new Intent(Intent.ACTION_PICK,
                		android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
                		startActivityForResult(i, REQ_CODE_PICTURES);
                			  
//                        Toast.makeText(getApplicationContext(), "图片库", Toast.LENGTH_SHORT).show();
                	}
                	
                }
        }); 
		builder.show();  
		
    }
    
    
}
