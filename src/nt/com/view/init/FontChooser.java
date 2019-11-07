package nt.com.view.init;

import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nt.com.config.Config;
import nt.com.model.FontFamilyModel;
import nt.com.model.FontSizeModel;
/**
 * 字体选择器
 * @author kege
 *
 */
public class FontChooser extends VBox{
	
	@SuppressWarnings("unchecked")
	public FontChooser(String areaType) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/FontChooser.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		Stage stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setTitle("选择字体 - "+areaType);
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		Label areaTypelabel = (Label) this.lookup("#areatypelabel");
		areaTypelabel.setText(areaType);
		ListView<FontFamilyModel> fontList=(ListView<FontFamilyModel>) this.lookup("#family");
		ObservableList<FontFamilyModel> ffmlist = FXCollections.observableArrayList();
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String[] fontNames = e.getAvailableFontFamilyNames();
	    FontFamilyModel selectFmaily=null;
	    Font currfont = null;
	    if(areaType.equals("Edit")) {
	    	currfont = Config.getEditFont();
	    }else {
	    	currfont =Config.getConsoleFont();
	    }
	    for(String family:fontNames) {
	    	FontFamilyModel addFmaily = new FontFamilyModel(family);
	    	if(family.toUpperCase().equals(currfont.getFamily().toUpperCase())) {
	    		selectFmaily=addFmaily;
	    	}
	    	ffmlist.add(addFmaily);
	    }
		fontList.setItems(ffmlist);
		fontList.getSelectionModel().select(selectFmaily);
		ListView<FontSizeModel> sizeList=(ListView<FontSizeModel>) this.lookup("#size");
		ObservableList<FontSizeModel> fsmlist = FXCollections.observableArrayList();
		FontSizeModel selectSize =null;
		for(int i =1;i<36;i++) {
			FontSizeModel addSize=new FontSizeModel(i);
			if(i==(int)currfont.getSize()) {
				selectSize=addSize;
			}
			fsmlist.add(addSize);
		}
		sizeList.setItems(fsmlist);
		sizeList.getSelectionModel().select(selectSize);
	}
}
