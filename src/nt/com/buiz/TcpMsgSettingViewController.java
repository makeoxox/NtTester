package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class TcpMsgSettingViewController {

    @FXML
    private VBox tcpmsgsettingview;
    
    @FXML
    private RadioButton byteradio;

    @FXML
    private RadioButton charradio;
    
    @FXML
    private ToggleGroup lengthtypegroup;
    
    @FXML
    private RadioButton leftradio;

    @FXML
    private RadioButton rightradio;
    
    @FXML
    private ToggleGroup fixtypegroup;
    
    @FXML
    private TextField ipfield;

    @FXML
    private TextField portfield;

    @FXML
    private TextField fixcharfield;

    @FXML
    private TextField fixlengthfield;

    @FXML
    private TextField offsetfield;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button okbtn;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void ok(ActionEvent event) {

    }

}
