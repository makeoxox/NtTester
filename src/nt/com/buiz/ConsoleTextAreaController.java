package nt.com.buiz;

import java.text.SimpleDateFormat;
import java.util.Date;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.FontChooser;
import nt.com.view.init.MainView;

/**
 * 控制台文本域事件控制器
 * 
 * @author kege
 *
 */
public class ConsoleTextAreaController {

	@FXML
	private ContextMenu consolemenu;

	@FXML
	private MenuItem clearbtn;

	@FXML
	private MenuItem textsetbtn;

	@FXML
	void clear(ActionEvent event) {
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
		Tab tab =mctp.getSelectionModel().getSelectedItem();
		ConsoleTextArea cta = (ConsoleTextArea) tab.getContent();
		cta.clear();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
		String dateStr =sdf.format(date);
		cta.setText(dateStr);
	}

	@FXML
	void textset(ActionEvent event) {
		new FontChooser("Console");
	}

}
