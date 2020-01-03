package nt.com.view.init;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.global.Config;
import nt.com.global.GlobalData;
import nt.com.model.TxtAssignModel;
import nt.com.model.TxtFieldModel;
import nt.com.model.TxtIncrementModel;

public class MultiTxtView extends VBox {

	@SuppressWarnings("unchecked")
	public MultiTxtView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/MultiTxtView.fxml"));
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
		stage.setTitle("并发设置 - 文本");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		//加载并发量配置
		Label concurrency = (Label) this.lookup("#concurrency");
		concurrency.setText(Config.getTxtConcurrency() + "");
		Slider multicount = (Slider) this.lookup("#multicount");
		multicount.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int value = newValue.intValue();
				concurrency.setText(value + "");
			}
		});
		//加载递增配置
		TxtIncrementModel tim = Config.getTxtIncrement();
		CheckBox incrementenable = (CheckBox) this.lookup("#incrementenable");
		Label incrementoffsetlabel = (Label) this.lookup("#incrementoffsetlabel");
		Label incrementlenlabel = (Label) this.lookup("#incrementlenlabel");
		TextField incrementoffsetfield = (TextField) this.lookup("#incrementoffsetfield");
		TextField incrementlenfield = (TextField) this.lookup("#incrementlenfield");
		ListView<TxtFieldModel> incrementelelist = (ListView<TxtFieldModel>) this.lookup("#incrementelelist");
		Button incrementadd = (Button) this.lookup("#incrementadd");
		Button incrementdel = (Button) this.lookup("#incrementdel");
		if (!tim.isDisable()) {
			incrementenable.setSelected(true);
			incrementelelist.setDisable(false);
			incrementadd.setDisable(false);
			incrementdel.setDisable(false);
			incrementoffsetlabel.setDisable(false);
			incrementlenlabel.setDisable(false);
			incrementoffsetfield.setDisable(false);
			incrementlenfield.setDisable(false);
		}
		List<TxtFieldModel> incrementList = GlobalData.txtIncrementNodeMap.get(tim.getIncrementListName());
		if (incrementList != null) {
			ObservableList<TxtFieldModel> items = FXCollections.observableArrayList(incrementList);
			incrementelelist.setItems(items);
		}
		//加载赋值配置
		TxtAssignModel tam = Config.getTxtAssign();
		CheckBox assignenable = (CheckBox) this.lookup("#assignenable");
		Label assignvalueslabel = (Label) this.lookup("#assignvalueslabel");
		TextField assignvaluesfield = (TextField) this.lookup("#assignvaluesfield");
		Button assignvaluesbtn = (Button) this.lookup("#assignvaluesbtn");
		Label assignoffsetlabel = (Label) this.lookup("#assignoffsetlabel");
		Label assignlenlabel = (Label) this.lookup("#assignlenlabel");
		TextField assignoffsetfield = (TextField) this.lookup("#assignoffsetfield");
		TextField assignlenfield = (TextField) this.lookup("#assignlenfield");
		ListView<TxtFieldModel> assignelelist = (ListView<TxtFieldModel>) this.lookup("#assignelelist");
		Button assignadd = (Button) this.lookup("#assignadd");
		Button assigndel = (Button) this.lookup("#assigndel");
		if(!tam.isDisable()) {
			assignenable.setSelected(true);
			assigndel.setDisable(false);
			assignelelist.setDisable(false);
			assignvaluesbtn.setDisable(false);
			assignvaluesfield.setDisable(false);
			assignvalueslabel.setDisable(false);
			assignadd.setDisable(false);
			assignoffsetlabel.setDisable(false);
			assignlenlabel.setDisable(false);
			assignoffsetfield.setDisable(false);
			assignlenfield.setDisable(false);
		}
		List<TxtFieldModel> assignList = GlobalData.txtAssignNodeMap.get(tam.getAssignListName());
		if (assignList != null) {
			ObservableList<TxtFieldModel> items = FXCollections.observableArrayList(assignList);
			assignelelist.setItems(items);
		}
		
	}
}
