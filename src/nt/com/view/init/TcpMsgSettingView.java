package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.config.Config;
/**
 * TCP报文设置
 * 
 * @author kege
 *
 */
public class TcpMsgSettingView extends VBox{
	public TcpMsgSettingView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/TcpMsgSettingView.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("报文设置 - TCP");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		String asyn = Config.getConnect();
		TextField svrportfield = (TextField) this.lookup("#svrportfield");
		 svrportfield.setText(Config.getTcpSvrPort()+"");
		 TextField svrtimeoutfield = (TextField) this.lookup("#svrtimeoutfield");
		 svrtimeoutfield.setText(Config.getTcpSvrTimeout()+"");
		if(asyn.toLowerCase().equals("asyn")) {
			 ((Label) this.lookup("#asynlabel")).setDisable(false);
			 ((Label) this.lookup("#svrportlabel")).setDisable(false);
			 ((Label) this.lookup("#svrtimeoutlabel")).setDisable(false);
			 svrportfield.setDisable(false);
			 svrtimeoutfield.setDisable(false);
		}
		String lengthtype=Config.getLengthType().toLowerCase();
		if(lengthtype.equals("byte")) {
			RadioButton byteradio =(RadioButton) this.lookup("#byteradio");
			byteradio.setSelected(true);
		}else if(lengthtype.equals("char")) {
			RadioButton charradio =(RadioButton) this.lookup("#charradio");
			charradio.setSelected(true);
		}
		String fixtype=Config.getFixType().toLowerCase();
		if(fixtype.equals("left")) {
			RadioButton leftradio =(RadioButton) this.lookup("#leftradio");
			leftradio.setSelected(true);
		}else if(fixtype.equals("right")) {
			RadioButton rightradio =(RadioButton) this.lookup("#rightradio");
			rightradio.setSelected(true);
		}
		TextField ipfield = (TextField) this.lookup("#ipfield");
		ipfield.setText(Config.getIP());
		TextField portfield = (TextField) this.lookup("#portfield");
		portfield.setText(Config.getPort()+"");
		TextField fixcharfield = (TextField) this.lookup("#fixcharfield");
		fixcharfield.setText(Config.getFixChar());
		TextField fixlengthfield = (TextField) this.lookup("#fixlengthfield");
		fixlengthfield.setText(Config.getFixLength()+"");
		TextField offsetfield = (TextField) this.lookup("#offsetfield");
		offsetfield.setText(Config.getOffset()+"");
		
	}

}
