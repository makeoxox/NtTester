package nt.com.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * http并发客户端
 *
 *@author kege
 */
public class HttpMulti {

	private   static Log log = LogFactory.getLog(HttpMulti.class);
	private   ArrayBlockingQueue<HttpURLConnection> channelList;
	private   String encodeC;
	private   URL url;
	
	// URL 编码  解码
	public  void load(URL url, String encodeC) {
		this.encodeC=encodeC;
		this.url=url;
		try {
			System.gc();
			channelList =new ArrayBlockingQueue<HttpURLConnection>(100);
			for(int session=100;session>0;session--) {
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				channelList.add(connection);
			} 
		}catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public  String sendByPost(String msg,String contentType) {
		try {
			log.info("发送消息到["+url.toString()+"]");
			HttpURLConnection connection =  channelList.take(); 
			try {
				String recv="";
				connection=(HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestProperty("Content-Type",contentType);
				connection.connect();
				OutputStream out = connection.getOutputStream();
				out.write(msg.getBytes(encodeC));
				out.flush();
				out.close();
				int code = connection.getResponseCode();
				if (code == 200) {
					 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encodeC));
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
		} catch (InterruptedException e1) {
			log.error(e1);
			e1.printStackTrace();
		}finally {
			new Thread(new Runnable() {
				@Override
				public void run() {
					putSocketChannel();
				}
			}).start();
		}
		return null;
	}
	
	public  String sendByGet(String msg,String msgname) {
		try {
			log.info("发送消息到["+url.toString()+"]");
			HttpURLConnection connection =  channelList.take(); 
			try {
				String recv="";
				connection=(HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				connection.setRequestProperty(msgname,msg);
				connection.connect();
				int code = connection.getResponseCode();
				if (code == 200) {
					 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encodeC));
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
		} catch (InterruptedException e1) {
			log.error(e1);
			e1.printStackTrace();
		}finally {
			new Thread(new Runnable() {
				@Override
				public void run() {
					putSocketChannel();
				}
			}).start();
		}
		return null;
	}
	
	
	public  void putSocketChannel() {
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			this.channelList.put(connection);
			log.info("连接池中可用连接数 ["+this.channelList.size()+"]");
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public  ArrayBlockingQueue<HttpURLConnection> getCurrentQueue() {
		return this.channelList;
	}
	
}
