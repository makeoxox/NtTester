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
 * 配置文件类
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
	
	//最后一次打开文件的路径
	public static String getLastOpenFilePath() {
		return CONFIG.getString("lastOpenFilePath");
	}

	public static void setLastOpenFilePath(String path) {
		CONFIG.element("lastOpenFilePath", path);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	//版本号
	public static String getVersion() {
		return CONFIG.get("version").toString();
	}
	
	//编辑面板字体
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
	
	//控制台字体
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
	
	//外部导入的文件列表
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
	//编码方式
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
	//连接方式
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
	//协议方式
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
	//加密方式
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
	//并发方式
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
	//报文类型
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
	//脚本方式
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
	
	//报文长度计算方式
	public static String getLengthType() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("lengthtype").toString();
	}
	
	public static void setLengthType(String lengthtype) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("lengthtype", lengthtype);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//报文长度补位方式
	public static String getFixType() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("fixtype").toString();
	}
	
	public static void setFixType(String fixtype) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("fixtype", fixtype);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//IP地址
	public static String getIP() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("ip").toString();
	}
	
	public static void setIP(String ip) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("ip", ip);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//端口
	public static String getPort() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("port").toString();
	}
	
	public static void setPort(String port) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("port", port);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//补位字符
	public static String getFixChar() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("fixchar").toString();
	}
	
	public static void setFixChar(String fixchar) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("fixchar", fixchar);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//补位长度
	public static String getFixLength() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("fixlength").toString();
	}
	
	public static void setFixLength(String fixlength) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("fixlength", fixlength);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//返回报文偏移度
	public static String getOffset() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.get("offset").toString();
	}
	
	public static void setOffset(String offset) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("offset", offset);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//URL
	public static String getURL() {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		return httpSetting.get("url").toString();
	}
	
	public static void setURL(String url) {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		httpSetting.element("url", url);
		CONFIG.element("httpSetting", httpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//http请求方式
	public static String getRequestType() {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		return httpSetting.get("requesttype").toString();
	}
	
	public static void setRequestType(String requesttype) {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		httpSetting.element("requesttype", requesttype);
		CONFIG.element("httpSetting", httpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//content-type
	public static String getContentType() {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		return httpSetting.get("contenttype").toString();
	}
	
	public static void setContentType(String contenttype) {
		JSONObject httpSetting =JSONObject.fromObject(CONFIG.get("httpSetting"));
		httpSetting.element("contenttype", contenttype);
		CONFIG.element("httpSetting", httpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
}
