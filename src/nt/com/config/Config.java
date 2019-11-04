package nt.com.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

import javafx.scene.text.Font;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nt.com.util.JsonParser;

/**
 * ≈‰÷√Œƒº˛¿‡
 * 
 * @author kege
 *
 */
public class Config {

	private final static JSONObject CONFIG;
	private final static Properties PROPERTIES = new Properties();

	static {
		BufferedReader br;
		String line = "";
		String content = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("config/boot.json"))));
			while ((line = (String) br.readLine()) != null) {
				content += line + "\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CONFIG = JSONObject.fromObject(content);
	}

	public static String getProp(String prop) {
		try {
			InputStream is = new FileInputStream(new File("config/msg.properties"));
			PROPERTIES.load(is);
			String value = PROPERTIES.getProperty(prop);
			is.close();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void setProp(String prop, String val) {

		try {
			InputStream is = new FileInputStream(new File("config/msg.properties"));
			PROPERTIES.load(is);
			OutputStream out = new FileOutputStream(new File("config/msg.properties"));
			PROPERTIES.setProperty(prop, val);
			PROPERTIES.store(out, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void WriteConfig(String jsonStr) {
		jsonStr =JsonParser.convertFormatJsonStr(jsonStr);
		Writer write;
		try {
			write = new OutputStreamWriter(new FileOutputStream("config/boot.json"));
			write.write(jsonStr);
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getLastOpenFilePath() {
		return CONFIG.getString("lastOpenFilePath");
	}

	public static void setLastOpenFilePath(String path) {
		CONFIG.element("lastOpenFilePath", path);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}

	public static String getVersion() {
		return CONFIG.get("version").toString();
	}
	
	public static Font getEditFont() {
		JSONObject editFont =JSONObject.fromObject(CONFIG.get("editFont"));
		return new Font(editFont.getString("family"),editFont.getInt("size"));
	}
	
	public static void setEditFont(String family,int size) {
		JSONObject editFont =JSONObject.fromObject(CONFIG.get("editFont"));
		editFont.element("family", family);
		editFont.element("size", size);
		CONFIG.element("editFont", editFont);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	
	public static Font getConsoleFont() {
		JSONObject editFont =JSONObject.fromObject(CONFIG.get("consoleFont"));
		return new Font(editFont.getString("family"),editFont.getInt("size"));
	}
	
	
	public static void setConsoleFont(String family,int size) {
		JSONObject consoleFont =JSONObject.fromObject(CONFIG.get("consoleFont"));
		consoleFont.element("family", family);
		consoleFont.element("size", size);
		CONFIG.element("consoleFont", consoleFont);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<String> getImportDirectories(){
		JSONArray importDirectories = JSONArray.fromObject(CONFIG.get("importDirectories"));
		List<String> list =(List<String>) JSONArray.toCollection(importDirectories);
		return list;
	}
	
	public static void addImportDirectory(String path) {
		for(String p:getImportDirectories()) {
			if(p.equals(path))return;
		}
		JSONArray importDirectories = JSONArray.fromObject(CONFIG.get("importDirectories"));
		importDirectories.add(path);
		CONFIG.element("importDirectories", importDirectories);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static void delImportDirectory(String path) {
		JSONArray importDirectories = JSONArray.fromObject(CONFIG.get("importDirectories"));
		for(int i =0;i<importDirectories.size();i++) {
			if(path.equals(importDirectories.get(i))) {
				importDirectories.remove(i);
				break;
			}
		}
		CONFIG.element("importDirectories", importDirectories);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	
}
