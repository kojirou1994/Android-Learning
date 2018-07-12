package com.bb.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bb.util.Constants;


/**
 * 
 * @author 
 *
 */
public class HttpApiAccessor {

	 

	public static boolean sendPlace(long lastId, int pageNo,  int  uid , String username ,  double jd , double wd ) {
		String url = Constants.WEB_APP_URL + "type.do?method=sendPlace&type=json&uid=" + uid ;
		
		try {
			url += "&username=" + URLEncoder.encode(   username , "utf-8" ) ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url += "&jd=" + jd ;
		url += "&wd=" + wd ;
		
		url = appendParams(url, lastId, pageNo);
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", ""  );
		if( jsonString.equals("1") ){
			return true;
		}
		return false;
	}
		 
	private static String appendParams(String url, long lastId, int pageNo) {
		if(lastId != -1){
			url = "?last_id=" + lastId;
		}
		if(pageNo != -1){
			url = "&pageNo=" + pageNo;
		}
		return url;
	}

	
	
}



