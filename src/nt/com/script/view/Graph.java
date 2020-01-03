package nt.com.script.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.util.Picture;

public class Graph extends VBox{
	
	public Graph(String Base64ImgStr,String title){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/script/view/Graph.fxml"));
		fxmlLoader.setRoot(this);
		try {
			 fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("graph-"+title);
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		ImageView iv = (ImageView) this.lookup("#image"); 
		try {
			iv.setImage(Picture.Base64Img(Base64ImgStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		iv.autosize();
	}
}
