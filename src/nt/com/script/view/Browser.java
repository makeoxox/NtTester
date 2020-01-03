package nt.com.script.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Browser extends BorderPane{
	
	public Browser(String content,String contentType,String title) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/script/view/Browser.fxml"));
		fxmlLoader.setRoot(this);
		try {
			 fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("browser"+title);
		stage.setMaximized(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		WebView wb = (WebView) this.lookup("#webview");
		WebEngine engine = wb.getEngine();
		if(contentType==null) {
			engine.load(content);
		}else {
			engine.loadContent(content,contentType);
		}
	}
}
