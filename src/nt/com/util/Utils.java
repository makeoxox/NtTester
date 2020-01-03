package nt.com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import nt.com.enums.TextType;

/**
 * ����һЩ����
 * 
 * @author kege
 */
public class Utils {
	
	/**
	 * ����ļ����ı�����ö��
	 */
	public static TextType getTextType(File file) {
		TextType type=null;
		String name = file.getName();
		String exName = name.substring(name.lastIndexOf('.')+1, name.length());
		switch (exName.trim().toUpperCase()) {
			case "XML" :
				type=TextType.XML;
				break;
			case "JS" :
				type=TextType.JAVASCRIPT;
				break;
			case "JSON" :
				type=TextType.JSON;
				break;
			case "JAVA" :
				type=TextType.JAVASCRIPT;
				break;
			case "HTML" :
				type=TextType.HTML;
				break;
			case "TXT" :
				type=TextType.TXT;
				break;
			case "FXML" :
				type=TextType.XML;
				break;
			default :
				type=TextType.UNKNOW;
		}
		return type;
	}
	
	//��ȡ�ļ�����
	public static String ReadFiletoString(File file,String code) throws IOException {
		String line="";
		String contend="";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),code));
		while((line=br.readLine())!=null) {
			contend+=line+"\n";
		}
		br.close();
		return contend;
	}
	
	//д�����ݵ��ļ�
		public static void WriteStringtoFile(String content,boolean overwrite,File file,String code) throws IOException {
			BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,overwrite),code));
			wr.write(content);
			wr.flush();
			wr.close();
		}
	
}
