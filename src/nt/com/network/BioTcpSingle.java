package nt.com.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ÆÕÍ¨tcp¿Í»§¶Ë
 * 
 * @author kege
 * */
public class BioTcpSingle {
	
	
	public  String send(String msg , String ip,int port) throws UnknownHostException, IOException {
		
			Socket socket = new Socket(ip,port);
			OutputStream os =  socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(msg);
			bw.flush();
			bw.close();
			os.close();
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line="";
			String contend="";
			while((line=br.readLine())!=null) {
				contend+=line+"\n";
			}
			br.close();
			is.close();
			socket.close();
			return contend;
	}
	
	public  void sendNoRecv(String msg , String ip,int port) throws UnknownHostException, IOException {
		
			Socket socket = new Socket(ip,port);
			OutputStream os =  socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(msg);
			bw.flush();
			bw.close();
			os.close();
			socket.close();
			
	}
	
	
}
