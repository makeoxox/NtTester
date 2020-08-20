package nt.com.view.init;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nt.com.enums.FileType;
import nt.com.model.NewFileListModel;

/**
 * �½��ļ�����
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
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("�½�");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		ListView<NewFileListModel> fileList = (ListView<NewFileListModel>) this.lookup("#filelist");
		NewFileListModel projectType = new NewFileListModel(" ��Ŀ","/res/import.gif",FileType.PROJECT);
		NewFileListModel dirType = new NewFileListModel(" �ļ���","/res/dir.png",FileType.DIR);
		NewFileListModel jsType = new NewFileListModel(" JavaScript �ļ�", "/res/js.png",FileType.JAVASCRIPT);
		NewFileListModel xmlrType = new NewFileListModel(" XML �ļ�","/res/xml.png",FileType.XML);
		NewFileListModel jsonType = new NewFileListModel(" Json �ļ�","/res/json.png",FileType.JSON);
		NewFileListModel txtType = new NewFileListModel(" �ı��ļ�","/res/json.png",FileType.TXT);
		NewFileListModel javaType = new NewFileListModel(" Java �ļ�","/res/java.png",FileType.JAVA);
		ObservableList<NewFileListModel> modelList = FXCollections.observableArrayList(projectType,dirType,txtType, jsType,javaType, xmlrType,jsonType);
		fileList.setItems(modelList);
	}

}
