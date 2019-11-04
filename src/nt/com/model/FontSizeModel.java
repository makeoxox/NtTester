package nt.com.model;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import nt.com.config.Config;

public class FontSizeModel extends Label{
	
	public FontSizeModel(int size){
		this.setFont(new Font(Config.getEditFont().getFamily(),size));
		this.setText(size+"");
	}
	
	public int getSize() {
		return Integer.parseInt(this.getText().trim());
	}
}
