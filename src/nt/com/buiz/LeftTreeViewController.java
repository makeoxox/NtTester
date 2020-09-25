package nt.com.buiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;

import freemarker.template.TemplateException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nt.com.enums.TextType;
import nt.com.global.Config;
import nt.com.global.TemplateFile;
import nt.com.model.FileTreeModel;
import nt.com.util.RegexUtils;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.LeftTreeView;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;

/**
 * ����ļ����¼�������
 * @author kege
 *
 */
public class LeftTreeViewController {

	@FXML
	private ContextMenu lefttreemenu;

	@FXML
	private Menu lefttreeadd;
	
	@FXML
	private MenuItem addprojectbtn;

	@FXML
	private MenuItem addfoldbtn;
	
	@FXML
	private MenuItem addtxtbtn;
	
	@FXML
	private MenuItem addxmlbtn;

	@FXML
	private MenuItem addjsbtn;

	@FXML
	private MenuItem addjsonbtn;
	
	@FXML
	private MenuItem addjavabtn;

	@FXML
	private MenuItem lefttreedel;
	
	@FXML
	private MenuItem lefttreecopy;
	
	@FXML
	private MenuItem lefttreepaste;
	
	@FXML
	private MenuItem lefttreermv;

	@FXML
	private MenuItem lefttreerename;
	
	@FXML
	private MenuItem lefttreerefresh;
	
	/**
	 * �����ļ��л��ļ�
	 */
	@FXML  
	void add(ActionEvent event) throws IOException {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		TextInputDialog dialog = new TextInputDialog();
		Stage dialogStage =(Stage) dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		dialog.setHeaderText(null);
		ImageView icon=null;
		MenuItem clickedBtn =(MenuItem) event.getTarget(); //�ж��½��ļ�����
		if(clickedBtn==addprojectbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/import.gif")));
			dialog.setContentText("�½���Ŀ����");
			dialog.setTitle("������Ŀ");
		}else if(clickedBtn==addfoldbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/dir.png")));
			dialog.setContentText("�½��ļ�������");
			dialog.setTitle("�����ļ���");
		}else if(clickedBtn==addtxtbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
			dialog.setContentText("�½��ı��ļ�����");
			dialog.setTitle("�����ı��ļ�");
		}else if(clickedBtn==addjsbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
			dialog.setContentText("�½�js�ļ�����");
			dialog.setTitle("����js�ļ�");
		}else if(clickedBtn==addxmlbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
			dialog.setContentText("�½�xml�ļ�����");
			dialog.setTitle("����xml�ļ�");
		}else if(clickedBtn==addjsonbtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
			dialog.setContentText("�½�json�ļ�����");
			dialog.setTitle("����json�ļ�");
		}else if(clickedBtn==addjavabtn) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
			dialog.setContentText("�½�java�ļ�����");
			dialog.setTitle("����java�ļ�");
		}
		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()){
		   return;
		} 
		if(item==null) {  //�Ҽ�ûѡ��treeitem��Ĭ��Ϊ�Ӹ��ڵ��½�
			if(clickedBtn!=addprojectbtn) {  //���ڵ㲻֧�ִ����ļ���ֻ����Ŀ
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("��Ŀ¼ֻ�ܴ�����Ŀ��");
				alert.showAndWait();
				return;
			}
			TreeItem<FileTreeModel> root =ltv.getRoot();
			File newfile = new File("projects/"+result.get());
			newfile.mkdir();
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/import.gif")));
			FileTreeModel newfileMod = new FileTreeModel(newfile.getName(), newfile.getAbsolutePath(), newfile);
			TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(newfileMod, icon);
			root.getChildren().add(newDirNode);
		}else {
			if(clickedBtn==addprojectbtn) {
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("��Ŀֻ���ڸ��ļ��д�����");
				alert.showAndWait();
				return;
			}
			File file=item.getValue().getFile();
			File newfile=null;
			Writer write=null;
			String content = "";
			if(file.isDirectory()) {  //������ļ��� ���ڸ��ļ����´���
				if(clickedBtn==addfoldbtn) {
					newfile = new File(file.getAbsolutePath()+File.separator+result.get());
					newfile.mkdir();
				}else if(clickedBtn==addtxtbtn) {
					newfile = new File(file.getAbsolutePath()+File.separator+result.get()+".txt");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content="");
				}else if(clickedBtn==addjsbtn) {
					newfile = new File(file.getAbsolutePath()+File.separator+result.get()+".js");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsTemplate());
				}else if(clickedBtn==addxmlbtn) {
					newfile = new File(file.getAbsolutePath()+File.separator+result.get()+".xml");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.XmlTemplate());
				}else if(clickedBtn==addjsonbtn) {
					newfile = new File(file.getAbsolutePath()+File.separator+result.get()+".json");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsonTemplate());
				}else if(clickedBtn==addjavabtn) {
					try {
						if(RegexUtils.ClassNameMatche(result.get())) {
							newfile = new File(file.getAbsolutePath()+File.separator+result.get()+".java");
							newfile.createNewFile();
							write = new OutputStreamWriter(new FileOutputStream(newfile));
							write.write(content=TemplateFile.JavaTemplate(result.get()));
						}else {
							Alert alert = new Alert(AlertType.WARNING);
							Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
							alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
							alert.setTitle("����");
							alert.setHeaderText(null);
							alert.setContentText("Java�ļ��������Ϲ淶��");
							alert.showAndWait();
							return;
						}
					} catch (TemplateException e) {
						if(write!=null) {
							write.flush();
							write.close();
						}
						return;
					}
				}
				if(write!=null) {
					write.flush();
					write.close();
				}
				FileTreeModel newfileMod = new FileTreeModel(newfile.getName(), newfile.getAbsolutePath(), newfile);
				TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(newfileMod, icon);
				item.getChildren().add(newDirNode);
				ltv.getSelectionModel().select(newDirNode);
			}else {  //������ļ������ڸ��ļ�ͬ������
				if(clickedBtn==addfoldbtn) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get());
					newfile.mkdir();
				}else if(clickedBtn==addtxtbtn) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get()+".txt");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content="");
				}else if(clickedBtn==addjsbtn) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get()+".js");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsTemplate());
				}else if(clickedBtn==addxmlbtn) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get()+".xml");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.XmlTemplate());
				}else if(clickedBtn==addjsonbtn) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get()+".json");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsonTemplate());
				}else if(clickedBtn==addjavabtn) {
					try {
						if(RegexUtils.ClassNameMatche(result.get())) {
							newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+result.get()+".java");
							newfile.createNewFile();
							write = new OutputStreamWriter(new FileOutputStream(newfile));
							write.write(content=TemplateFile.JavaTemplate(result.get()));
						}else {
							Alert alert = new Alert(AlertType.WARNING);
							Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
							alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
							alert.setTitle("����");
							alert.setHeaderText(null);
							alert.setContentText("Java�ļ��������Ϲ淶��");
							alert.showAndWait();
							return;
						}
					} catch (TemplateException e) {
						if(write!=null) {
							write.flush();
							write.close();
						}
						return;
					}
				}
				if(write!=null) {
					write.flush();
					write.close();
				}
				FileTreeModel newfileMod = new FileTreeModel(newfile.getName(), newfile.getAbsolutePath(), newfile);
				TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(newfileMod, icon);
				item.getParent().getChildren().add(newDirNode);
				ltv.getSelectionModel().select(newDirNode);
			}
			//������½��ļ�����ļ�
			if(clickedBtn==addfoldbtn || clickedBtn==addprojectbtn) {
				return;
			}
			TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
			TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
			ObservableList<Tab> edittabList = metp.getTabs();
			ObservableList<Tab> consoletabList = mctp.getTabs();
			//�Ѿ��򿪵��ļ�ֱ��ѡ��tab
			for (int j = 0; j < edittabList.size(); j++) {
				Tab edittab = edittabList.get(j);
				Tab consoletab = consoletabList.get(j);
				if (edittab.getText().equals(newfile.getName())) {
					metp.getSelectionModel().select(edittab);
					mctp.getSelectionModel().select(consoletab);
					return;
				}
			}
			Tab edittab =new Tab(newfile.getName());
			edittab.setId(newfile.getAbsolutePath());
			edittab.setGraphic(new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png"))));
			edittabList.add(edittab);
			Tab consoletab = new Tab(newfile.getName() + " - console");
			consoletab.setGraphic(new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/console.png"))));
			consoletab.setId(newfile.getAbsolutePath());
			consoletabList.add(consoletab);
			
			edittab.setOnClosed((CloseEvent) -> {
				consoletabList.remove(consoletab);
			});
			consoletab.setOnClosed((CloseEvent) -> {
				edittabList.remove(edittab);
			});
			metp.getSelectionModel().select(edittab);
			mctp.getSelectionModel().select(consoletab);
			RichEditTextArea eta = new RichEditTextArea();
			ConsoleTextArea cta = new ConsoleTextArea();
			 if(clickedBtn==addjsbtn) {
				 eta.setText(content,TextType.JAVASCRIPT);
			}else if(clickedBtn==addxmlbtn) {
				eta.setText(content,TextType.XML);
			}else if(clickedBtn==addjsonbtn) {
				eta.setText(content,TextType.JSON);
			}else if(clickedBtn==addtxtbtn) {
				eta.setText(content,TextType.TXT);
			}else if(clickedBtn==addjavabtn){
				eta.setText(content,TextType.JAVA);
			}else {
				eta.setText(content,TextType.UNKNOW);
			}
			 VirtualizedScrollPane<RichEditTextArea> sp = new VirtualizedScrollPane<RichEditTextArea>(eta);
			edittab.setContent(sp);
			consoletab.setContent(cta);
			Config.setLastOpenFilePath(newfile.getPath());
		}
	}
	
	/**
	 *  ɾ���ļ�
	 */
	@FXML  
	void del(ActionEvent event) {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		if(item==null) {
			return;
		}else {
			File targetFile =item.getValue().getFile();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("ɾ��");
			alert.setHeaderText(null);
			alert.setContentText("�Ƿ�ɾ�� "+targetFile.getAbsolutePath()+"�������ļ���");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get()==ButtonType.CANCEL)return;
			if(targetFile.isDirectory()) {   
				deleteDir(targetFile);
			}else {
				targetFile.delete();
			}
			Config.delImportDirectory(targetFile.getAbsolutePath()); 
			item.getParent().getChildren().remove(item);
		}
	}
	
	//�ݹ�ɾ���ǿ��ļ���
	void deleteDir(File file) {
		File files[]=file.listFiles();
		for(File f:files) {
			if(!f.isDirectory()) {
				f.delete();
			}else {
				deleteDir(f);
			}
		}
		file.delete();
	}
	

	/**
	 * ����
	 * 
	 */
	@FXML 
	void  copy(ActionEvent event) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent cc = new ClipboardContent();
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		if(item==null) {
			return;
		}else {
			File targetFile =item.getValue().getFile();
			List<File> files = new ArrayList<File>();
			files.add(targetFile);
			cc.putFiles(files);
			clipboard.setContent(cc);
		}
	}
	
	/**
	 * ճ��
	 * 
	 */
	@SuppressWarnings("unchecked")
	@FXML 
	void  paste(ActionEvent event) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		if(item==null) {
			return;
		}else {
			List<File> files =(List<File>) clipboard.getContent(DataFormat.FILES);
			File copyFile = (File) files.get(0);
			if(copyFile==null|| !copyFile.exists()) {
				return ;
			}else {
				File anchorFile =  item.getValue().getFile();
				//������ļ��������ļ�����ճ��
				if(anchorFile.isDirectory()) {
					try {
						boolean exists=false;
						for(File file:anchorFile.listFiles()) {
							if(file.getName().equals(copyFile.getName())) {
								exists=true;
								break;
							}
						}
						if(!exists) {
							if(copyFile.isDirectory()) { 
								FileUtils.copyDirectoryToDirectory(copyFile, anchorFile);
							}else {
								FileUtils.copyFileToDirectory(copyFile, anchorFile);
							}
						}else {
							Alert alert = new Alert(AlertType.WARNING);
							Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
							alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
							alert.setTitle("����");
							alert.setHeaderText(null);
							alert.setContentText("�Ѵ���ͬ���ļ���");
							alert.showAndWait();
							return;
						}
					} catch (IOException e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
					}
				}else {
					try {
						if(copyFile.getName().equals(anchorFile.getName())) {
							Alert alert = new Alert(AlertType.WARNING);
							Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
							alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
							alert.setTitle("����");
							alert.setHeaderText(null);
							alert.setContentText("�Ѵ���ͬ���ļ���");
							alert.showAndWait();
							return;
						}else {
							if(copyFile.isDirectory()) {
								FileUtils.copyDirectoryToDirectory(copyFile, anchorFile.getParentFile());
							}else {
								FileUtils.copyFileToDirectory(copyFile,  anchorFile.getParentFile());
							}
						}
					} catch (IOException e) {
						ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
					}
				}
			}
			refresh(null);
		}
	}
	
	
	
	/**
	 *  ���ļ������Ƴ��ⲿ��Ŀ
	 */
	@FXML
	void remove(ActionEvent evnet) {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		if(item==null)return;
		if(item.getParent()!=ltv.getRoot()) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("����");
			alert.setHeaderText(null);
			alert.setContentText("ֻ���Ƴ����ļ��У�");
			alert.showAndWait();
			return;
		}
		Config.delImportDirectory(item.getValue().getFilePathName());
		ltv.getRoot().getChildren().remove(item);
	}
	
	/**
	 * �������ļ�
	 * 
	 */
	@FXML  
	void rename(ActionEvent event) {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		if(item==null)return;
		File oldFile =item.getValue().getFile();
		TextInputDialog dialog = new TextInputDialog(oldFile.getName());
		Stage dialogStage =(Stage) dialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		dialog.setHeaderText(null);
		dialog.setTitle("������");
		dialog.setContentText("������Ϊ��");
		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()){
		   return;
		} 
		File newfile =new File(oldFile.getParentFile().getAbsolutePath()+File.separator+result.get());
		oldFile.renameTo(newfile);
		item.setValue(new FileTreeModel(result.get(),newfile.getAbsolutePath(),newfile));
		
	}
	
	/**
	 * ˢ���ļ���
	 * 
	 */
	@FXML 
	void  refresh(ActionEvent event) {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> root = new TreeItem<FileTreeModel>();
		root.setExpanded(true);
		ltv.setRoot((TreeItem<FileTreeModel>) root);
		LeftTreeLoad();
		ltv.getSelectionModel().clearSelection();
	}
	
	
	
	// ���н����ʼ���󣬶�ȡprojects����Ŀ�Լ��ⲿ������Ŀ�������ϴ����򿪵��ļ���
	public static void LeftTreeLoad() {
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
		String filepath = Config.getLastOpenFilePath();
		File lastFile = new File(filepath);
		File dir = new File("projects");
		File [] dirs = dir.listFiles();
		for(File project:dirs) {   //���ع����ռ��ڵ��ļ���
			ImageView dirIcon = new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/import.gif")));
			FileTreeModel fileMode = new FileTreeModel(project.getName(), project.getAbsolutePath(), project);
			TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(fileMode, dirIcon);
			ltv.getRoot().getChildren().add(newDirNode);
			iteratorDir(project.listFiles(), newDirNode,project,ltv);
		}
		List<String> externalDir = Config.getImportDirectories();  //�����ⲿ�ļ���
		for(String exDirPath:externalDir) {  
			File exDir = new File(exDirPath);
			ImageView dirIcon = new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/import.gif")));
			FileTreeModel fileMode = new FileTreeModel(exDir.getName(), exDir.getAbsolutePath(), exDir);
			TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(fileMode, dirIcon);
			ltv.getRoot().getChildren().add(newDirNode);
			iteratorDir(exDir.listFiles(), newDirNode,exDir,ltv);
		}
		ltv.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int i = event.getClickCount();
				switch (event.getButton()) {
				case PRIMARY: // ���˫���ڵ���ļ�
					if (i != 2)
						break;
					TreeItem<FileTreeModel> item = ltv.getSelectionModel().getSelectedItem();
					File file = item.getValue().getFile();
					if (!file.isDirectory()) {
						ObservableList<Tab> edittabList = metp.getTabs();
						ObservableList<Tab> consoletabList = mctp.getTabs();
						//�Ѿ��򿪵��ļ�ֱ��ѡ��tab
						for(int j=0;j<edittabList.size();j++) {
							Tab edittab =edittabList.get(j);
							Tab consoletab = consoletabList.get(j);
							if (edittab.getText().equals(file.getName())) {
								metp.getSelectionModel().select(edittab);
								mctp.getSelectionModel().select(consoletab);
								Alert alert = new Alert(AlertType.WARNING);
								Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
								alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
								alert.setTitle("����");
								alert.setHeaderText(null);
								alert.setContentText("�Ѵ��ڴ򿪵�ͬ���ļ������ȹرղ�ȷ�ϱ�����ٴ򿪸��ļ���");
								alert.showAndWait();
								return;
							}
 						}
						Tab edittab =new Tab(file.getName());
						edittab.setId(file.getAbsolutePath()); //���ļ�����·����Ϊid
						edittab.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/file.png"))));
						edittabList.add(edittab);
						Tab consoletab = new Tab(file.getName() + " - console");
						consoletab.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/console.png"))));
						consoletab.setId(file.getAbsolutePath());
						consoletabList.add(consoletab);
						edittab.setOnClosed((CloseEvent) -> {
							consoletabList.remove(consoletab);
						});
						consoletab.setOnClosed((CloseEvent) -> {
							edittabList.remove(edittab);
						});
						metp.getSelectionModel().select(edittab);
						mctp.getSelectionModel().select(consoletab);
						RichEditTextArea eta = new RichEditTextArea();
						ConsoleTextArea cta = new ConsoleTextArea();
						String content = "";
						try {
							
							content = Utils.ReadFiletoString(file, Config.getEncode());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						eta.setText(content,Utils.getTextType(file));
						VirtualizedScrollPane<RichEditTextArea> sp = new VirtualizedScrollPane<RichEditTextArea>(eta);
						edittab.setContent(sp);
						consoletab.setContent(cta);
						Config.setLastOpenFilePath(file.getPath());
					}
					break;
				default:
					break;
				}
			}
		});
		// ���ϴ����򿪵��ļ�
		if (!lastFile.exists()||lastFile.isDirectory()) {
			return;
		}
		ObservableList<Tab> edittabList = metp.getTabs();
		ObservableList<Tab> consoletabList = mctp.getTabs();
		for (Tab tab : edittabList) {
			if (tab.getText().equals(lastFile.getName()))
				return;
		}
		Tab edittab = new Tab(lastFile.getName());
		edittab.setId(lastFile.getAbsolutePath());
		edittab.setGraphic(new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png"))));
		edittabList.add(edittab);
		Tab consoletab = new Tab(lastFile.getName() + " - console");
		consoletab.setGraphic(new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/console.png"))));
		consoletab.setId(lastFile.getAbsolutePath());
		consoletabList.add(consoletab);
		edittab.setOnClosed((CloseEvent) -> {
			consoletabList.remove(consoletab);
		});
		consoletab.setOnClosed((CloseEvent) -> {
			edittabList.remove(edittab);
		});
		metp.getSelectionModel().select(edittab);
		mctp.getSelectionModel().select(consoletab);
		RichEditTextArea eta = new RichEditTextArea();
		ConsoleTextArea cta = new ConsoleTextArea();
		String line = "";
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(lastFile), Config.getEncode()));
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		eta.setText(content,Utils.getTextType(lastFile));
		VirtualizedScrollPane<RichEditTextArea> sp = new VirtualizedScrollPane<RichEditTextArea>(eta);
		edittab.setContent(sp);
		consoletab.setContent(cta);
	}

	// �����ļ��е�Tree
	public static void iteratorDir(File[] files, TreeItem<FileTreeModel> node,File lastFile,LeftTreeView ltv) {
		for (File file : files) {
			if (file.isDirectory()) {
				ImageView dirIcon = new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/dir.png")));
				FileTreeModel fileMode = new FileTreeModel(file.getName(), file.getAbsolutePath(), file);
				TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(fileMode, dirIcon);
				node.getChildren().add(newDirNode);
				iteratorDir(file.listFiles(), newDirNode,lastFile,ltv);
			} else {
				ImageView fileIcon = new ImageView(new Image(LeftTreeViewController.class.getClass().getResourceAsStream("/res/file.png")));
				FileTreeModel fileMode = new FileTreeModel(file.getName(), file.getAbsolutePath(), file);
				TreeItem<FileTreeModel> newFileNode = new TreeItem<FileTreeModel>(fileMode, fileIcon);
				node.getChildren().add(newFileNode);
				if(file.getAbsolutePath().equals(lastFile.getAbsolutePath())) { //������ϴδ򿪵��ļ����ļ���ѡ�иýڵ�
					ltv.getSelectionModel().select(newFileNode);
				}
			}
		}
	}
}
