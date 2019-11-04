package nt.com.view.init;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nt.com.enmu.FileType;
import nt.com.model.NewFileListModel;

/**
 * 新建文件窗口
 * 
 * @author kege
 *
 */
public class NewFileView extends VBox {

	@SuppressWarnings("unchecked")
	public NewFileView() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/nt/com/view/fxml/NewFileView.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("新建");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		ListView<NewFileListModel> fileList = (ListView<NewFileListModel>) this.lookup("#filelist");
		NewFileListModel dirType = new NewFileListModel(" 文件夹","/res/dir.png",FileType.DIR);
		NewFileListModel jsType = new NewFileListModel(" JavaScript 文件", "/res/js.png",FileType.JAVASCRIPT);
		NewFileListModel xmlrType = new NewFileListModel(" XML 文件","/res/xml.png",FileType.XML);
		NewFileListModel jsonType = new NewFileListModel(" Json 文件","/res/json.png",FileType.JSON);
		ObservableList<NewFileListModel> modelList = FXCollections.observableArrayList(dirType, jsType, xmlrType,jsonType);
		fileList.setItems(modelList);
	}

}
