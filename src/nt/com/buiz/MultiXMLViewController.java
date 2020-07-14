package nt.com.buiz;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import nt.com.model.XmlElementModel;
import nt.com.util.XmlParser;
import nt.com.view.init.ConsoleTextArea;
import nt.com.view.init.MainView;

public class MultiXMLViewController {
	
	 	@FXML
	    private VBox multixmlview;

	    @FXML
	    private Slider multicount;
	    
	    @FXML
	    private Label concurrency;

	    @FXML
	    private CheckBox incrementenable;

	    @FXML
	    private RadioButton incrementtextbtn;

	    @FXML
	    private ToggleGroup incrementgroup;

	    @FXML
	    private RadioButton incrementattrbtn;

	    @FXML
	    private Label incrementxmllabel;

	    @FXML
	    private TextField incrementxmlfield;

	    @FXML
	    private Button incrementxmlbtn;

	    @FXML
	    private Label incrementattrlabel;

	    @FXML
	    private TextField incrementattrfield;

	    @FXML
	    private TreeView<XmlElementModel> incrementeletree;

	    @FXML
	    private ListView<XmlElementModel> incrementelelist;

	    @FXML
	    private Button incrementadd;

	    @FXML
	    private Button incrementdel;

	    @FXML
	    private CheckBox assignenable;

	    @FXML
	    private RadioButton assigntextbtn;

	    @FXML
	    private ToggleGroup assigngroup;

	    @FXML
	    private RadioButton assignattrbtn;

	    @FXML
	    private Label assignvalueslabel;

	    @FXML
	    private TextField assignvaluesfield;

	    @FXML
	    private Button assignvaluesbtn;

	    @FXML
	    private Label assignxmllabel;

	    @FXML
	    private TextField assignxmlfield;

	    @FXML
	    private Button assignxmlbtn;

	    @FXML
	    private Label assignattrlabel;

	    @FXML
	    private TextField assignattrfield;

	    @FXML
	    private TreeView<XmlElementModel> assigneletree;

	    @FXML
	    private ListView<XmlElementModel> assignelelist;

	    @FXML
	    private Button assignadd;

	    @FXML
	    private Button assigndel;

	    @FXML
	    private Button cancelbtn;

	    @FXML
	    private Button okbtn;
	    
	   
	    
	    @FXML
	    void assign(ActionEvent event) {
	    	if(assignenable.isSelected()) {
	    		assignadd.setDisable(false);
	    		assignattrbtn.setDisable(false);
	    		assigndel.setDisable(false);
	    		assignelelist.setDisable(false);
	    		assigneletree.setDisable(false);
	    		assigntextbtn.setDisable(false);
	    		assignvaluesbtn.setDisable(false);
	    		assignvaluesfield.setDisable(false);
	    		assignvalueslabel.setDisable(false);
	    		assignxmlbtn.setDisable(false);
	    		assignxmlfield.setDisable(false);
	    		assignxmllabel.setDisable(false);
	    		if(assignattrbtn.isSelected()) {
	    			assignattrfield.setDisable(false);
	    			assignattrlabel.setDisable(false);
	    		}
	    	}else {
	    		assignadd.setDisable(true);
	    		assignattrbtn.setDisable(true);
	    		assigndel.setDisable(true);
	    		assignelelist.setDisable(true);
	    		assigneletree.setDisable(true);
	    		assigntextbtn.setDisable(true);
	    		assignvaluesbtn.setDisable(true);
	    		assignvaluesfield.setDisable(true);
	    		assignvalueslabel.setDisable(true);
	    		assignxmlbtn.setDisable(true);
	    		assignxmlfield.setDisable(true);
	    		assignxmllabel.setDisable(true);
	    		assignattrfield.setDisable(true);
	    		assignattrlabel.setDisable(true);
	    	}
	    }
	    //选中属性赋值
	    @FXML
	    void assignattrselect(ActionEvent event) {
	    	assignattrfield.setDisable(false);
			assignattrlabel.setDisable(false);
	    }
	    //选中文本赋值
	    @FXML
	    void assigntextselect(ActionEvent event) {
	    	assignattrfield.setDisable(true);
			assignattrlabel.setDisable(true);
	    }

	    @FXML
	    void increment(ActionEvent event) {
	    	if(incrementenable.isSelected()) {
	    		incrementadd.setDisable(false);
	    		incrementattrbtn.setDisable(false);
	    		incrementdel.setDisable(false);
	    		incrementelelist.setDisable(false);
	    		incrementeletree.setDisable(false);
	    		incrementtextbtn.setDisable(false);
	    		incrementxmlbtn.setDisable(false);
	    		incrementxmlfield.setDisable(false);
	    		incrementxmllabel.setDisable(false);
	    		if(incrementattrbtn.isSelected()) {
	    			incrementattrfield.setDisable(false);
		    		incrementattrlabel.setDisable(false);
	    		}
	    	}else {
	    		incrementadd.setDisable(true);
	    		incrementattrbtn.setDisable(true);
	    		incrementdel.setDisable(true);
	    		incrementelelist.setDisable(true);
	    		incrementeletree.setDisable(true);
	    		incrementtextbtn.setDisable(true);
	    		incrementxmlbtn.setDisable(true);
	    		incrementxmlfield.setDisable(true);
	    		incrementxmllabel.setDisable(true);
	    		incrementattrfield.setDisable(true);
	    		incrementattrlabel.setDisable(true);
	    		
	    	}
	    }
	    //选中属性递增
	    @FXML
	    void incrementattrselect(ActionEvent event) {
    		incrementattrfield.setDisable(false);
	    	incrementattrlabel.setDisable(false);
	    }
	    //选中文本递增
	    @FXML
	    void incrementtextselect(ActionEvent event) {
	    	incrementattrfield.setDisable(true);
	    	incrementattrlabel.setDisable(true);
	    }
	    
	    //解析xml树,并显示在TreeView上
	    @FXML
	    void parseXML(ActionEvent event) {
	    	try {
	    		Node node = (Node) event.getSource();
	    		TreeView<XmlElementModel> tv =null;
	    		String path = null;
	    		if(node==incrementxmlbtn) {
	    			tv = incrementeletree;
	    			path =incrementxmlfield.getText();
	    		}else if(node == assignxmlbtn) {
	    			tv = assigneletree;
	    			path =assignxmlfield.getText();
	    		}
	    		
				Document doc = XmlParser.getDocByAbsolutePath(path);
				Element root =doc.getRootElement();
				XmlElementModel  rootModel =  new XmlElementModel();
				rootModel.setElement(root);
				TreeItem<XmlElementModel> elementItem = new TreeItem<XmlElementModel> (rootModel);
				elementItem.setExpanded(true);
				tv.setRoot(elementItem);
				iteratorXML(root,elementItem);
			} catch (DocumentException e) {
				ConsoleTextArea.AppendMessageOnCurrentConsole(e.toString());
			}
	    }
	    
	    // 绘制XML文档的元素树
	    public static void iteratorXML(Element currentEle, TreeItem<XmlElementModel> node) {
	    	for(Iterator<Element> it  = currentEle.elementIterator();it.hasNext();) {
	    		Element ele = it.next();
	    		XmlElementModel  eleModel =  new XmlElementModel();
	    		eleModel.setElement(ele);
	    		TreeItem<XmlElementModel> elementItem = new TreeItem<XmlElementModel> (eleModel);
	    		node.getChildren().add(elementItem);
	    		iteratorXML(ele,elementItem);
	    	}	
		}
	    
	    //增加元素
	    @FXML
	    void addElement(ActionEvent event) {
	    	Node node = (Node) event.getSource();
	    	ListView<XmlElementModel> lv =null;
	    	TreeView<XmlElementModel> tv =null;
    		if(node==incrementadd) {
    			lv = incrementelelist;
    			tv = incrementeletree;
    		}else if(node == assignadd) {
    			lv = assignelelist;
    			tv = assigneletree;
    		}
    		XmlElementModel elementModel = tv.getSelectionModel().getSelectedItem().getValue();
    		ObservableList<XmlElementModel> list = lv.getItems();
    		if(list.size() ==0 || !list.contains(elementModel)) {
    			list.add(elementModel);
    		}
	    }
	    
	    //删除元素
	    @FXML
	    void delElement(ActionEvent event) {
	    	Node node = (Node) event.getSource();
	    	ListView<XmlElementModel> lv =null;
    		if(node==incrementdel) {
    			lv = incrementelelist;
    		}else if(node == assigndel) {
    			lv = assignelelist;
    		}
    		lv.getItems().remove(lv.getSelectionModel().getSelectedItem());
	    }
	    
	    @FXML
	    void selectValuesFile(ActionEvent event) {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setInitialDirectory(new File("config"));
			File file = fileChooser.showOpenDialog(MainView.scene.getWindow());
			if(file==null)return;
			if(file.getParentFile().getName().equals("config")) {
				assignvaluesfield.setText("config"+File.separator+file.getName());
			}else {
				assignvaluesfield.setText(file.getAbsolutePath());
			}
	    }
	    
	    @FXML
	    void cancel(ActionEvent event) {

	    }

	    @FXML
	    void ok(ActionEvent event) {

	    }
	
}
