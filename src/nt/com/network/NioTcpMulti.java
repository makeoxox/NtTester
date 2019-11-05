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
 * NIO���ӳأ�����Ĭ��������Ϊ50
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
	
	// ip , �˿� ,���뷽ʽ , ���뷽ʽ , ���ر���offset 
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
	
	// ip , �˿� ,���뷽ʽ 
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
			log.info("������Ϣ��["+ip+":"+port+"] - "+msg);
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
			log.info("�յ���Ϣ["+ip+":"+port+"] - "+recvMsg);
			sc.close();
			return recvMsg;
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
			return "�߳��жϣ�";
		}catch (UnsupportedEncodingException e) {
			log.error(e.getLocalizedMessage());
			return "����֧�ֵı�������";
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return "IO�쳣�������ж�";
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
			log.info("������Ϣ��["+ip+":"+port+"] - "+msg);
			SocketChannel sc =  channelList.take(); 
			sc.connect(new InetSocketAddress(ip, port));
			byte []bytes =msg.getBytes(encodeC);
			sc.write(ByteBuffer.wrap(bytes));
			sc.close();
			return "���ͳɹ�";
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage());
			return "�߳��жϣ�";
		}catch (UnsupportedEncodingException e) {
			log.error(e.getLocalizedMessage());
			return "����֧�ֵı�������";
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			return "IO�쳣�������ж�";
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
			log.info("���ӳ��п��������� ["+this.channelList.size()+"]");
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


