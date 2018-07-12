package com.bb.ui;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.bb.R;
import com.bb.model.Cuoti;
import com.bb.api.CuotiHttpAdapter;
import com.bb.util.AsyncImageLoader;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.util.Constants;

import edu.self.utils.AppContext;



public class CuotiListActivity  extends  ListActivity {


    private CuotiAdapter adapter = null;
    
    private ArrayList<Cuoti> cuotiList;

    private String type;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        type = (String) getIntent().getExtras().get("type");    
        
        type = AppContext.userinfo.getUserName();
        setContentView(R.layout.cuoti_list);  
        
		Button left_button = (Button) findViewById(R.id.left_button);
		left_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        new LoadTask().execute();    
			}
		});

		Button right_button = (Button) findViewById(R.id.right_button);
		right_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent( CuotiListActivity.this, CuotiInfoAddActivity.class);
				startActivity(intent);
			}
		});

        new LoadTask().execute();     
    }
     
	 
	/**
	 * 异步加载所有资源
	 *
	 */
	public class LoadTask extends AsyncTask<Void, Void, Void>{
	 
		protected Void doInBackground(Void... arg0) {
			try {
				cuotiList =  CuotiHttpAdapter.getAllCuotiList(-1, -1,type) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
 
		protected void onPostExecute(Void result) {
			adapter = new CuotiAdapter() ;
			setListAdapter(adapter);
			removeDialog(0);
			super.onPostExecute(result);
		}
		
		protected void onPreExecute() {
			showDialog(0);
			super.onPreExecute();
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent( CuotiListActivity.this, CuotiInfoActivity.class);
		intent.putExtra("object", cuotiList.get(position));
		startActivity(intent);
	}
	

	public class CuotiAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;
		
		public int getCount() {
			return cuotiList.size();
		}

		public Object getItem(int arg0) {
			return cuotiList.get(arg0);
		}
		
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
		
			asyncImageLoader = new AsyncImageLoader();
			 
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cuoti_list_row, null);
 
		    TextView name =  (TextView) convertView.findViewById(R.id.name); ;
		    
	        Cuoti u = cuotiList.get(position); 
	      // name.setText(  u.name    );
	        
				name.setText( u.getMingcheng() ); 
		 
		 
			return convertView;
		}
	}
	
	
 
    
}
