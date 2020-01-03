package nt.com.view.init;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import nt.com.global.Config;
/**
 * 控制台
 * @author kege
 *
 */
public class ConsoleTextArea extends TextArea{
	
	public ConsoleTextArea() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/ConsoleTextArea.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		this.setEditable(false);
		this.setFont(Config.getConsoleFont());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
		String dateStr =sdf.format(date);
		this.setText(dateStr);
	}
	
	/**
	 * 换行追加内容到当前控制台
	 * 
	 * @param message
	 */
	public static void AppendMessageOnCurrentConsole(String message) {
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
    	TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
    	int index =metp.getSelectionModel().getSelectedIndex();
    	mctp.getSelectionModel().select(index);
    	ConsoleTextArea cta = (ConsoleTextArea) mctp.getSelectionModel().getSelectedItem().getContent();
    	cta.appendText("\n"+message);
	}
	
	/**
	 * 追加内容到当前控制台
	 * 
	 * @param message
	 */
	public static void PrintMessageOnCurrentConsole(String message) {
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
    	TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
    	int index =metp.getSelectionModel().getSelectedIndex();
    	mctp.getSelectionModel().select(index);
    	ConsoleTextArea cta = (ConsoleTextArea) mctp.getSelectionModel().getSelectedItem().getContent();
    	cta.appendText(message);
	}
	
}
