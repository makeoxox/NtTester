package nt.com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * http单发客户端
 *
 *@author kege
 */
public class HttpSingle {
	
	private  static Log log = LogFactory.getLog(HttpSingle.class);
	private static HttpURLConnection connection;
	
	public String sendByBody(URL url,Map<String, String> propMap,String msg,String encode,String decode) {
		try {
			String recv="";
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			Set <Entry<String, String>> entrys = propMap.entrySet();
			for (Entry<String, String> entry : entrys) {
				connection.setRequestProperty(entry.getKey().trim(),entry.getValue().trim());
			}
			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(msg.getBytes(encode));
			out.flush();
			out.close();
			int code = connection.getResponseCode();
			if (code == 200) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), decode));
				 String line;
				 while ((line = reader.readLine()) != null) {
					 recv += line + "\n";
				 }
				 reader.close();
			 }else {
				 recv="接受失败，错误码["+code+"]";
			 }
			connection.disconnect();
			return recv;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendByHead(URL url,Map<String, String> propMap,String msg,String msgname,String decode) {
		try {
			String recv="";
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connection.setRequestProperty(msgname,msg);
			Set <Entry<String, String>> entrys = propMap.entrySet();
			for (Entry<String, String> entry : entrys) {
				connection.setRequestProperty(entry.getKey().trim(),entry.getValue().trim());
			}
			connection.connect();
			int code = connection.getResponseCode();
			if (code == 200) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),decode));
				 String line;
				 while ((line = reader.readLine()) != null) {
					 recv += line + "\n";
				}
				 reader.close();
			 }else {
				 recv="接受失败，错误码["+code+"]";
			 }
			connection.disconnect();
			log.info("收到消息["+url+"] - "+recv);
			return recv;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeConnect() {
		connection.disconnect();
		log.info("关闭连接");
	}
	
}
