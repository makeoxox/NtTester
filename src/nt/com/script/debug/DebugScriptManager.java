package nt.com.script.debug;


import javax.script.Invocable;

import nt.com.script.AbstractMessageScriptManager;
import nt.com.view.init.ConsoleTextArea;

public class DebugScriptManager extends AbstractMessageScriptManager {
	
	private String path;
	
	public DebugScriptManager (String path) {
		this.path=path;
	}
	
	public String invoke(String arg){
		loadLib();
		try {
			Invocable iv = this.getInvocable(this.path);
			DebugScript ds =iv.getInterface(DebugScript.class);
			return ds.main(arg);
		}catch (Exception e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
		}
		return null;
	}
	
	
	

	
	
}
