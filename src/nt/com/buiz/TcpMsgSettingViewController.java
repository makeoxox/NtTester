package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.global.Config;

/**
 * Tcp报文设置框控制器
 * 
 * @author kege
 *
 */
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
    private Label asynlabel;

    @FXML
    private Label svrportlabel;

    @FXML
    private Label svrtimeoutlabel;
    
    @FXML
    private TextField svrtimeoutfield;
    
    @FXML
    private TextField svrportfield;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button okbtn;
    

    
    
    @FXML
    void cancel(ActionEvent event) {
    	((Stage) tcpmsgsettingview.getScene().getWindow()).close();
    }

    @FXML
    void ok(ActionEvent event) {
    	RadioButton typebtn =(RadioButton) lengthtypegroup.getSelectedToggle();
    	if(typebtn==byteradio) {
    		Config.setLengthType("byte");
    	}else {
    		Config.setLengthType("char");
    	}
    	RadioButton fixtypebtn =(RadioButton) fixtypegroup.getSelectedToggle();
    	if(fixtypebtn==leftradio) {
    		Config.setFixType("left");
    	}else {
    		Config.setFixType("right");
    	}
    	String ip = ipfield.getText().trim();
    	if(ip!=null) {
    		Config.setIP(ip);
    	}
    	int port =  Integer.parseInt(portfield.getText().trim());
    		Config.setPort(port);
    	String fixchar = fixcharfield.getText().trim();
    		Config.setFixChar(fixchar);
    	int fixlen =  Integer.parseInt(fixlengthfield.getText().trim());
    		Config.setFixLength(fixlen);
    	int offset = Integer.parseInt(offsetfield.getText().trim());
    		Config.setOffset(offset);
    	int svrport = Integer.parseInt(svrportfield.getText().trim());
    		Config.setTcpSvrPort(svrport);
    	int svrtimeout = Integer.parseInt(svrtimeoutfield.getText().trim());
    		Config.setTcpSvrTimeout(svrtimeout);	
    		((Stage) tcpmsgsettingview.getScene().getWindow()).close();
    }

}
