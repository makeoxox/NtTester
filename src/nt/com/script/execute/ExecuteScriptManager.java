package nt.com.script.execute;

import java.io.FileNotFoundException;

import javax.script.Invocable;
import javax.script.ScriptException;

import nt.com.script.AbstractMessageScriptManager;

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
			e.printStackTrace();
		}
		return message;
	}
	
}