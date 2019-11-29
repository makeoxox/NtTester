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
 * 通信方法类
 * 
 * @author kege
 *
 */
public class Comm {
	
	private File file;
	
	//网络组件
	final static NioTcpSingle nts= new NioTcpSingle();
	final static HttpSingle hs = new HttpSingle();
	
	//界面
	final static ToolBar toptoolbar = (ToolBar)MainView.parent.lookup("#toptoolbar");
	final static  Button runbtn = (Button) toptoolbar.lookup("#runbtn");
	
	public Comm(File file) {
		this.file = file;
	}
	
	/**
	 * 判断各种条件执行发送
	 * 
	 */
	public void send() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, DocumentException {
		//判断文件类型
		Alert alert = new Alert(AlertType.WARNING);
		Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
		alert.setHeaderText(null);
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		TextType type = Utils.getTextType(this.file);
		String msgtype = Config.getMsg();
		if(type!=TextType.XML&&type!=TextType.JSON&&type!=TextType.TXT) {
			alert.setTitle("提醒");
			alert.setContentText("只能发送XML,JSON,TXT类型文件！");
			alert.showAndWait();
			return;
		}
		if((type==TextType.XML&&!msgtype.toLowerCase().equals("xml"))||(type==TextType.JSON&&!msgtype.toLowerCase().equals("json"))||(type==TextType.TXT&&!msgtype.toLowerCase().equals("txt"))) {
			alert.setTitle("提醒");
			alert.setContentText("所选报文类型和发送的文件类型不符合！");
			alert.showAndWait();
			return;
		}
		
		//判断是否启用脚本
		String script = Config.getScript();
		String scriptpath = getScriptPath(this.file);
		if(!script.toLowerCase().equals("disable")) {
			File file = new File(scriptpath);
			if(!file.exists()) {
				alert.setTitle("提醒");
				alert.setContentText("脚本 "+scriptpath+" 不存在");
				alert.showAndWait();
				return;
			}
		}
		//读取文件内容
		String message =Utils.ReadFiletoString(this.file, Config.getEncode());
		//---------根据各种条件判断发送------------
		String asyn = Config.getConnect();     	//同步
		String protocol = Config.getProtocol();	//协议
		String multi = Config.getMulti();	   	//并发
		//Tcp - 同步 - 单条
		if(asyn.toLowerCase().equals("sync")&&protocol.toLowerCase().equals("tcp")&&multi.toLowerCase().equals("single")) {
			TcpSyncSingle(message,scriptpath);
		}
		//http - 同步 - 单条
		else if(asyn.toLowerCase().equals("sync")&&protocol.toLowerCase().equals("http")&&multi.toLowerCase().equals("single")) {
			HttpSyncSingle(message,scriptpath);
		}
		
	}
	
	//获取脚本
	private String getScriptPath(File file) {
		String scriptName =  this.file.getName().substring(0,this.file.getName().lastIndexOf("."));
		String parentPath = this.file.getParent();
		String scriptPath = parentPath+File.separator+scriptName+".js";
		return scriptPath;
	}
	
	//拼装报文长度与报文内容
	private String packLengthAndMessage(String message) throws UnsupportedEncodingException {
		//获得长度
		String lengthtype=Config.getLengthType();
		int length;
		if(lengthtype.toLowerCase().equals("byte")) {
			length= message.getBytes(Config.getEncode()).length;
		}else {
			length=message.length();
		}
		//判断补位
		String fixStr;
		String fixtype= Config.getFixType();
		if(fixtype.toLowerCase().equals("left")) {
			fixStr =Str.leftFix(length, Config.getFixLength(), Config.getFixChar());
		}else {
			fixStr =Str.rightFix(length, Config.getFixLength(), Config.getFixChar());
		}
		return fixStr + message;
	}
	
	//收到报文后生成文件
	private void generateResponse(String message) throws IOException {
		String respName =  this.file.getName().substring(0,this.file.getName().lastIndexOf("."));
		String parentPath = this.file.getParent();
		String respPath= parentPath+File.separator+respName+".resp";
		File file = new File(respPath);
		Utils.WriteStringtoFile(message, false, file, Config.getEncode());
	}
	
	//tcp-同步-单发
	private void TcpSyncSingle(String message,String scriptpath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException, DocumentException {
		//先处理 项目内handler,再处理自定义handler,最后处理脚本
		HandlerManager handlermanage= new HandlerManager();
		handlermanage.loadCustomHandler(message);
		String finalMessage = (String) handlermanage.before();
		if(Config.getScript().toLowerCase().equals("enable")) { //是否启用脚本
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
		//无脚本
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
	
	//http-同步-单发
	private void HttpSyncSingle(String message, String scriptpath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DocumentException, MalformedURLException {
		HandlerManager handlermanage= new HandlerManager();
		handlermanage.loadCustomHandler(message);
		String finalMessage = (String) handlermanage.before();
		if(Config.getScript().toLowerCase().equals("enable")) { //是否启用脚本
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
		//无脚本
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
