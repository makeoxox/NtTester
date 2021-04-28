package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.global.Config;

/**
 * HTTP报文设置控制器
 * 
 * @author kege
 *
 */
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
    private Label asynlabel;

    @FXML
    private Label svrportlabel;

    @FXML
    private Label svrrootlabel;

    @FXML
    private TextField svrportfield;

    @FXML
    private TextField svrrootfield;

    @FXML
    private Label svrtimeoutlabel;

    @FXML
    private TextField svrtimeoutfield;

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
    	((Stage) httpmsgsettingview.getScene().getWindow()).close();
    }

    @FXML
    void ok(ActionEvent event) {
    	String url = urlfield.getText().trim();
    	Config.setURL(url);
    	RadioButton request =(RadioButton) requesttypegroup.getSelectedToggle();
    	if(request==getradio) {
    		Config.setHttpRequestType("get");
    	}else {
    		Config.setHttpRequestType("post");
    	}
    	int selected = contenttypechoice.getSelectionModel().getSelectedIndex();
    	switch(selected) {
	    	case 0 :
	    		Config.setHttpContentType("text/plain");
	    		break;
	    	case 1 :
	    		Config.setHttpContentType("application/json");
	    		break;
	    	case 2 :
	    		Config.setHttpContentType("text/xml");
	    		break;
	    	case 3 :
	    		Config.setHttpContentType("application/x-www-form-urlencoding");
	    		break;
    	}
    	int svrport = Integer.parseInt(svrportfield.getText().trim());
		Config.setHttpSvrPort(svrport);
		int svrtimeout = Integer.parseInt(svrtimeoutfield.getText().trim());
		Config.setHttpSvrTimeout(svrtimeout);
		String svrroot = svrrootfield.getText().trim();
		Config.setContextRoot(svrroot);
		((Stage) httpmsgsettingview.getScene().getWindow()).close();
    }

}
