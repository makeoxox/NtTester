package nt.com.script.debug;

import javax.script.Invocable;

import nt.com.script.AbstractMessageScriptManager;
import nt.com.view.init.ConsoleTextArea;
/**
 * 调试脚本管理器
 * 
 * @author kege
 *
 */
public class DebugScriptManager extends AbstractMessageScriptManager {
	
	private String path;
	
	public DebugScriptManager (String path) {
		this.path=path;
	}
	
	public String invoke(Object arg){
		loadLib();
		try {
			Invocable iv = this.getInvocable(this.path);
			DebugScript ds =iv.getInterface(DebugScript.class);
			if(arg!=null) {
				return ds.main(arg.toString());
			}else {
				return ds.main(null);
			}
			
		}catch (Exception e) {
			if(e instanceof NullPointerException) {
				ConsoleTextArea.AppendMessageOnCurrentConsole("调试脚本缺少main函数");
			}else {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
			}
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	
	
}
