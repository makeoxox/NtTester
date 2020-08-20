package nt.com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;


/**
 * ¼òµ¥http·þÎñÆ÷
 * 
 * @author kege
 * */
public class HttpServer {
	
	protected static Log log = LogFactory.getLog(HttpServer.class);
	private static boolean isOpened = false;
	private  Map<?,?> recvMsg;
	private Server server;
	
	public Map<?,?> getRecvMsg() {
		  return this.recvMsg;
	  }
	
	public  void start(int port,String root ,String contentype,String encode) {
		try {
			if(isOpened) {
				return;
			}
			this.server = new Server(port);	
			ContextHandler ctx = new ContextHandler();
			ctx.setContextPath(root);
			ctx.setHandler(new AbstractHandler() {
				@Override
				public void handle(String arg0, Request varReqeust, HttpServletRequest request, HttpServletResponse response){
					
						response.setContentType(contentype);
						response.setStatus(HttpServletResponse.SC_OK);
						varReqeust.setHandled(true);
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),encode));
							String line="";
							String contend="";
							while((line=br.readLine())!=null) {
								contend+=line+"\n";
							}
							br.close();
						} catch (IOException e) {
							log.error(e.toString());
							e.printStackTrace();
						} 
				}
			});
			server.setHandler(ctx);
			server.start();
			isOpened = false;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void stop() {
		try {
			if(!isOpened)return;
			this.server.stop();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
