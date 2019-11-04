package nt.com.buiz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.enmu.TextType;
import nt.com.util.Utils;
import nt.com.view.init.FontChooser;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;
import nt.com.view.init.ScriptDebugDialog;
import nt.com.view.init.TopMenuBar;

/**
 * 编辑文本域事件控制器
 * 
 * @author kege
 *
 */
public class RichEditTextAreaController {

	@FXML
	private ContextMenu editmenu;

	@FXML
	private MenuItem savebtn;

	@FXML
	private MenuItem textsetbtn;

	@FXML
	private MenuItem formatbtn;

	@FXML
	private MenuItem runbtn;

	@FXML
	private MenuItem debugbtn;

	@FXML
	void format(ActionEvent event) {

	}

	@FXML
	void run(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	@FXML
	void save(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab = metp.getSelectionModel().getSelectedItem();
		if (currentEditTab == null)
			return;
		String fileAbsPath = currentEditTab.getId();
		VirtualizedScrollPane<RichEditTextArea> sp = (VirtualizedScrollPane<RichEditTextArea>) currentEditTab
				.getContent();
		RichEditTextArea eta = (RichEditTextArea) sp.getContent();
		String content = eta.getText();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		alert.setTitle("保存");
		alert.setHeaderText(null);
		alert.setContentText("是否保存 " + fileAbsPath + "？");
		Optional<ButtonType> result = alert.showAndWait();
		TopMenuBar mb = (TopMenuBar) MainView.parent.lookup("#topmenubar");
		Menu codeMenu = mb.getMenus().get(0);
		RadioMenuItem rmi = (RadioMenuItem) codeMenu.getItems().get(0);
		rmi = (RadioMenuItem) rmi.getToggleGroup().getSelectedToggle();
		String code = rmi.getText();
		if (result.get() == ButtonType.OK) {
			File file = new File(fileAbsPath);
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), code));
				bw.write(content);
				bw.flush();
				bw.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void textset(ActionEvent event) {
		new FontChooser("Edit");
	}

	@FXML
	void debug(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab = metp.getSelectionModel().getSelectedItem();
		if (currentEditTab == null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("提醒");
			alert.setHeaderText(null);
			alert.setContentText("请先打开一个Js文件！");
			alert.showAndWait();
			return;
		} else {
			String fileAbsPath = currentEditTab.getId();
			File file = new File(fileAbsPath);
			TextType tt = Utils.getTextType(file);
			if (tt != TextType.JAVASCRIPT) {
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("文件 " + file.getName() + " 不是Js文件！");
				alert.showAndWait();
				return;
			} else {
				ScriptDebugDialog sdd = new ScriptDebugDialog();
				TextField pathfield = (TextField) sdd.lookup("#pathfield");
				pathfield.setText(fileAbsPath);
			}
		}
	}

}
