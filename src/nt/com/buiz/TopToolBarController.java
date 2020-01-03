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

import org.dom4j.DocumentException;
import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import nt.com.buiz.comm.Comm;
import nt.com.enums.TextType;
import nt.com.global.Config;
import nt.com.model.FileTreeModel;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.HttpMsgSettingView;
import nt.com.view.init.LeftTreeView;
import nt.com.view.init.MainView;
import nt.com.view.init.MultiJsonView;
import nt.com.view.init.MultiTxtView;
import nt.com.view.init.MultiXMLView;
import nt.com.view.init.NewFileView;
import nt.com.view.init.RichEditTextArea;
import nt.com.view.init.ScriptDebugDialog;
import nt.com.view.init.TcpMsgSettingView;

/**
 * 上部工具栏事件控制器
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
	protected Button runbtn;

	@FXML
	protected Button stopbtn;

	@FXML
	private Button clearbtn;

	@FXML
	private Button setbtn;

	@FXML
	private Button multibtn;
	
	/**
	 * 手动导入外部的项目
	 */
	@FXML
	void importPackage(ActionEvent event) {
		ImageView dirIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/import.gif")));
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setInitialDirectory(new File("projects"));
		dirChooser.setTitle("导入文件夹");
		File dir = dirChooser.showDialog(MainView.parent.getScene().getWindow());
		if(dir==null)return;
		File[] files = dir.listFiles();
		FileTreeModel dirMod = new FileTreeModel(dir.getName(),dir.getAbsolutePath(),dir);
		TreeItem<FileTreeModel> newDir = new TreeItem<FileTreeModel>(dirMod, dirIcon);
		ltv.getRoot().getChildren().add(newDir);
		iteratorDir(files, newDir);
		Config.addImportDirectory(dir.getAbsolutePath());
	}

	// 绘制文件夹的Tree
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
	 * 保存当前编辑中文件
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
		alert.setTitle("保存");
		alert.setHeaderText(null);
		alert.setContentText("是否保存 "+fileAbsPath+"？");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK) {
			File file = new File(fileAbsPath);
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),Config.getEncode()));
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
	 * 新建文件
	 */
	@FXML
	void newFile(ActionEvent event) {
			new NewFileView();
	}
	
	/**
	 * 调试脚本
	 */
	@FXML
	void debug(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("提醒");
			alert.setHeaderText(null);
			alert.setContentText("请先打开一个Js文件！");
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
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("文件 "+file.getName()+" 不是Js文件！");
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
	 * 清空当前控制台
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
	
	/**
	 * 报文设置
	 */
	@FXML
	void msgset(ActionEvent event) {
		String protocol= Config.getProtocol();
		if(protocol.equals("tcp")) {
			 new TcpMsgSettingView();
		}else if(protocol.equals("http")) {
			new HttpMsgSettingView();
		}else if(protocol.equals("udp")) {
			
		}
	}
	
	/**
	 * 并发设置
	 * 
	 */
	@FXML
	void multiset(ActionEvent event) {
		String msg = Config.getMsg();
		if(msg.equals("xml")) {
			 new MultiXMLView();
		}else if(msg.equals("json")) {
			new MultiJsonView();
		}else if(msg.equals("txt")) {
			new MultiTxtView();
		}
	}
	
	
	@FXML
	void run(ActionEvent event) {
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("提醒");
			alert.setHeaderText(null);
			alert.setContentText("请先打开一个文件！");
			alert.showAndWait();
			return;
		}else {
			String fileAbsPath =currentEditTab.getId();
			File file = new File(fileAbsPath);
				try {
					Comm sender = new Comm(file);
					sender.send();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| DocumentException | IOException e) {
					ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
					e.printStackTrace();
				}
				
			
		}
	
	}
}
