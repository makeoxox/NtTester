package nt.com.model;

/**
 * ͨ��ģ��
 * 
 * @author kege
 *
 */
public class SessionModel {
	
	private String SendMessage;
	
	private String recvMessage;

	public String getSendMessage() {
		return SendMessage;
	}

	public void setSendMessage(String sendMessage) {
		SendMessage = sendMessage;
	}

	public String getRecvMessage() {
		return recvMessage;
	}

	public void setRecvMessage(String recvMessage) {
		this.recvMessage = recvMessage;
	}
}
