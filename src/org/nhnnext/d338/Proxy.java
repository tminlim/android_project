package org.nhnnext.d338;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.util.Log;

public class Proxy {
	public String getJSON(){
		try {
		URL url = new URL("http://10.73.38.167:8080/board/list.json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setConnectTimeout(10 * 1000);
		conn.setReadTimeout(10 *1000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Accept", "application/json");
		
		conn.setDoInput(true);
		conn.connect();
		
		int status = conn.getResponseCode();
		Log.i("test", "ProxyResponseCode:" + status);
		
		switch(status) {
		case 200:
		case 201:
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		}
		return "";
	} catch (Exception e) {
		e.printStackTrace();
		Log.i("test", "error:"+e);
	}
	return null;
	}
	
	
}
