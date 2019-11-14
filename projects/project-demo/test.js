package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

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
	    private TreeView<?> incrementeletree;

	    @FXML
	    private ListView<?> incrementelelist;

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
	    private TreeView<?> assigneletree;

	    @FXML
	    private ListView<?> assignelelist;

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
	    
	    @FXML
	    void cancel(ActionEvent event) {

	    }

	    @FXML
	    void ok(ActionEvent event) {

	    }
	
}
