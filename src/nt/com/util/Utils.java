package nt.com.util;

import java.io.File;

import nt.com.enmu.TextType;

/*
 * 其它一些工具
 */
public class Utils {
	
	public static TextType getTextType(File file) {
		TextType type=null;
		String name = file.getName();
		String exName = name.substring(name.lastIndexOf('.')+1, name.length());
		if(exName.trim().toUpperCase().equals("XML")) {
			type=TextType.XML;
		}else if(exName.trim().toUpperCase().equals("JS")){
			type=TextType.JAVASCRIPT;
		}else if(exName.trim().toUpperCase().equals("JSON")) {
			type=TextType.JSON;
		}else if(exName.trim().toUpperCase().equals("JAVA")) {
			type=TextType.JAVASCRIPT;
		}else if(exName.trim().toUpperCase().equals("HTML")) {
			type=TextType.XML;
		}else {
			type=TextType.UNKNOW;
		}
		return type;
	}
}
