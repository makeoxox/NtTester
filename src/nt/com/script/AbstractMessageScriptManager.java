
package nt.com.script;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import nt.com.view.init.ConsoleTextArea;

/**
 * 抽象脚本管理器
 *
 * @author kege
 */
public abstract class AbstractMessageScriptManager {

	public static  ScriptEngine engine;
	private ScriptEngineManager sem;

	protected static final Log log = LogFactory.getLog(AbstractMessageScriptManager.class);

	protected AbstractMessageScriptManager() {
		sem = new ScriptEngineManager();
	}

	// 调用头js文件
	protected void loadLib() {

		if (engine == null) {
			engine = sem.getEngineByName("javascript");
			try {
				engine.eval(new InputStreamReader(AbstractMessageScriptManager.class.getResourceAsStream("/nt/com/script/h.js")));
			} catch (Exception e) {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}

	}
	
	
	protected Invocable getInvocable(String path) {
		try {
			if (engine != null) {
				String Rpath = path;
				File scriptFile = new File(Rpath);
				if (!scriptFile.exists()) {
					return null;
				}
				engine.eval(new FileReader(scriptFile));
			}
			return (Invocable) engine;
		} catch (Exception e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

	}

	public abstract String invoke(Object arg);

}
