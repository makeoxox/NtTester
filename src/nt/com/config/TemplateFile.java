package nt.com.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 读取模板文件
 * 
 * @author kege
 *
 */
public class TemplateFile {

	public static String JsonTemplate() {
		String line = "";
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(TemplateFile.class.getResourceAsStream("/template/newfile_json.json"), "GBK"));
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static String XmlTemplate() {
		String line = "";
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(TemplateFile.class.getResourceAsStream("/template/newfile_xml.xml"), "GBK"));
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public static String JsTemplate() {
		String line = "";
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(TemplateFile.class.getResourceAsStream("/template/newfile_js.js"), "GBK"));
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	
}
