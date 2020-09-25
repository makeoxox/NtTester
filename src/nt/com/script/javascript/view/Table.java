package nt.com.script.javascript.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table extends VBox{
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Table(List<Map<String,Object>> list,String title){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/script/javascript/view/fxml/Table.fxml"));
		fxmlLoader.setRoot(this);
		try {
			 fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("table-"+title);
		stage.setResizable(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		
		TableView<Map<String, Object>> table = (TableView<Map<String, Object>>) this.lookup("#table");
		ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(list);
		Map<String,Object> map =list.get(0);
		Set<String> set =map.keySet();
		for(Iterator<String> it=set.iterator();it.hasNext();) {
			String colname =it.next().toString();
			TableColumn<Map<String, Object>, String> col = new TableColumn<>(colname);
			col.setCellValueFactory(new MapValueFactory(colname));  //map型数据工厂
			table.getColumns().add(col);
		}
		table.setItems(data);
	}
}
