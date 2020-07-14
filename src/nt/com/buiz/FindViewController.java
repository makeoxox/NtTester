package nt.com.buiz;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nt.com.global.Config;
import nt.com.global.GlobalData;
import nt.com.model.FileTreeModel;
import nt.com.model.RangeListModel;
import nt.com.util.Utils;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.LeftTreeView;
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
    void findone(ActionEvent event) {
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
		if( GlobalData.currRLM==null || !keyword.equals(GlobalData.currRLM.getKeyWord())) {
			String content = eta.getText();
			List<Integer> res = kmp(content.toUpperCase(), keyword.toUpperCase());
			if(res.size()==0) {
				alert.setTitle("提醒");
				alert.setHeaderText(null);
				alert.setContentText("没有查到相关关键字！");
				alert.showAndWait();
				return;
			}
			RangeListModel rlm = new RangeListModel();
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
		GlobalData.currRLM.setCurrentIndex(currentIndex+1);
    }
	
	//kmp算法匹配子串坐标
	private List<Integer> kmp(String s, String p){
		List<Integer> res = new ArrayList<Integer>();
		if(s == null || p == null || s.length()<p.length())
			return res;
		char[] sArr = s.toCharArray();
		char[] pArr = p.toCharArray();
		int[] next = getNext(p);
		int i=0, j=0;
		while(i<sArr.length){
			while(j<pArr.length){
				//加判断防止i溢出
				if(i>=sArr.length)
					break;
				//j为-1或匹配，则两数组往后遍历
				if(j==-1 || sArr[i]==pArr[j]){
					i++;
					j++;
				}else{
					//匹配失败，在next数组中找到应该移动到的位置
					j = next[j];
				}
			}
			if(j==pArr.length){
				res.add(i-j);
				j=0;
			}
		}
		return res;
	}
	
	//根据模式串计算next数组, next数组记录如果当前j不匹配应该跳到哪个位置
	private  int[] getNext(String p){
		char[] pArr = p.toCharArray();
		int[] next = new int[pArr.length];
		//如果第一个都不匹配，标记为-1
		next[0] = -1;
		//k表示j不匹配时的下标值
		int j=0;
		int k=-1;
		while(j<pArr.length-1){
			if(k==-1 || pArr[j]==pArr[k]){
				next[++j] = ++k;
			}else{
				k = next[k];
			}
		}
		return next;
	}

	 
	//查找全部文件
    @FXML
    void findall(ActionEvent event) {
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
			Config.setKeyword(keyword);
			iteratorDir(new File("projects").listFiles(),keyword);
		} catch (IOException e) {
			ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
		}
    }
    
    //迭代文件夹
    public  void iteratorDir(File[] files,String keyword) throws IOException {
		for (File file : files) {
			if (file.isDirectory()) {
				iteratorDir(file.listFiles(),keyword); 
			} else {
				String content = Utils.ReadFiletoString(file, Config.getEncode());
				List<Integer> rangeList = kmp(content,keyword);
				if(rangeList.size()==0) {
					return;
				}else {
					RangeListModel rlm = new RangeListModel();
					rlm.setKeyWord(keyword);
					rlm.setRangeList(rangeList);
					rlm.setCurrentIndex(0);
					String absPath = file.getAbsolutePath();
					Map<String,RangeListModel> map = new HashMap<>();
					map.put(absPath,rlm);
					GlobalData.rangeKeyList.add(map);
					int i = absPath.indexOf("projects");
					FileTreeModel ftm = new FileTreeModel(absPath.substring(i), absPath, file);
					findlist.getItems().add(ftm);
				}
			}
		}
	}
}
