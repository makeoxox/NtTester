package nt.com.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * NIO连接池，池中默认连接数为50
 *
 *@author  kege
 */
public class NioTcpMulti {
	private   static Log log = LogFactory.getLog(NioTcpMulti.class);
	private   ArrayBlockingQueue<SocketChannel> channelList;
	private   String ip;
	private   int port;
	private   String encodeC;
	private   String decodeC;
	private   int recvheadLength;
	
	// ip , 端口 ,编码方式 , 解码方式 , 返回报文offset 
	public  void load(String ip , int port  , String encodeC,String decodeC,int recvheadLength) {
		this.ip= ip;
		this.port=port;
		this.encodeC=encodeC;
		this.decodeC=decodeC;
		this.recvheadLength=recvheadLength;
		try {
			System.gc();
			channelList =new ArrayBlockingQueue<SocketChannel>(100);
			for(int session=100;session>0;session--) {
				SocketChannel sc =SocketChannel.open();
				sc.configureBlocking(true);
				channelList.add(sc);
			} 
		}catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	// ip , 端口 ,编码方式 
		public  void load(String ip , int port  , String encodeC) {
			this.ip= ip;
			this.port=port;
			this.encodeC=encodeC;
			try {
				System.gc();
				channelList =new ArrayBlockingQueue<SocketChannel>(100);
				for(int session=100;session>0;session--) {
					SocketChannel sc =SocketChannel.open();
					sc.configureBlocking(true);
					channelList.add(sc);
				} 
			}catch (IOException e) {
				log.error(e.getLocalizedMessage());
			}
		}
	
	public  String sendMsg(String msg) {
		try {
			log.info("发送消息到["+ip+":"+port+"] - "+msg);
			SocketChannel sc =  channelList.take(); 
			sc.connect(new InetSocketAddress(ip, port));
			byte []bytes =msg.getBytes(encodeC);
			sc.write(ByteBuffer.wrap(bytes));
			ByteBuffer readbufhead = ByteBuffer.allocate(recvheadLength);
			while(readbufhead.hasRemaining() ){
				if(sc.read(readbufhead)==-1) {
					break;
				}
			}
			String recvhead=new String(readbufhead.array(),decodeC);
			ByteBuffer readbufbody = ByteBuffer.allocate(Integer.parseInt(recvhead));
			while(readbufbody.hasRemaining() ){
				if(sc.read(readbufbody)==-1) {
					break;
				}
			}
			String recvMsg =new String(readbufbody.array(),decodeC);
			log.info("收到消息["+ip+":"+port+"] - "+recvMsg);
			sc.close();
			return recvMsg;
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
			return "线程中断！";
		}catch (UnsupportedEncodingException e) {
			log.error(e.getLocalizedMessage());
			return "不受支持的编码类型";
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return "IO异常，连接中断";
		}finally {
			new Thread(new Runnable() {
				@Override
				public void run() {
					putSocketChannel();
				}
			}).start();
		}
	}
	
	public  String sendMsgNoRecv(String msg) {
		try {
			log.info("发送消息到["+ip+":"+port+"] - "+msg);
			SocketChannel sc =  channelList.take(); 
			sc.connect(new InetSocketAddress(ip, port));
			byte []bytes =msg.getBytes(encodeC);
			sc.write(ByteBuffer.wrap(bytes));
			sc.close();
			return "发送成功";
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
			return "线程中断！";
		}catch (UnsupportedEncodingException e) {
			log.error(e.getLocalizedMessage());
			return "不受支持的编码类型";
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return "IO异常，连接中断";
		}finally {
			new Thread(new Runnable() {
				@Override
				public void run() {
					putSocketChannel();
				}
			}).start();
		}
	}
	
		
	public  void putSocketChannel() {
		SocketChannel sc;
		try {
			sc = SocketChannel.open();
			sc.configureBlocking(true);
			this.channelList.put(sc);
			log.info("连接池中可用连接数 ["+this.channelList.size()+"]");
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public  ArrayBlockingQueue<SocketChannel> getCurrentQueue() {
		return this.channelList;
	}
	
}


