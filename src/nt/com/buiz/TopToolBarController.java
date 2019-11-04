package nt.com.buiz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.enmu.TextType;
import nt.com.model.FileTreeModel;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.LeftTreeView;
import nt.com.view.init.MainView;
import nt.com.view.init.NewFileView;
import nt.com.view.init.RichEditTextArea;
import nt.com.view.init.ScriptDebugDialog;
import nt.com.view.init.TopMenuBar;

/**
 * �ϲ��������¼�������
 * @author kege
 *
 */
public class TopToolBarController {

	@FXML
	private Button newfilebtn;

	@FXML
	private Button savefilebtn;

	@FXML
	private Button importfilebtn;

	@FXML
	private Button debugbtn;

	@FXML
	private Button runbtn;

	@FXML
	private Button stopbtn;

	@FXML
	private Button clearbtn;

	@FXML
	private Button setbtn;

	@FXML
	private Button multibtn;
	
	/**
	 * �ֶ������ⲿ����Ŀ
	 */
	@FXML
	void importPackage(ActionEvent event) {
		ImageView dirIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/dir.png")));
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setInitialDirectory(new File("workspace"));
		dirChooser.setTitle("�����ļ���");
		File dir = dirChooser.showDialog(MainView.parent.getScene().getWindow());
		if(dir==null)return;
		File[] files = dir.listFiles();
		FileTreeModel dirMod = new FileTreeModel(dir.getName(),dir.getAbsolutePath(),dir);
		TreeItem<FileTreeModel> newDir = new TreeItem<FileTreeModel>(dirMod, dirIcon);
		ltv.getRoot().getChildren().add(newDir);
		iteratorDir(files, newDir);
		Config.addImportDirectory(dir.getAbsolutePath());
	}

	// �����ļ��е�Tree
	void iteratorDir(File[] files, TreeItem<FileTreeModel> node) {
		for (File file : files) {
			if (file.isDirectory()) {
				ImageView dirIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/dir.png")));
				FileTreeModel fileMod = new FileTreeModel(file.getName(),file.getAbsolutePath(),file);
				TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(fileMod, dirIcon);
				node.getChildren().add(newDirNode);
				iteratorDir(file.listFiles(), newDirNode);
			} else {
				ImageView fileIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/file.png")));
				FileTreeModel fileMod = new FileTreeModel(file.getName(),file.getAbsolutePath(),file);
				TreeItem<FileTreeModel> newFileNode = new TreeItem<FileTreeModel>(fileMod, fileIcon);
				node.getChildren().add(newFileNode);
			}
		}
	}
	
	/**
	 * ���浱ǰ�༭���ļ�
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void savaFile(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null)return;
		String fileAbsPath =currentEditTab.getId();
		VirtualizedScrollPane<RichEditTextArea> sp = (VirtualizedScrollPane<RichEditTextArea>)currentEditTab.getContent();
		RichEditTextArea eta=(RichEditTextArea)sp.getContent();
		String content =eta.getText();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		alert.setTitle("����");
		alert.setHeaderText(null);
		alert.setContentText("�Ƿ񱣴� "+fileAbsPath+"��");
		Optional<ButtonType> result = alert.showAndWait();
		TopMenuBar mb =(TopMenuBar)MainView.parent.lookup("#topmenubar");
		Menu codeMenu = mb.getMenus().get(0);
		RadioMenuItem rmi= (RadioMenuItem) codeMenu.getItems().get(0);
		rmi =(RadioMenuItem) rmi.getToggleGroup().getSelectedToggle();
		String code = rmi.getText();
		if(result.get()==ButtonType.OK) {
			File file = new File(fileAbsPath);
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),code));
				bw.write(content);
				bw.flush();
				bw.close();
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * �½��ļ�
	 */
	@FXML
	void newFile(ActionEvent event) {
			new NewFileView();
	}
	
	/**
	 * ���Խű�
	 */
	@FXML
	void debug(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("����");
			alert.setHeaderText(null);
			alert.setContentText("���ȴ�һ��Js�ļ���");
			alert.showAndWait();
			return;
		}else {
			String fileAbsPath =currentEditTab.getId();
			File file = new File(fileAbsPath);
			TextType tt =Utils.getTextType(file);
			if(tt!=TextType.JAVASCRIPT) {
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("�ļ� "+file.getName()+" ����Js�ļ���");
				alert.showAndWait();
				return;
			}else {
				ScriptDebugDialog sdd = new ScriptDebugDialog();
				TextField pathfield =(TextField) sdd.lookup("#pathfield");
				pathfield.setText(fileAbsPath);
			}
		}
	}
	

	/**
	 * ��յ�ǰ����̨
	 */
	@FXML
	void clear(ActionEvent evnet) {
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
		Tab tab =mctp.getSelectionModel().getSelectedItem();
		ConsoleTextArea cta = (ConsoleTextArea) tab.getContent();
		cta.clear();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
		String dateStr =sdf.format(date);
		cta.setText(dateStr);
	}
	
}