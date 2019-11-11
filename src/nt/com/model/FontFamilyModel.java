package nt.com.model;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/**
 * ��������ģ��
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
