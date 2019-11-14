package nt.com.view.init;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.config.Config;

public class MultiJsonView extends VBox{
	
	public MultiJsonView() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/nt/com/view/fxml/MultiJsonView.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("≤¢∑¢…Ë÷√ - JSON");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		Label concurrency = (Label) this.lookup("#concurrency");
		concurrency.setText(Config.getJSONConcurrency()+"");
		Slider multicount =(Slider) this.lookup("#multicount");
		multicount.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int value =newValue.intValue();
				concurrency.setText(value+"");
			}
		});
		
	}
}

