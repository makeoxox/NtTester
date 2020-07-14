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
 * �����ı�������
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
	
    //������һ��
	@SuppressWarnings("unchecked")
	@FXML
    void findone(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		String keyword = findfield.getText().trim();
		if(keyword==null || keyword.equals("")) {
			alert.setTitle("����");
			alert.setHeaderText(null);
			alert.setContentText("�ؼ��ֲ���Ϊ�գ�");
			alert.showAndWait();
			return;
		}
		Config.setKeyword(keyword);
		//��ǰ�༭�е��ĵ�
		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
		Tab currentEditTab =metp.getSelectionModel().getSelectedItem();
		if(currentEditTab==null)return;
		VirtualizedScrollPane<RichEditTextArea> sp = (VirtualizedScrollPane<RichEditTextArea>)currentEditTab.getContent();
		RichEditTextArea eta=(RichEditTextArea)sp.getContent();
		//�жϻ���
		if( GlobalData.currRLM==null || !keyword.equals(GlobalData.currRLM.getKeyWord())) {
			String content = eta.getText();
			List<Integer> res = kmp(content.toUpperCase(), keyword.toUpperCase());
			if(res.size()==0) {
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("û�в鵽��عؼ��֣�");
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
	
	//kmp�㷨ƥ���Ӵ�����
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
				//���жϷ�ֹi���
				if(i>=sArr.length)
					break;
				//jΪ-1��ƥ�䣬���������������
				if(j==-1 || sArr[i]==pArr[j]){
					i++;
					j++;
				}else{
					//ƥ��ʧ�ܣ���next�������ҵ�Ӧ���ƶ�����λ��
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
	
	//����ģʽ������next����, next�����¼�����ǰj��ƥ��Ӧ�������ĸ�λ��
	private  int[] getNext(String p){
		char[] pArr = p.toCharArray();
		int[] next = new int[pArr.length];
		//�����һ������ƥ�䣬���Ϊ-1
		next[0] = -1;
		//k��ʾj��ƥ��ʱ���±�ֵ
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

	 
	//����ȫ���ļ�
    @FXML
    void findall(ActionEvent event) {
    	try {
	    	Alert alert = new Alert(AlertType.WARNING);
			Stage alertStage =(Stage) alert.getDialogPane().getScene().getWindow();
			alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
			String keyword = findfield.getText().trim();
			if(keyword==null || keyword.equals("")) {
				alert.setTitle("����");
				alert.setHeaderText(null);
				alert.setContentText("�ؼ��ֲ���Ϊ�գ�");
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
    
    //�����ļ���
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
