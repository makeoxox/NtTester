package nt.com.buiz;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.enums.TextType;
import nt.com.script.java.JavaRun;
import nt.com.script.javascript.debug.DebugScriptManager;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.MainView;
import nt.com.view.init.ScriptDebugDialog;
import nt.com.view.init.TopToolBar;

public class ScriptDebugDialogController {
	
	@FXML
	private VBox scriptdebugdialog;
	
    @FXML
    private TextField pathfield;

    @FXML
    private TextField argfield;

    @FXML
    private TextField timeoutfield;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button debugbtn;

    @FXML
    void cancel(ActionEvent event) {
    	((Stage) scriptdebugdialog.getScene().getWindow()).close();
    }

    @FXML
    void debug(ActionEvent event) {
    	String path =pathfield.getText();
    	String arg = argfield.getText();
    	String timeout= timeoutfield.getText();
    	TopToolBar ttb = (TopToolBar)MainView.parent.lookup("#toptoolbar");
    	Button scriptBtn = (Button) ttb.getItems().get(5);
    	ScriptDebugDialog sdd = (ScriptDebugDialog)scriptdebugdialog;
    	if(sdd.textType==TextType.JAVASCRIPT) {
    		Task<Object> task = new Task<Object>() {
				@Override
				public Object call() {
					scriptBtn.setDisable(true);
					try {
						DebugScriptManager dsm = new DebugScriptManager(path.trim());
						if (arg == null || arg.equals("")) {
							return dsm.invoke(null);
						} else {
							return dsm.invoke(arg);
						}
					}catch(Exception e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
					}
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (timeout.trim() == null || timeout.trim().equals("") || timeout.trim().equals("0")) {
							task.get();
						} else {
							task.get(Integer.parseInt(timeout), TimeUnit.MILLISECONDS);
						}
					} catch (InterruptedException e1) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e1.getLocalizedMessage());
					} catch (ExecutionException e1) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e1.getLocalizedMessage());
					} catch (TimeoutException e1) {
						task.cancel(true);
						
					} finally {
						scriptBtn.setDisable(false);
						ConsoleTextArea.AppendMessageOnCurrentConsole("调试脚本["+path+"]结束");
					}
				}
			}).start();
    	}else if(sdd.textType==TextType.JAVA){
    		Task<Object> task = new Task<Object>() {
				@Override
				public Object call() {
					try {
						String code = Utils.ReadFiletoString(new File(path), "GBK");
						new JavaRun().run(new String[] {arg}, code);
					} catch (IOException e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
					}
					return null;
				}
			};
			new Thread(task).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (timeout.trim() == null || timeout.trim().equals("") || timeout.trim().equals("0")) {
							task.get();
						} else {
							task.get(Integer.parseInt(timeout), TimeUnit.MILLISECONDS);
						}
					} catch (InterruptedException e1) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e1.getLocalizedMessage());
					} catch (ExecutionException e1) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e1.getLocalizedMessage());
					} catch (TimeoutException e1) {
						task.cancel(true);
						
					} finally {
						scriptBtn.setDisable(false);
						ConsoleTextArea.AppendMessageOnCurrentConsole("调试脚本["+path+"]结束");
					}
				}
			}).start();
    	}
	
			((Stage) scriptdebugdialog.getScene().getWindow()).close();
    }
}
