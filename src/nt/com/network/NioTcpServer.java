package nt.com.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * NIO服务器
 * 
 *  @author kege
 * 
 */
public class NioTcpServer {
	
	  private  static Log log = LogFactory.getLog(NioTcpServer.class);
	 
	  private  Selector selector;  
	  private  ByteBuffer readheadBuffer;
	  private  String decodeC;
	  private  ServerSocketChannel servSocketChannel;  
	  
	 
	  
	  public  void init(int port,int recvheadLength,String codeC){  
		  readheadBuffer = ByteBuffer.allocate(recvheadLength);  
		  this.decodeC = codeC;
	      try {  
	            servSocketChannel = ServerSocketChannel.open();  
	            servSocketChannel.configureBlocking(false);  
	            servSocketChannel.socket().bind(new InetSocketAddress(port));  
	            selector = Selector.open();  
	            servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }        
	    }  
	  
	  public  void listen() {  
	        while(true){  
	            try{
	            	//当selector关闭时，跳出循环不选择
	            	if(!selector.isOpen()) {
	            		break;
	            	}
	                selector.select();       
	                if(!selector.isOpen()) {
	            		break;
	            	}
	                Iterator<SelectionKey> ite = selector.selectedKeys().iterator();  
	                while(ite.hasNext()){  
	                    SelectionKey key = ite.next();                    
	                    ite.remove();//确保不重复处理  
	                    handleKey(key);  
	                }  
	            }  
	            catch(Throwable t){  
	                t.printStackTrace();  
	            }                            
	        }                
	    }  
	  

	    private  void handleKey(SelectionKey key)  {  
	        SocketChannel sc = null;  
	        try{  
	            if(key.isAcceptable()){  
	                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();  
	                sc = serverChannel.accept(); 
	                sc.configureBlocking(false);  
	                sc.register(selector, SelectionKey.OP_READ);  
	            }  
	            else if(key.isReadable()){  
	            	sc = (SocketChannel) key.channel();  
	            	readheadBuffer.clear();  
	            	while(readheadBuffer.hasRemaining() ){
	    				if(sc.read(readheadBuffer)==-1) {
	    					break;
	    				}
	    			}
	    			String head=new String(readheadBuffer.array(),decodeC);
	    			ByteBuffer readbufbody = ByteBuffer.allocate(Integer.parseInt(head));
	    			while(readbufbody.hasRemaining() ){
	    				if(sc.read(readbufbody)==-1) {
	    					break;
	    				}
	    			}
	    			String recvMsg =new String(readbufbody.array(),decodeC);  
	    			log.info("收到消息["+sc.getRemoteAddress()+"] - "+recvMsg);
	    			
	    			
					sc.close();    
					this.closeConnect();
	            }  
	        }  
	        catch(Throwable t){  
	            t.printStackTrace();  
	            if(sc != null){  
	                try {
						sc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	            }  
	        }     
	    }  
	    
	 public void closeConnect() {
		 try {
			this.selector.close();
			this.servSocketChannel.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
}
