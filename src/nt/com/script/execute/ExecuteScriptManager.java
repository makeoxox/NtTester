package nt.com.script.execute;


import javax.script.Invocable;

import nt.com.model.SessionModel;
import nt.com.script.AbstractMessageScriptManager;
import nt.com.view.init.ConsoleTextArea;
/**
 * 执行脚本管理器
 * 
 * @author kege
 *
 */
public class ExecuteScriptManager extends AbstractMessageScriptManager {
	
	private String path;
	private String method;
	
	public ExecuteScriptManager (String path,String method) {
		this.path=path;
		this.method=method;
	}
	
	public String invoke(Object session){
		loadLib();
		try {
			Invocable iv = this.getInvocable(path);
			ExecuteScript as =iv.getInterface(ExecuteScript.class);
			if(method.equals("before")) {
				return 	as.beforeSend(((SessionModel) session).getSendMessage());
			}else if(method.equals("after")){
				return as.afterReceive(((SessionModel) session).getSendMessage(),((SessionModel) session).getRecvMessage());
			}
		}catch (Exception e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
			e.printStackTrace();
		}
		return null;
	}
	
}
