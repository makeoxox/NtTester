package nt.com.buiz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.dom4j.Document;
import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import nt.com.enums.TextType;
import nt.com.global.Config;
import nt.com.util.HtmlParser;
import nt.com.util.JsonParser;
import nt.com.util.Utils;
import nt.com.util.XmlParser;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.FindView;
import nt.com.view.init.FontChooser;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;
import nt.com.view.init.ScriptDebugDialog;
import nt.com.view.init.TopMenuBar;

/**
 * �༭�ı����¼�������
 * 
 * @author kege
 *
 */
public class RichEditTextAreaController {
	
	private KeyCombination saveEvent = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN); //��ϰ����¼� Ctrl+S 
	private KeyCombination deleteLineEvent = new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN); //��ϰ����¼� Ctrl+D
	private KeyCombination findEvent = new  KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN); //��ϰ����¼� Ctrl+F

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
	
	/**
	 * ��ʽ���ı�
	 * 
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void format(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab tab =metp.getSelectionModel().getSelectedItem();
		VirtualizedScrollPane<RichEditTextArea> vp =   (VirtualizedScrollPane<RichEditTextArea>) tab.getContent();
		RichEditTextArea rta= (RichEditTextArea) vp.getContent();
		TextType tt = rta.getTextType();
		String text =rta.getText();
		if(tt==TextType.JSON) {
			try {
				text = JsonParser.convertFormatJsonStr(text);
				rta.clear();
				rta.setText(text, TextType.JSON);
			}catch(Exception e) {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
			}
		}else if(tt==TextType.XML) {
			try {
				Document xmldoc=XmlParser.getDocByString(text, Config.getEncode());
				text=XmlParser.convertFormatXMLStr(xmldoc, Config.getEncode());
				rta.clear();
				rta.setText(text, TextType.XML);
			}catch(Exception e) {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
			}
		}else if(tt==TextType.HTML) {
			try {
				text = HtmlParser.convertFormatHTMLStr(text,Config.getEncode());
				rta.clear();
				rta.setText(text, TextType.HTML);
			}catch(Exception e) {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.getLocalizedMessage());
			}
		}
		
	}

	@FXML
	void run(ActionEvent event) {

	}
	
	/**
	 * ����
	 *
	 */
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
		alert.setTitle("����");
		alert.setHeaderText(null);
		alert.setContentText("�Ƿ񱣴� " + fileAbsPath + "��");
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
	
	/**
	 * �ı�����
	 *
	 */
	@FXML
	void textset(ActionEvent event) {
		new FontChooser("Edit");
	}
	
	/**
	 * ���Խű�
	 *
	 */
	@FXML
	void debug(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab = metp.getSelectionModel().getSelectedItem();
		if (currentEditTab == null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("����");
			alert.setHeaderText(null);
			alert.setContentText("���ȴ�һ��Js��Java�ļ���");
			alert.showAndWait();
			return;
		} else {
			String fileAbsPath = currentEditTab.getId();
			File file = new File(fileAbsPath);
			TextType tt = Utils.getTextType(file);
			if(tt==TextType.JAVASCRIPT || tt==TextType.JAVA) {
				ScriptDebugDialog sdd = new ScriptDebugDialog(tt);
				TextField pathfield =(TextField) sdd.lookup("#pathfield");
				pathfield.setText(fileAbsPath);
			}else {
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("�ļ� "+file.getName()+" ����Js��Java�ļ���");
				alert.showAndWait();
				return;
			}
		}
	}
	
	//��ϼ����¼�
	@SuppressWarnings("unchecked")
	@FXML
	void keyPress(KeyEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab tab =metp.getSelectionModel().getSelectedItem();
		VirtualizedScrollPane<RichEditTextArea> vp =   (VirtualizedScrollPane<RichEditTextArea>) tab.getContent();
		RichEditTextArea rta= (RichEditTextArea) vp.getContent();
		if(saveEvent.match(event)) {  //�����¼��������ļ�����
			String fileAbsPath = tab.getId();
			String content = rta.getText();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("����");
			alert.setHeaderText(null);
			alert.setContentText("�Ƿ񱣴� " + fileAbsPath + "��");
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
		if(deleteLineEvent.match(event)) { //ɾ�����¼� �� ��ɾ��ѡ������ ����ɾ�����в�������ݡ�
			rta.deleteText(new IndexRange(rta.getSelection().getStart(),rta.getSelection().getEnd()));
			rta.selectLine();
			rta.deleteText(new IndexRange(rta.getSelection().getStart(),rta.getSelection().getEnd()));
		}
		if(findEvent.match(event)) { //�����¼���ȫ�Ĳ������ݡ�
			new FindView();
		}
	}
	
}
