package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class MultiJsonViewController {
	
    @FXML
	private VBox multijsonview;
	
	@FXML
	private Slider multicount;
	
	@FXML
    private Label concurrency;

    @FXML
    private CheckBox incrementenable;

    @FXML
    private TextField incrementjsonfield;

    @FXML
    private Button incrementjsonbtn;

    @FXML
    private TreeView<?> incrementjsontree;

    @FXML
    private ListView<?> incrementjsonlist;

    @FXML
    private Button incrementjsonadd;

    @FXML
    private Button incrementjsondel;

    @FXML
    private CheckBox assignenable;

    @FXML
    private TextField assignvaluesfield;

    @FXML
    private Button assignvaluesbtn;

    @FXML
    private Label assignjsonlabel;

    @FXML
    private TextField assignjsonfield;

    @FXML
    private Button assignjsonbtn;

    @FXML
    private TreeView<?> assignjsontree;

    @FXML
    private ListView<?> assignjsonlist;

    @FXML
    private Button assignjsonadd;

    @FXML
    private Button assignjsondel;

    @FXML
    private Label incrementjsonlabel;

    @FXML
    private Label assignvalueslabel;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button okbtn;

    @FXML
    void assign(ActionEvent event) {
    	if(assignenable.isSelected()) {
    		assignjsonadd.setDisable(false);
    		assignjsonbtn.setDisable(false);
    		assignjsondel.setDisable(false);
    		assignjsonfield.setDisable(false);
    		assignjsonlabel.setDisable(false);
    		assignjsonlist.setDisable(false);
    		assignjsontree.setDisable(false);
    		assignvaluesbtn.setDisable(false);
    		assignvaluesfield.setDisable(false);
    		assignvalueslabel.setDisable(false);
    	}else {
    		assignjsonadd.setDisable(true);
    		assignjsonbtn.setDisable(true);
    		assignjsondel.setDisable(true);
    		assignjsonfield.setDisable(true);
    		assignjsonlabel.setDisable(true);
    		assignjsonlist.setDisable(true);
    		assignjsontree.setDisable(true);
    		assignvaluesbtn.setDisable(true);
    		assignvaluesfield.setDisable(true);
    		assignvalueslabel.setDisable(true);
    	}
    }

   

    @FXML
    void increment(ActionEvent event) {
    	if(incrementenable.isSelected()) {
    		incrementjsonadd.setDisable(false);
    		incrementjsonbtn.setDisable(false);
    		incrementjsondel.setDisable(false);
    		incrementjsonfield.setDisable(false);
    		incrementjsonlabel.setDisable(false);
    		incrementjsonlist.setDisable(false);
    		incrementjsontree.setDisable(false);
    	}else {
    		incrementjsonadd.setDisable(true);
    		incrementjsonbtn.setDisable(true);
    		incrementjsondel.setDisable(true);
    		incrementjsonfield.setDisable(true);
    		incrementjsonlabel.setDisable(true);
    		incrementjsonlist.setDisable(true);
    		incrementjsontree.setDisable(true);
    	}
    }

    @FXML
    void ok(ActionEvent event) {

    }
    
    @FXML
    void cancel(ActionEvent event) {

    }

}
