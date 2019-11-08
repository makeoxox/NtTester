package nt.com.script.execute;


import javax.script.Invocable;

import nt.com.script.AbstractMessageScriptManager;
import nt.com.view.init.ConsoleTextArea;

public class ExecuteScriptManager extends AbstractMessageScriptManager {
	
	private String path;
	private String method;
	
	public ExecuteScriptManager (String path,String method) {
		this.path=path;
		this.method=method;
	}
	
	public String invoke(String message){
		loadLib();
		try {
			Invocable iv = this.getInvocable(path);
			ExecuteScript as =iv.getInterface(ExecuteScript.class);
			if(method.equals("before")) {
				return 	as.beforeSend(message);
			}else if(method.equals("after")){
				return as.afterReceive(message);
			}
		}catch (Exception e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
		}
		return message;
	}
	
}
