package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultiXMLView extends VBox{
	
	public MultiXMLView() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/nt/com/view/fxml/MultiXMLView.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("≤¢∑¢…Ë÷√ - XML");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		
		
	}
}

