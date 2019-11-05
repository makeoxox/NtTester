package nt.com.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public static String getEncode() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("encode").toString();
	}
	
	public static void setEncode(String encode) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("encode", encode);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getConnect() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("connect").toString();
	}
	
	public static void setConnect(String connect) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("connect", connect);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getProtocol() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("protocol").toString();
	}
	
	public static void setProtocol(String protocol) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("protocol", protocol);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getMac() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("mac").toString();
	}
	
	public static void setMac(String mac) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("mac", mac);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getMulti() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("multi").toString();
	}
	
	public static void setMulti(String multi) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("multi", multi);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getMsg() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("msg").toString();
	}
	
	public static void setMsg(String msg) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("msg", msg);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	public static String getScript() {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		return menu.get("script").toString();
	}
	
	public static void setScript(String script) {
		JSONObject menu =JSONObject.fromObject(CONFIG.get("menu"));
		menu.element("script", script);
		CONFIG.element("menu", menu);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
}
