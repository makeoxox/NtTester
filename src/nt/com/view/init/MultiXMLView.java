package nt.com.view.init;

import java.io.IOException;
import java.util.List;

import org.dom4j.Node;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.config.GlobalData;
import nt.com.model.XmlAssignModel;
import nt.com.model.XmlIncrementModel;

public class MultiXMLView extends VBox {

	@SuppressWarnings("unchecked")
	public MultiXMLView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/MultiXMLView.fxml"));
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
		stage.setTitle("并发设置 - XML");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		//加载并发量配置
		Label concurrency = (Label) this.lookup("#concurrency");
		concurrency.setText(Config.getXMLConcurrency() + "");
		Slider multicount = (Slider) this.lookup("#multicount");
		multicount.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int value = newValue.intValue();
				concurrency.setText(value + "");
			}
		});
		//加载递增配置
		XmlIncrementModel xim = Config.getXmlIncrement();
		CheckBox incrementenable = (CheckBox) this.lookup("#incrementenable");
		RadioButton incrementtextbtn = (RadioButton) this.lookup("#incrementtextbtn");
		RadioButton incrementattrbtn = (RadioButton) this.lookup("#incrementattrbtn");
		Label incrementxmllabel = (Label) this.lookup("#incrementxmllabel");
		TextField incrementxmlfield = (TextField) this.lookup("#incrementxmlfield");
		Button incrementxmlbtn = (Button) this.lookup("#incrementxmlbtn");
		Label incrementattrlabel = (Label) this.lookup("#incrementattrlabel");
		TextField incrementattrfield = (TextField) this.lookup("#incrementattrfield");
		TreeView<?> incrementeletree = (TreeView<?>) this.lookup("#incrementeletree");
		ListView<Node> incrementelelist = (ListView<Node>) this.lookup("#incrementelelist");
		Button incrementadd = (Button) this.lookup("#incrementadd");
		Button incrementdel = (Button) this.lookup("#incrementdel");
		if (!xim.isDisable()) {
			incrementenable.setSelected(true);
			incrementtextbtn.setDisable(false);
			incrementattrbtn.setDisable(false);
			incrementxmllabel.setDisable(false);
			incrementxmlfield.setDisable(false);
			incrementxmlbtn.setDisable(false);
			incrementeletree.setDisable(false);
			incrementelelist.setDisable(false);
			incrementadd.setDisable(false);
			incrementdel.setDisable(false);
			if (xim.getType().equals("attribution")) {
				incrementattrlabel.setDisable(false);
				incrementattrfield.setDisable(false);
			}
		}
		if (xim.getType().equals("attribution")) {
			incrementattrbtn.setSelected(true);
		} else if (xim.getType().equals("text")) {
			incrementtextbtn.setSelected(true);
		}
		incrementattrfield.setText(xim.getAttrname());
		incrementxmlfield.setText(xim.getXmlFilePath());
		List<Node> incrementList = GlobalData.xmlIncrementNodeMap.get(xim.getIncrementListName());
		if (incrementList != null) {
			ObservableList<Node> items = FXCollections.observableArrayList(incrementList);
			incrementelelist.setItems(items);
		}
		//加载赋值配置
		XmlAssignModel xam = Config.getXmlAssign();
		CheckBox assignenable = (CheckBox) this.lookup("#assignenable");
		RadioButton assigntextbtn = (RadioButton) this.lookup("#assigntextbtn");
		RadioButton assignattrbtn = (RadioButton) this.lookup("#assignattrbtn");
		Label assignvalueslabel = (Label) this.lookup("#assignvalueslabel");
		TextField assignvaluesfield = (TextField) this.lookup("#assignvaluesfield");
		Button assignvaluesbtn = (Button) this.lookup("#assignvaluesbtn");
		Label assignxmllabel = (Label) this.lookup("#assignxmllabel");
		TextField assignxmlfield = (TextField) this.lookup("#assignxmlfield");
		Button assignxmlbtn = (Button) this.lookup("#assignxmlbtn");
		Label assignattrlabel = (Label) this.lookup("#assignattrlabel");
		TextField assignattrfield = (TextField) this.lookup("#assignattrfield");
		TreeView<?> assigneletree = (TreeView<?>) this.lookup("#assigneletree");
		ListView<Node> assignelelist = (ListView<Node>) this.lookup("#assignelelist");
		Button assignadd = (Button) this.lookup("#assignadd");
		Button assigndel = (Button) this.lookup("#assigndel");
		if(!xam.isDisable()) {
			assignenable.setSelected(true);
			assigntextbtn.setDisable(false);
			assignattrbtn.setDisable(false);
			assigndel.setDisable(false);
			assignelelist.setDisable(false);
			assigneletree.setDisable(false);
			assignvaluesbtn.setDisable(false);
			assignvaluesfield.setDisable(false);
			assignxmlbtn.setDisable(false);
			assignvalueslabel.setDisable(false);
			assignxmlfield.setDisable(false);
			assignxmllabel.setDisable(false);
			assignadd.setDisable(false);
			if(xam.getType().equals("attribution")) {
				assignattrfield.setDisable(false);
				assignattrlabel.setDisable(false);
			}
		}
		if(xam.getType().equals("attribution")) {
			assignattrbtn.setSelected(true);
		}else if(xam.getType().equals("text")) {
			assigntextbtn.setSelected(true);
		}
		assignvaluesfield.setText(xam.getValuesFilePath());
		assignxmlfield.setText(xam.getXmlFilePath());
		assignattrfield.setText(xam.getAttrname());
		List<Node> assignList = GlobalData.xmlAssignNodeMap.get(xam.getAssignListName());
		if (assignList != null) {
			ObservableList<Node> items = FXCollections.observableArrayList(assignList);
			assignelelist.setItems(items);
		}
	}
}
