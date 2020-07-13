package nt.com.script.execute;


import javax.script.Invocable;

import nt.com.model.SessionModel;
import nt.com.script.AbstractMessageScriptManager;
import nt.com.view.init.ConsoleTextArea;
/**
 * ִ�нű�������
 * 
 * @author kege
 *
 */
public class ExecuteScriptManager extends AbstractMessageScriptManager {
	
	public final  static   String  BEFORE="before";
	public final  static   String  AFTER="after";
	
	private String path;
	private String method;
	
	public ExecuteScriptManager (String path) {
		this.path=path;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


	public String invoke(Object session){
		loadLib();
		try {
			Invocable iv = this.getInvocable(path);
			ExecuteScript as =iv.getInterface(ExecuteScript.class);
			if(method.equals(BEFORE)) {
				return as.beforeSend(((SessionModel) session).getSendMessage());
			}else if(method.equals(AFTER)){
				return as.afterReceive(((SessionModel) session).getSendMessage(),((SessionModel) session).getRecvMessage());
			}
		}catch (Exception e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
			ConsoleTextArea.AppendMessageOnCurrentConsole("ȷ���ű����Ƿ���ڱ����'beforeSend'��'afterReceive'����");
			e.printStackTrace();
		}
		return null;
	}
	
}
