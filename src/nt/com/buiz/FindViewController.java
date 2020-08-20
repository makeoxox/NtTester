package nt.com.buiz;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nt.com.global.Config;
import nt.com.global.GlobalData;
import nt.com.model.FileTreeModel;
import nt.com.model.RangeListModel;
import nt.com.util.Str;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;

/**
 * 查找文本控制器
 *
 * @author kege
 *
 */
public class FindViewController {
	
	@FXML
    private TextField findfield;
	
	@FXML
	private ListView<FileTreeModel> findlist;
	
	@FXML
    private Button findsinglebtn;

    @FXML
    private Button findallbtn;
	
    //查找下一个
	@SuppressWarnings("unchecked")
	@FXML
    void findone() {
		Alert alert = new Alert(AlertType.WARNING);
		Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		String keyword = findfield.getText().trim();
		if(keyword==null || keyword.equals("")) {
			alert.setTitle("提醒");
			alert.setHeaderText(null);
			alert.setContentText("关键字不能为空！");
			alert.showAndWait();
			return;
		}
		Config.setKeyword(keyword);
		//当前编辑中的文档
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null)return;
		VirtualizedScrollPane<RichEditTextArea> sp = (VirtualizedScrollPane<RichEditTextArea>)currentEditTab.getContent();
		RichEditTextArea eta=(RichEditTextArea)sp.getContent();
		//判断缓存
		if( GlobalData.currRLM==null || !keyword.equals(GlobalData.currRLM.getKeyWord()) || !currentEditTab.getId().equals(GlobalData.currRLM.getFileName())) {
			String content = eta.getText();
			List<Integer> res = Str.kmp(content.toUpperCase(), keyword.toUpperCase());
			if(res.size()==0) {
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("没有查到相关关键字！");
				alert.showAndWait();
				return;
			}
			RangeListModel rlm = new RangeListModel();
			rlm.setFileName(currentEditTab.getId());
			rlm.setCurrentIndex(0);
			rlm.setKeyWord(keyword);
			rlm.setRangeList(res);
			GlobalData.currRLM = rlm;
		}
		int currentIndex = GlobalData.currRLM.getCurrentIndex();
		List<Integer> rangelist = GlobalData.currRLM.getRangeList();
		if(rangelist.size()==currentIndex) { 
				currentIndex=0;
		}
		int range = GlobalData.currRLM.getRangeList().get(currentIndex);
		eta.selectRange(range, range+keyword.length());
		eta.requestFollowCaret(); //跟随光标移动滚动条
		GlobalData.currRLM.setCurrentIndex(currentIndex+1);
    }
	
	//查找全部文件
    @FXML
    void findall() {
    	try {
	    	Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			String keyword = findfield.getText().trim();
			if(keyword==null || keyword.equals("")) {
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("关键字不能为空！");
				alert.showAndWait();
				return;
			}
			findlist.getItems().clear();
			GlobalData.rangeKeyList.clear();
			Config.setKeyword(keyword);
			iteratorDir(new File("projects").listFiles(),keyword);
		} catch (IOException e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
		}
    }
    
    //递归文件夹
    private  void iteratorDir(File[] files,String keyword) throws IOException {
		for (File file : files) {
			if (file.isDirectory()) {
				iteratorDir(file.listFiles(),keyword); 
			} else {
				String content = Utils.ReadFiletoString(file, Config.getEncode());
				List<Integer> rangeList = Str.kmp(content,keyword);
				if(rangeList.size()==0) {
					continue;
				}else {
					RangeListModel rlm = new RangeListModel();
					rlm.setFileName(file.getAbsolutePath());
					rlm.setKeyWord(keyword);
					rlm.setRangeList(rangeList);
					rlm.setCurrentIndex(0);
					String absPath = file.getAbsolutePath();
					Map<String,RangeListModel> map = new HashMap<>();
					map.put(absPath,rlm);
					GlobalData.rangeKeyList.add(map);
					int i = absPath.indexOf("projects");
					FileTreeModel ftm = new FileTreeModel(absPath.substring(i+9), absPath, file);
					findlist.getItems().add(ftm);
				}
			}
		}
	}
    
    //双击文件路径打开文件
    @FXML
    void openFileAction(Event event) {
    	if(event instanceof KeyEvent) {
    		if(((KeyEvent) event).getCode()==KeyCode.ENTER) {
    			openFile();
    		}
    	}else if(event instanceof MouseEvent) {
    		int i = ((MouseEvent) event).getClickCount();
			switch (((MouseEvent) event).getButton()) {
			case PRIMARY: // 左键双击节点打开文件
				if (i != 2)
					return;
				openFile();
			default:
				break;
			}
    	}
    }
    
    //打开文件夹
    private void openFile() {
    	FileTreeModel flm = findlist.getSelectionModel().getSelectedItem();
    	TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
    	ObservableList<Tab> edittabList = metp.getTabs();
		ObservableList<Tab> consoletabList = mctp.getTabs();
		//已经打开的文件直接选中tab
		for(int j=0;j<edittabList.size();j++) {
			Tab edittab =edittabList.get(j);
			Tab consoletab = consoletabList.get(j);
			if (edittab.getId().equals(flm.getFile().getAbsolutePath())) {
				metp.getSelectionModel().select(edittab);
				mctp.getSelectionModel().select(consoletab);
				for(Iterator<Map<String,RangeListModel>> it = GlobalData.rangeKeyList.iterator();it.hasNext();) {
					Map<String,RangeListModel> map = it.next();
					if(map.containsKey(edittab.getId())) {
						GlobalData.currRLM = map.get(edittab.getId());
					}
				}
				return;
			}else if(edittab.getText().equals(flm.getFile().getName())) {
				metp.getSelectionModel().select(edittab);
				mctp.getSelectionModel().select(consoletab);
				Alert alert = new Alert(AlertType.WARNING);
				Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
				alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("已存在打开的同名文件，请先关闭同名文件！");
				alert.showAndWait();
				return;
			}
		}
		Tab edittab =new Tab(flm.getFile().getName());
		edittab.setId(flm.getFile().getAbsolutePath()); //把文件绝对路径作为id
		edittab.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/file.png"))));
		edittabList.add(edittab);
		Tab consoletab = new Tab(flm.getFile().getName() + " - console");
		consoletab.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/console.png"))));
		consoletab.setId(flm.getFile().getAbsolutePath());
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
			content = Utils.ReadFiletoString(flm.getFile(), Config.getEncode());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		eta.setText(content,Utils.getTextType(flm.getFile()));
		VirtualizedScrollPane<RichEditTextArea> sp = new VirtualizedScrollPane<RichEditTextArea>(eta);
		edittab.setContent(sp);
		consoletab.setContent(cta);
		for(Iterator<Map<String,RangeListModel>> it = GlobalData.rangeKeyList.iterator();it.hasNext();) {
			Map<String,RangeListModel> map = it.next();
			if(map.containsKey(edittab.getId())) {
				GlobalData.currRLM = map.get(edittab.getId());
			}
		}
		Config.setLastOpenFilePath(flm.getFile().getPath());
    }
    
}
