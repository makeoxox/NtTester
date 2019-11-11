package nt.com.model;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/**
 * 字体类型模型
 * 
 * @author kege
 *
 */
public class FontFamilyModel extends Label{
	
	public FontFamilyModel(String family){
		this.setFont(new Font(family,14));
		this.setText(family);
	}
	
	public String getFamily() {
		return this.getText().trim();
	}
	
}
