package nt.com.buiz.comm;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.dom4j.DocumentException;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.enmu.TextType;
import nt.com.handler.HandlerManager;
import nt.com.model.SessionModel;
import nt.com.network.HttpSingle;
import nt.com.network.NioTcpSingle;
import nt.com.script.execute.ExecuteScriptManager;
import nt.com.util.Str;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.MainView;

/**
 * 
 * ͨ�ŷ�����
 * 
 * @author kege
 *
 */
public class Comm {
	
	private File file;
	
	//�������
	final static NioTcpSingle nts= new NioTcpSingle();
	final static HttpSingle hs = new HttpSingle();
	
	//����
	final static ToolBar toptoolbar = (ToolBar)MainView.parent.lookup("#toptoolbar");
	final static  Button runbtn = (Button) toptoolbar.lookup("#runbtn");
	
	public Comm(File file) {
		this.file = file;
	}
	
	/**
	 * �жϸ�������ִ�з���
	 * 
	 */
	public void send() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, DocumentException {
		//�ж��ļ�����
		Alert alert = new Alert(AlertType.WARNING);
		Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
		alert.setHeaderText(null);
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		TextType type = Utils.getTextType(this.file);
		String msgtype = Config.getMsg();
		if(type!=TextType.XML&&type!=TextType.JSON&&type!=TextType.TXT) {
			alert.setTitle("����");
			alert.setContentText("ֻ�ܷ���XML,JSON,TXT�����ļ���");
			alert.showAndWait();
			return;
		}
		if((type==TextType.XML&&!msgtype.toLowerCase().equals("xml"))||(type==TextType.JSON&&!msgtype.toLowerCase().equals("json"))||(type==TextType.TXT&&!msgtype.toLowerCase().equals("txt"))) {
			alert.setTitle("����");
			alert.setContentText("��ѡ�������ͺͷ��͵��ļ����Ͳ����ϣ�");
			alert.showAndWait();
			return;
		}
		
		//�ж��Ƿ����ýű�
		String script = Config.getScript();
		String scriptpath = getScriptPath(this.file);
		if(!script.toLowerCase().equals("disable")) {
			File file = new File(scriptpath);
			if(!file.exists()) {
				alert.setTitle("����");
				alert.setContentText("�ű� "+scriptpath+" ������");
				alert.showAndWait();
				return;
			}
		}
		//��ȡ�ļ�����
		String message =Utils.ReadFiletoString(this.file, Config.getEncode());
		//---------���ݸ��������жϷ���------------
		String asyn = Config.getConnect();     	//ͬ��
		String protocol = Config.getProtocol();	//Э��
		String multi = Config.getMulti();	   	//����
		//Tcp - ͬ�� - ����
		if(asyn.toLowerCase().equals("sync")&&protocol.toLowerCase().equals("tcp")&&multi.toLowerCase().equals("single")) {
			TcpSyncSingle(message,scriptpath);
		}
		//http - ͬ�� - ����
		else if(asyn.toLowerCase().equals("sync")&&protocol.toLowerCase().equals("http")&&multi.toLowerCase().equals("single")) {
			HttpSyncSingle(message,scriptpath);
		}
		
	}
	
	//��ȡ�ű�
	private String getScriptPath(File file) {
		String scriptName =  this.file.getName().substring(0,this.file.getName().lastIndexOf("."));
		String parentPath = this.file.getParent();
		String scriptPath = parentPath+File.separator+scriptName+".js";
		return scriptPath;
	}
	
	//ƴװ���ĳ����뱨������
	private String packLengthAndMessage(String message) throws UnsupportedEncodingException {
		//��ó���
		String lengthtype=Config.getLengthType();
		int length;
		if(lengthtype.toLowerCase().equals("byte")) {
			length= message.getBytes(Config.getEncode()).length;
		}else {
			length=message.length();
		}
		//�жϲ�λ
		String fixStr;
		String fixtype= Config.getFixType();
		if(fixtype.toLowerCase().equals("left")) {
			fixStr =Str.leftFix(length, Config.getFixLength(), Config.getFixChar());
		}else {
			fixStr =Str.rightFix(length, Config.getFixLength(), Config.getFixChar());
		}
		return fixStr + message;
	}
	
	//�յ����ĺ������ļ�
	private void generateResponse(String message) throws IOException {
		String respName =  this.file.getName().substring(0,this.file.getName().lastIndexOf("."));
		String parentPath = this.file.getParent();
		String respPath= parentPath+File.separator+respName+".resp";
		File file = new File(respPath);
		Utils.WriteStringtoFile(message, false, file, Config.getEncode());
	}
	
	//tcp-ͬ��-����
	private void TcpSyncSingle(String message,String scriptpath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException, DocumentException {
		//�ȴ��� ��Ŀ��handler,�ٴ����Զ���handler,�����ű�
		HandlerManager handlermanage= new HandlerManager();
		handlermanage.loadCustomHandler(message);
		String finalMessage = (String) handlermanage.before();
		if(Config.getScript().toLowerCase().equals("enable")) { //�Ƿ����ýű�
			Task<Object> task =new Task<Object>() {
				@Override
				public Object call() {  
					runbtn.setDisable(true);
					ExecuteScriptManager esm = new ExecuteScriptManager(scriptpath);
					esm.setMethod(ExecuteScriptManager.BEFORE);
					SessionModel sm = new SessionModel();
					sm.setSendMessage(finalMessage);
					String scriptedMsg = esm.invoke(sm);
					String recvMsg=null;
					try {
						String sendMsg = packLengthAndMessage(scriptedMsg);
						recvMsg = nts.sendMsg(sendMsg, Config.getOffset(), Config.getEncode(), Config.getEncode(), Config.getIP(), Config.getPort());
						if(recvMsg!=null) {
							generateResponse(recvMsg);
						}
					}catch(Exception e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
						e.printStackTrace();
					}
					esm.setMethod(ExecuteScriptManager.AFTER);
					sm.setRecvMessage(recvMsg);
					String esmresvmsg = esm.invoke(sm);
					handlermanage.getContext().setObject(esmresvmsg);
					handlermanage.after();
					runbtn.setDisable(false);
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {	
					@Override
					public void run() {
						try {
							task.get();
						} catch (InterruptedException | ExecutionException e) {
							ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
							e.printStackTrace();
						}finally {
							runbtn.setDisable(false);
						}
					}
			}).start();
		}
		//�޽ű�
		else {
			Task<Object> task =new Task<Object>() {
				@Override
				protected Object call() throws Exception {
					runbtn.setDisable(true);
					String recvMsg=null;
					try {
						String sendMsg = packLengthAndMessage(finalMessage);
						recvMsg = nts.sendMsg(sendMsg, Config.getOffset(), Config.getEncode(), Config.getEncode(), Config.getIP(), Config.getPort());
						if(recvMsg!=null) {
							generateResponse(recvMsg);
						}
					}catch(Exception e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
						e.printStackTrace();
					}
					
					handlermanage.getContext().setObject(recvMsg);
					handlermanage.after();
					runbtn.setDisable(false);
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {	
					@Override
					public void run() {
						try {
							task.get();
						} catch (InterruptedException | ExecutionException e) {
							ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
							e.printStackTrace();
						}finally {
							runbtn.setDisable(false);
						}
					}
			}).start();
		}
		
	}
	
	//http-ͬ��-����
	private void HttpSyncSingle(String message, String scriptpath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DocumentException, MalformedURLException {
		HandlerManager handlermanage= new HandlerManager();
		handlermanage.loadCustomHandler(message);
		String finalMessage = (String) handlermanage.before();
		if(Config.getScript().toLowerCase().equals("enable")) { //�Ƿ����ýű�
			Task<Object> task =new Task<Object>() {
				@Override
				public Object call() {  
					runbtn.setDisable(true);
					ExecuteScriptManager esm = new ExecuteScriptManager(scriptpath);
					esm.setMethod(ExecuteScriptManager.BEFORE);
					SessionModel sm = new SessionModel();
					sm.setSendMessage(finalMessage);
					String scriptedMsg = esm.invoke(sm);
					String recvMsg=null;
					try {
						String requestType = Config.getHttpRequestType();
						if(requestType.toLowerCase().equals("post")) {
							recvMsg = hs.sendByPost(new URL(Config.getURL()), scriptedMsg, Config.getEncode(), Config.getHttpContentType());

						}else {
							recvMsg = hs.sendByGet(new URL(Config.getURL()),  Config.getEncode());
						}
						if(recvMsg!=null) {
							generateResponse(recvMsg);
						}
					}catch(Exception e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
						e.printStackTrace();
					}
					esm.setMethod(ExecuteScriptManager.AFTER);
					sm.setRecvMessage(recvMsg);
					String esmresvmsg = esm.invoke(sm);
					handlermanage.getContext().setObject(esmresvmsg);
					handlermanage.after();
					runbtn.setDisable(false);
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {	
					@Override
					public void run() {
						try {
							task.get();
						} catch (InterruptedException | ExecutionException e) {
							ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
							e.printStackTrace();
						}finally {
							runbtn.setDisable(false);
						}
					}
			}).start();
			
		}
		//�޽ű�
		else {
			Task<Object> task =new Task<Object>() {
				@Override
				protected Object call() throws Exception {
					runbtn.setDisable(true);
					String recvMsg=null;
					try {
						String requestType = Config.getHttpRequestType();
						if(requestType.toLowerCase().equals("post")) {
							recvMsg = hs.sendByPost(new URL(Config.getURL()), finalMessage, Config.getEncode(), Config.getHttpContentType());

						}else {
							recvMsg = hs.sendByGet(new URL(Config.getURL()),  Config.getEncode());
						}
						if(recvMsg!=null) {
							generateResponse(recvMsg);
						}
					}catch(Exception e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
						e.printStackTrace();
					}
					
					handlermanage.getContext().setObject(recvMsg);
					handlermanage.after();
					runbtn.setDisable(false);
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {	
					@Override
					public void run() {
						try {
							task.get();
						} catch (InterruptedException | ExecutionException e) {
							ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
							e.printStackTrace();
						}finally {
							runbtn.setDisable(false);
						}
					}
			}).start();
		}
	}

}
