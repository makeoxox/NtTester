package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class HttpMsgSettingViewController {

    @FXML
    private VBox httpmsgsettingview;

    @FXML
    private TextField urlfield;

    @FXML
    private RadioButton postradio;

    @FXML
    private ToggleGroup requesttypegroup;

    @FXML
    private RadioButton getradio;
    
    @FXML
    private ChoiceBox<String> contenttypechoice;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button okbtn;
    
    @FXML
    void requestchoose(ActionEvent event) {
    	RadioButton request =(RadioButton) requesttypegroup.getSelectedToggle();
    	if(request==getradio) {
    		contenttypechoice.getSelectionModel().select(3);
    		contenttypechoice.setDisable(true);
    	}else {
    		contenttypechoice.setDisable(false);
    	}
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void ok(ActionEvent event) {

    }

}
