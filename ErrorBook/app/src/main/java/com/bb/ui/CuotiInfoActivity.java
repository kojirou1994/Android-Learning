package com.bb.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.ImageView;
import android.widget.TextView;
import com.bb.R; 
import com.bb.util.AsyncImageLoader;
import com.bb.util.Constants;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.model.Cuoti;


public class  CuotiInfoActivity extends Activity {


	private Cuoti cuoti ;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.cuoti_info);
		 
		cuoti = (Cuoti) getIntent().getSerializableExtra("object");

		TextView tv_mingcheng = (TextView) this.findViewById(R.id.mingcheng);
		tv_mingcheng.setText(  "错题名 : " + cuoti.mingcheng   ) ;

//		从服务器上获取图片，并且显示
		ImageView iv = (ImageView) this.findViewById(R.id.tupian) ; 
        String picPath = Constants.WEB_APP_URL + cuoti.tupian  ; 
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		
		Drawable cachedImage = asyncImageLoader.loadDrawable(
    			picPath , iv , new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});

		if (cachedImage == null) {
			iv.setImageResource(R.drawable.pork);
		} else {
			iv.setImageDrawable(cachedImage);
		}
		
		
		TextView tv_yuanyin = (TextView) this.findViewById(R.id.yuanyin);
		tv_yuanyin.setText(  "错题原因 : " + cuoti.yuanyin   ) ;
		TextView tv_leixing = (TextView) this.findViewById(R.id.leixing);
		tv_leixing.setText(  "错题类型 : " + cuoti.leixing   ) ;
		TextView tv_jieda = (TextView) this.findViewById(R.id.jieda);
		tv_jieda.setText(  "正确解答 : " + cuoti.jieda   ) ;
		TextView tv_silu = (TextView) this.findViewById(R.id.silu);
		tv_silu.setText(  "解题思路 : " + cuoti.silu   ) ;
		TextView tv_fangfa = (TextView) this.findViewById(R.id.fangfa);
		tv_fangfa.setText(  "方法 : " + cuoti.fangfa   ) ;
		TextView tv_yonghu = (TextView) this.findViewById(R.id.yonghu);
		tv_yonghu.setText(  "用户 : " + cuoti.yonghu   ) ;
		TextView tv_shijian = (TextView) this.findViewById(R.id.shijian);
		tv_shijian.setText(  "时间 : " + cuoti.shijian   ) ;
     
		Button btn_button1 = (Button) findViewById(R.id.button1) ;
		btn_button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

//		    	Intent intent = new Intent( CuotiInfoActivity.this , CuotiListActivity.class ) ;
//				intent.putExtra("id",   info.info_id  );
//				startActivity( intent );
				finish();
			}
		}) ; 
		
	}
	
	
}
