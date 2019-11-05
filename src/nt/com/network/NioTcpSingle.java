package nt.com.network;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * NIO�ͻ���
 * 
 * @author kege
 * */
public class NioTcpSingle {
	
	private  static Log log = LogFactory.getLog(NioTcpSingle.class);
	static SocketChannel sc ;
	
	
	public  String sendMsg(String msg,int recvheadLength,String encodeC,String decodeC,String ip ,int port ) {
		try {
			log.info("������Ϣ��["+ip+":"+port+"] - "+msg);
			sc =SocketChannel.open();
			sc.configureBlocking(true);
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
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			return "IO�쳣�������ж�";
		}
		return null;
	}
	
	public  String sendMsgNoRecv(String msg,String encodeC,String ip ,int port ) {
		try {
			log.info("������Ϣ��["+ip+":"+port+"] - "+msg);
			sc =SocketChannel.open();
			sc.configureBlocking(true);
			sc.connect(new InetSocketAddress(ip, port));
			byte []bytes =msg.getBytes(encodeC);
			sc.write(ByteBuffer.wrap(bytes));
			sc.close();
			return "���ͳɹ�";
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			return "��֧�ֵı���";
		} catch (IOException e) {
			log.error(e.getMessage());
			return "IO�쳣�������ж�";
		}
	}
	
	public void closeConnect() {
		try {
			sc.close();
			log.info("�ر�����");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		NioTcpSingle nts = new NioTcpSingle();
		nts.sendMsg("niha\n", 3, "GBK", "GBK", "localhost", 8888);
	}

}
