package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nt.com.model.TxtFieldModel;


/**
 * 并发文本报文控制器
 * 
 * @author kege
 *
 */
public class MultiTxtViewController {
	
	@FXML
    private VBox multitxtview;

    @FXML
    private Label concurrency;

    @FXML
    private Slider multicount;

    @FXML
    private CheckBox incrementenable;

    @FXML
    private ListView<TxtFieldModel> incrementelelist;

    @FXML
    private Button incrementadd;

    @FXML
    private Button incrementdel;

    @FXML
    private CheckBox assignenable;

    @FXML
    private Label assignvalueslabel;

    @FXML
    private TextField assignvaluesfield;

    @FXML
    private Button assignvaluesbtn;

    @FXML
    private ListView<TxtFieldModel> assignelelist;

    @FXML
    private Button assignadd;

    @FXML
    private Button assigndel;

    @FXML
    private Label incrementoffsetlabel;

    @FXML
    private Label incrementlenlabel;

    @FXML
    private TextField incrementoffsetfield;

    @FXML
    private TextField incrementlenfield;

    @FXML
    private Label assignoffsetlabel;

    @FXML
    private Label assignlenlabel;

    @FXML
    private TextField assignoffsetfield;

    @FXML
    private TextField assignlenfield;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button okbtn;

    @FXML
    void assign(ActionEvent event) {
    	if(assignenable.isSelected()) {
    		assignadd.setDisable(false);
    		assigndel.setDisable(false);
    		assignelelist.setDisable(false);
    		assignvaluesbtn.setDisable(false);
    		assignvaluesfield.setDisable(false);
    		assignvalueslabel.setDisable(false);
    		assignlenfield.setDisable(false);
    		assignlenlabel.setDisable(false);
    		assignoffsetlabel.setDisable(false);
    		assignoffsetfield.setDisable(false);
    		
    	}else {
    		assignadd.setDisable(true);
    		assigndel.setDisable(true);
    		assignelelist.setDisable(true);
    		assignvaluesbtn.setDisable(true);
    		assignvaluesfield.setDisable(true);
    		assignvalueslabel.setDisable(true);
    		assignlenfield.setDisable(true);
    		assignlenlabel.setDisable(true);
    		assignoffsetlabel.setDisable(true);
    		assignoffsetfield.setDisable(true);
    	}

    }
    @FXML
    void increment(ActionEvent event) {
    	if(incrementenable.isSelected()) {
    		incrementadd.setDisable(false);
    		incrementdel.setDisable(false);
    		incrementelelist.setDisable(false);
    		incrementlenfield.setDisable(false);
    		incrementlenlabel.setDisable(false);
    		incrementoffsetlabel.setDisable(false);
    		incrementoffsetfield.setDisable(false);
    	}else {
    		incrementadd.setDisable(true);
    		incrementdel.setDisable(true);
    		incrementelelist.setDisable(true);
    		incrementlenfield.setDisable(true);
    		incrementlenlabel.setDisable(true);
    		incrementoffsetlabel.setDisable(true);
    		incrementoffsetfield.setDisable(true);
    		
    	}
    }

    @FXML
    void ok(ActionEvent event) {

    }
    

    @FXML
    void cancel(ActionEvent event) {

    }

}
