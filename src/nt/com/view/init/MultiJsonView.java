package nt.com.view.init;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.json.JSON;
import nt.com.config.Config;
import nt.com.config.GlobalData;
import nt.com.model.JsonAssignModel;
import nt.com.model.JsonIncrementModel;

public class MultiJsonView extends VBox{
	
	@SuppressWarnings("unchecked")
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
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("并发设置 - JSON");
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
		//加载递增配置
		JsonIncrementModel jim = Config.getJsonIncrement();
		CheckBox incrementenable = (CheckBox) this.lookup("#incrementenable");
		Label incrementjsonlabel = (Label) this.lookup("#incrementjsonlabel");
		TextField incrementjsonfield = (TextField) this.lookup("#incrementjsonfield");
		Button incrementjsonbtn = (Button) this.lookup("#incrementjsonbtn");
		TreeView<?> incrementjsontree = (TreeView<?>) this.lookup("#incrementjsontree");
		ListView<JSON> incrementjsonlist = (ListView<JSON>) this.lookup("#incrementjsonlist");
		Button incrementjsonadd = (Button) this.lookup("#incrementjsonadd");
		Button incrementjsondel = (Button) this.lookup("#incrementjsondel");
		if (!jim.isDisable()) {
			incrementenable.setSelected(true);
			incrementjsonadd.setDisable(false);
			incrementjsondel.setDisable(false);
			incrementjsonlabel.setDisable(false);
			incrementjsonfield.setDisable(false);
			incrementjsonbtn.setDisable(false);
			incrementjsontree.setDisable(false);
			incrementjsonlist.setDisable(false);
		}
		incrementjsonfield.setText(jim.getJsonFilePath());
		List<JSON> incrementList = GlobalData.jsonIncrementNodeMap.get(jim.getIncrementListName());
		if (incrementList != null) {
			ObservableList<JSON> items = FXCollections.observableArrayList(incrementList);
			incrementjsonlist.setItems(items);
		}
		//加载赋值配置
		JsonAssignModel jam = Config.getJsonAssign();
		CheckBox assignenable = (CheckBox) this.lookup("#assignenable");
		Label assignvalueslabel = (Label) this.lookup("#assignvalueslabel");
		TextField assignvaluesfield = (TextField) this.lookup("#assignvaluesfield");
		Button assignvaluesbtn = (Button) this.lookup("#assignvaluesbtn");
		Label assignjsonlabel = (Label) this.lookup("#assignjsonlabel");
		TextField assignjsonfield = (TextField) this.lookup("#assignjsonfield");
		Button assignjsonbtn = (Button) this.lookup("#assignjsonbtn");
		TreeView<?> assignjsontree = (TreeView<?>) this.lookup("#assignjsontree");
		ListView<JSON> assignjsonlist = (ListView<JSON>) this.lookup("#assignjsonlist");
		Button assignjsonadd = (Button) this.lookup("#assignjsonadd");
		Button assignjsondel = (Button) this.lookup("#assignjsondel");
		if(!jam.isDisable()) {
			assignenable.setSelected(true);
			assignjsonlist.setDisable(false);
			assignjsontree.setDisable(false);
			assignvaluesbtn.setDisable(false);
			assignvaluesfield.setDisable(false);
			assignvalueslabel.setDisable(false);
			assignjsonlabel.setDisable(false);
			assignjsonfield.setDisable(false);
			assignjsonbtn.setDisable(false);
			assignjsonadd.setDisable(false);
			assignjsondel.setDisable(false);
		}
		assignvaluesfield.setText(jam.getValuesFilePath());
		assignjsonfield.setText(jam.getJsonFilePath());
		List<JSON> assignList = GlobalData.jsonAssignNodeMap.get(jam.getAssignListName());
		if (assignList != null) {
			ObservableList<JSON> items = FXCollections.observableArrayList(assignList);
			assignjsonlist.setItems(items);
		}
	}
}

