package nt.com.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import nt.com.view.init.ConsoleTextArea;



/**
 *脚本管理器
 *
 * @author kege
 */
public abstract class AbstractMessageScriptManager {

	protected ScriptEngine engine;
	private ScriptEngineManager sem;
	
	protected static final Log log = LogFactory.getLog(AbstractMessageScriptManager.class);

	
	protected AbstractMessageScriptManager() {
		sem = new ScriptEngineManager();
	}
	
	//调用头js文件
	protected void loadLib()  {
		
			if (engine == null) {
				engine = sem.getEngineByName("javascript");
				File headFile = new File("script/h.js");
				if (!headFile.exists()) {
					return ;
				}
				try {
					engine.eval(new FileReader(headFile));
				} catch (FileNotFoundException e) {
					ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
				} catch (ScriptException e) {
					ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
				}
			}
		
	}
	
	protected Invocable getInvocable(String path) {
		
			if (engine != null) {
				String Rpath =  path;
				File scriptFile = new File(Rpath);
				if (!scriptFile.exists()) {
					return null;
				}
				try {
					engine.eval(new FileReader(scriptFile));
				} catch (FileNotFoundException e) {
					ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
				} catch (ScriptException e) {
					ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
				}
			}
			return (Invocable) engine;
		
	}
	
	public abstract String invoke(String message);
	
}
