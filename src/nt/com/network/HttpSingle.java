package nt.com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * http单发客户端
 *
 *@author kege
 */
public class HttpSingle {
	
	private  static Log log = LogFactory.getLog(HttpSingle.class);
	private static HttpURLConnection connection;
	
	public String sendByPost(URL url,String msg,String encode,String contentType) {
		try {
			String recv="";
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type",contentType);
			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(msg.getBytes(encode));
			out.flush();
			out.close();
			int code = connection.getResponseCode();
			if (code == 200) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
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
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendByGet(URL url,String encode) {
		try {
			String recv="";
			connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();
			int code = connection.getResponseCode();
			if (code == 200) {
				 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
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
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void closeConnect() {
		connection.disconnect();
		log.info("关闭连接");
	}
	
}
