package nt.com.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import nt.com.enmu.FileType;

/**
 * 文件选择List文件模型
 * 
 * @author kege
 *
 */
public class NewFileListModel extends HBox{
	
	private FileType type;
	
	public void setFileType(FileType type) {
		this.type=type;
	}
	
	public FileType getFileType() {
		return this.type;
	}

	public NewFileListModel(String text, String imageClassPath,FileType type) {
		Label label = new Label();
		ImageView icon = new ImageView();
		label.setText(text);
		icon.setImage(new Image(getClass().getResourceAsStream(imageClassPath)));
		icon.setFitWidth(15);
		icon.setFitHeight(15);
		this.getChildren().add(icon);
		this.getChildren().add(label);
		this.type=type;
	}
	
	
	
}
