package nt.com.buiz;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.script.debug.DebugScriptManager;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.MainView;
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
    	Stage stage = (Stage)scriptdebugdialog.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void debug(ActionEvent event) {
    	String path =pathfield.getText();
    	String arg = argfield.getText();
    	String timeout= timeoutfield.getText();
    	TopToolBar ttb = (TopToolBar)MainView.parent.lookup("#toptoolbar");
    	Button scriptBtn = (Button) ttb.getItems().get(5);
    	Stage stage = (Stage)scriptdebugdialog.getScene().getWindow();
    	stage.close();
    	FutureTask<Object> task = null;
		try {
			task = new FutureTask<Object>(new Callable<Object>() {
				@Override
				public Object call() {
					scriptBtn.setDisable(true);
					DebugScriptManager dsm = new DebugScriptManager(path.trim());
					if (arg == null || arg.equals("")) {
						return dsm.invoke(null);
					} else {
						return dsm.invoke(arg);
					}
				}
			});
			new Thread(task).start();
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
		}
    }

}
