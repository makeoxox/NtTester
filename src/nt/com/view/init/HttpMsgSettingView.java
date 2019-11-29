package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.config.Config;
/**
 * HTTP报文设置
 * 
 * @author kege
 *
 */
public class HttpMsgSettingView extends VBox{
	
	@SuppressWarnings("unchecked")
	public HttpMsgSettingView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/HttpMsgSettingView.fxml"));
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
		stage.setTitle("报文设置 - HTTP");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		String asyn = Config.getConnect();
		TextField svrportfield = (TextField) this.lookup("#svrportfield");
		 svrportfield.setText(Config.getHttpSvrPort()+"");
		 TextField svrtimeoutfield = (TextField) this.lookup("#svrtimeoutfield");
		 svrtimeoutfield.setText(Config.getHttpSvrTimeout()+"");
		 TextField svrrootfield = (TextField) this.lookup("#svrrootfield");
		 svrrootfield.setText(Config.getContextRoot());
		if(asyn.toLowerCase().equals("asyn")) {
			 ((Label) this.lookup("#asynlabel")).setDisable(false);
			 ((Label) this.lookup("#svrportlabel")).setDisable(false);
			 ((Label) this.lookup("#svrtimeoutlabel")).setDisable(false);
			 ((Label) this.lookup("#svrrootlabel")).setDisable(false);
			 svrportfield.setDisable(false);
			 svrtimeoutfield.setDisable(false);
			 svrrootfield.setDisable(false);
		}
		TextField urlfield = (TextField) this.lookup("#urlfield");
		ChoiceBox<String> contenttypechoice = (ChoiceBox<String>) this.lookup("#contenttypechoice");
		urlfield.setText(Config.getURL());
		String requesttype=Config.getHttpRequestType();
		if(requesttype.toLowerCase().equals("get")) {
			RadioButton getradio =(RadioButton) this.lookup("#getradio");
			getradio.setSelected(true);
			contenttypechoice.getSelectionModel().select(3);
		}else if(requesttype.toLowerCase().equals("post")) {
			RadioButton postradio =(RadioButton) this.lookup("#postradio");
			postradio.setSelected(true);
		}
		String contenttype=Config.getHttpContentType();
		if(contenttype.equals("text/plain")) {
			contenttypechoice.getSelectionModel().select(0);
		}else if(contenttype.equals("application/json")){
			contenttypechoice.getSelectionModel().select(1);
		}else if(contenttype.equals("text/xml")) {
			contenttypechoice.getSelectionModel().select(2);
		}else if(contenttype.equals("application/x-www-form-urlencoding")) {
			contenttypechoice.getSelectionModel().select(3);
		}
			
		
	}

}
