package nt.com.buiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.fxmisc.flowless.VirtualizedScrollPane;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.config.TemplateFile;
import nt.com.enmu.FileType;
import nt.com.enmu.TextType;
import nt.com.model.FileTreeModel;
import nt.com.model.NewFileListModel;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.LeftTreeView;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;

/**
 * 新建文件事件控制器
 * @author kege
 *
 */
public class NewFileViewController {
	
	@FXML
	private ListView<NewFileListModel> filelist;
	
	@FXML
	private TextField filefield;
	
	@FXML
	private VBox newfileview;
	
	/**
	 * 提交创建新文件
	 * @throws IOException 
	 */
	@FXML
	void submit(ActionEvent event) throws IOException {
		NewFileListModel nfm = filelist.getSelectionModel().getSelectedItem();
		LeftTreeView ltv = (LeftTreeView) MainView.parent.lookup("#packagetree");
		TreeItem<FileTreeModel> item =ltv.getSelectionModel().getSelectedItem();
		String newFileName=filefield.getText();
		if(newFileName.trim().equals("") ||nfm==null) {
			Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			alert.setTitle("提醒");
			alert.setHeaderText(null);
			alert.setContentText("请输入文件名和选择文件类型！");
			alert.showAndWait();
			return;
		}
		ImageView icon=null;
		FileType filetype=nfm.getFileType();
		//判断新建文件类型
		if(filetype==FileType.DIR) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/dir.png")));
		}else if(filetype==FileType.TXT) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
		}else if(filetype==FileType.JAVASCRIPT) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
		}else if(filetype==FileType.XML) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
		}else if(filetype==FileType.JSON) {
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/file.png")));
		}
		if(item==null) {  //右键没选中treeitem就默认为从根节点新建
			if(filetype!=FileType.DIR) {  //根节点不支持创建文件，只建文件夹
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("根目录只能创建文件夹！");
				alert.showAndWait();
				return;
			}
			TreeItem<FileTreeModel> root =ltv.getRoot();
			File newfile = new File("workspace/"+newFileName);
			newfile.mkdir();
			icon= new ImageView(new Image(LeftTreeViewController.class.getResourceAsStream("/res/import.gif")));
			FileTreeModel newfileMod = new FileTreeModel(newfile.getName(), newfile.getAbsolutePath(), newfile);
			TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(newfileMod, icon);
			root.getChildren().add(newDirNode);
		}else { 
			File file=item.getValue().getFile();
			File newfile=null;
			Writer write=null;
			String content = null;
			if(file.isDirectory()) {  //如果是文件夹 则在该文件夹下创建
				if(filetype==FileType.DIR) {
					newfile = new File(file.getAbsolutePath()+File.separator+newFileName);
					newfile.mkdir();
				}else if(filetype==FileType.TXT) {
					newfile = new File(file.getAbsolutePath()+File.separator+newFileName+".txt");
					newfile.createNewFile();
				}else if(filetype==FileType.JAVASCRIPT) {
					newfile = new File(file.getAbsolutePath()+File.separator+newFileName+".js");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsTemplate());
				}else if(filetype==FileType.XML) {
					newfile = new File(file.getAbsolutePath()+File.separator+newFileName+".xml");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.XmlTemplate());
				}else if(filetype==FileType.JSON) {
					newfile = new File(file.getAbsolutePath()+File.separator+newFileName+".json");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsonTemplate());
				}
				if(write!=null) {
					write.flush();
					write.close();
				}
				FileTreeModel newfileMod = new FileTreeModel(newfile.getName(), newfile.getAbsolutePath(), newfile);
				TreeItem<FileTreeModel> newDirNode = new TreeItem<FileTreeModel>(newfileMod, icon);
				item.getChildren().add(newDirNode);
				ltv.getSelectionModel().select(newDirNode);
			}else {  //如果是文件，则在该文件同级创建
				if(filetype==FileType.DIR) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+newFileName);
					newfile.mkdir();
				}else if(filetype==FileType.TXT) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+newFileName+".txt");
					newfile.createNewFile();
				}else if(filetype==FileType.JAVASCRIPT) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+newFileName+".js");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsTemplate());
				}else if(filetype==FileType.XML) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+newFileName+".xml");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.XmlTemplate());
				}else if(filetype==FileType.JSON) {
					newfile = new File(file.getParentFile().getAbsolutePath()+File.separator+newFileName+".json");
					newfile.createNewFile();
					write = new OutputStreamWriter(new FileOutputStream(newfile));
					write.write(content=TemplateFile.JsonTemplate());
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
			//如果是新建文件则打开文件
			if(filetype==FileType.DIR) {
				return;
			}
			TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
			TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
			ObservableList<Tab> edittabList = metp.getTabs();
			ObservableList<Tab> consoletabList = mctp.getTabs();
			//已经打开的文件直接选中tab
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
			 if(filetype==FileType.JAVASCRIPT) {
				 eta.setText(content,TextType.JAVASCRIPT);
			}else if(filetype==FileType.XML) {
				 eta.setText(content,TextType.XML);
			}else if(filetype==FileType.JSON) {
				eta.setText(content,TextType.JSON);
			}else if(filetype==FileType.TXT) {
				eta.setText(content,TextType.TXT);
			}else {
				eta.setText(content,TextType.UNKNOW);
			}
			 VirtualizedScrollPane<RichEditTextArea> sp = new VirtualizedScrollPane<RichEditTextArea>(eta);
			edittab.setContent(sp);
			consoletab.setContent(cta);
			Config.setLastOpenFilePath(newfile.getPath());
			Stage stage =(Stage) newfileview.getScene().getWindow();
			stage.close();
		}
	}
	
	@FXML
	void cancel(ActionEvent event) {
		Stage stage =(Stage) newfileview.getScene().getWindow();
		stage.close();
	}
}

