package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.enums.TextType;

public class ScriptDebugDialog extends VBox{
	
	public TextType textType; 
	
	public ScriptDebugDialog(TextType textType) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/ScriptDebugDialog.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		this.textType = textType;
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("ต๗สิ");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
	}

}
