package com.bb.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.bb.api.BaseAuthenicationHttpClient;

import com.bb.model.Cuoti;
import com.bb.util.Constants;



public class  CuotiHttpAdapter {

	
	public static ArrayList<Cuoti> search(long lastId, int pageNo, String flag) throws Exception{
		String url = Constants.WEB_APP_URL + "cuoti.do?method=search&type=json";
		if( flag != null && !flag.equals("") ){
			try {
				flag  = URLEncoder.encode(flag, "utf-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url += "&f=" + flag;
		}
		
		System.out.println( " getUpdatesList url ::::::::::" + url );
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");

		JSONArray jsonArray = new JSONArray(jsonString);		
		ArrayList<Cuoti> ret = new ArrayList<Cuoti>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);

			Cuoti object = new Cuoti();
			object.id =  json.getString("id");

     		object.mingcheng = json.getString("mingcheng");
     		object.tupian = json.getString("tupian");
     		object.yuanyin = json.getString("yuanyin");
     		object.leixing = json.getString("leixing");
     		object.jieda = json.getString("jieda");
     		object.silu = json.getString("silu");
     		object.fangfa = json.getString("fangfa");
     		object.yonghu = json.getString("yonghu");
     		object.shijian = json.getString("shijian");
          
           
			ret.add(object);
		}
		return ret;
	}
	
	public static ArrayList<Cuoti> getAllCuotiList(long lastId, int pageNo, String flag) throws Exception{
		String url = Constants.WEB_APP_URL + "cuoti.do?method=list&type=json";
		if( flag != null && !flag.equals("") ){
			try {
				flag  = URLEncoder.encode(flag, "utf-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url += "&f=" + flag;
		}
		
		System.out.println( " getUpdatesList url ::::::::::" + url );
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");

		JSONArray jsonArray = new JSONArray(jsonString);		
		ArrayList<Cuoti> ret = new ArrayList<Cuoti>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);

			Cuoti object = new Cuoti();
			object.id =  json.getString("id");

     		object.mingcheng = json.getString("mingcheng");
     		object.tupian = json.getString("tupian");
     		object.yuanyin = json.getString("yuanyin");
     		object.leixing = json.getString("leixing");
     		object.jieda = json.getString("jieda");
     		object.silu = json.getString("silu");
     		object.fangfa = json.getString("fangfa");
     		object.yonghu = json.getString("yonghu");
     		object.shijian = json.getString("shijian");
          
           
			ret.add(object);
		}
		return ret;
	}
	
 
	
//	public static ArrayList<Cuoti> getFollowedByType(long lastId, int pageNo,  String news_type ) throws Exception  {
//	String url = Constants.WEB_APP_URL + "foodList.do?type=json";
//	if( news_type != null ){
//		try {
//			news_type  = URLEncoder.encode(news_type, "utf-8") ;
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		url += "&news_type=" +  news_type  ;
//	}
//	return getUpdatesList(url,lastId,pageNo);
//}
	
	
}



