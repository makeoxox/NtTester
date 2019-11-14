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

import javafx.scene.text.Font;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nt.com.model.JsonAssignModel;
import nt.com.model.JsonIncrementModel;
import nt.com.model.XmlAssignModel;
import nt.com.model.XmlIncrementModel;
import nt.com.util.JsonParser;

/**
 * 全局配置类
 * 
 * @author kege
 *
 */
public class Config {

	private final static JSONObject CONFIG;

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
	public static int getFixLength() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.getInt("fixlength");
	}
	
	public static void setFixLength(int fixlength) {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		tcpSetting.element("fixlength", fixlength);
		CONFIG.element("tcpSetting", tcpSetting);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//返回报文偏移度
	public static int getOffset() {
		JSONObject tcpSetting =JSONObject.fromObject(CONFIG.get("tcpSetting"));
		return tcpSetting.getInt("offset");
	}
	
	public static void setOffset(int offset) {
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
	
	//xml并发量
	public static int getXMLConcurrency() {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		return multixml.getInt("concurrency");
	}
	
	public static void setXMLConcurrency(int concurrency) {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		multixml.element("concurrency", concurrency);
		CONFIG.element("multixml", multixml);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//XML递增属性模型
	public static XmlIncrementModel getXmlIncrement() {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		JSONObject increment =JSONObject.fromObject(multixml.get("increment"));
		XmlIncrementModel xim = new XmlIncrementModel();
		boolean disable = increment.getBoolean("disable");
		String type  =  increment.getString("type");
		String xmlfilepath  =  increment.getString("xmlfilepath");
		String attrname = increment.getString("attrname");
		String incrementlistname = increment.getString("incrementlistname");
		xim.setAttrname(attrname);
		xim.setDisable(disable);
		xim.setIncrementlListName(incrementlistname);
		xim.setType(type);
		xim.setXmlFilePath(xmlfilepath);
		return xim;
	}
	
	public static void setXmlIncrement(XmlIncrementModel xim) {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		JSONObject increment =JSONObject.fromObject(multixml.get("increment"));
		increment.element("disable", xim.isDisable());
		increment.element("type", xim.getType());
		increment.element("xmlfilepath", xim.getXmlFilePath());
		increment.element("attrname", xim.getAttrname());
		increment.element("incrementlistname", xim.getIncrementlListName());
		multixml.element("increment", increment);
		CONFIG.element("multixml", multixml);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//xml赋值属性模型
	public static XmlAssignModel getXmlAssign() {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		JSONObject assign =JSONObject.fromObject(multixml.get("assign"));
		XmlAssignModel xam = new XmlAssignModel();
		boolean disable = assign.getBoolean("disable");
		String type  =  assign.getString("type");
		String xmlfilepath  =  assign.getString("xmlfilepath");
		String attrname = assign.getString("attrname");
		String valuesfilepath = assign.getString("valuesfilepath");
		String assignlistname = assign.getString("assignlistname");
		xam.setAttrname(attrname);
		xam.setDisable(disable);
		xam.setAssignListName(assignlistname);
		xam.setValuesFilePath(valuesfilepath);
		xam.setType(type);
		xam.setXmlFilePath(xmlfilepath);
		return xam;
	}
	
	public static void setXmlAssign(XmlAssignModel xam) {
		JSONObject multixml =JSONObject.fromObject(CONFIG.get("multixml"));
		JSONObject assign =JSONObject.fromObject(multixml.get("assign"));
		assign.element("disable", xam.isDisable());
		assign.element("type", xam.getType());
		assign.element("xmlfilepath", xam.getXmlFilePath());
		assign.element("attrname", xam.getAttrname());
		assign.element("valuesfilepath", xam.getValuesFilePath());
		assign.element("assignlistname", xam.getAssignListName());
		multixml.element("assign", assign);
		CONFIG.element("multixml", multixml);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//json并发量
	public static int getJSONConcurrency() {
			JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
			return multijson.getInt("concurrency");
	}
		
	public static void setJSONConcurrency(int concurrency) {
			JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
			multijson.element("concurrency", concurrency);
			CONFIG.element("multijson", multijson);
			String jsonStr = CONFIG.toString();
			WriteConfig(jsonStr);
	}
	
	//json递增属性模型
	public static JsonIncrementModel getJsonIncrement() {
		JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
		JSONObject increment =JSONObject.fromObject(multijson.get("increment"));
		JsonIncrementModel jim = new JsonIncrementModel();
		boolean disable = increment.getBoolean("disable");
		String jsonfilepath  =  increment.getString("jsonfilepath");
		String incrementlistname = increment.getString("incrementlistname");
		jim.setDisable(disable);
		jim.setIncrementListName(incrementlistname);
		jim.setJsonFilePath(jsonfilepath);
		return jim;
	}
	
	public static void setJsonIncrement(JsonIncrementModel jim) {
		JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
		JSONObject increment =JSONObject.fromObject(multijson.get("increment"));
		increment.element("disable", jim.isDisable());
		increment.element("jsonfilepath", jim.getJsonFilePath());
		increment.element("incrementlistname", jim.getIncrementListName());
		multijson.element("increment", increment);
		CONFIG.element("multijson", multijson);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}
	
	//json赋值属性模型
	public static JsonAssignModel getJsonAssign() {
		JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
		JSONObject assign =JSONObject.fromObject(multijson.get("assign"));
		JsonAssignModel jam = new JsonAssignModel();
		boolean disable = assign.getBoolean("disable");
		String jsonfilepath  =  assign.getString("jsonfilepath");
		String valuesfilepath = assign.getString("valuesfilepath");
		String assignlistname = assign.getString("assignlistname");
		jam.setDisable(disable);
		jam.setAssignListName(assignlistname);
		jam.setValuesFilePath(valuesfilepath);
		jam.setJsonFilePath(jsonfilepath);
		return jam;
	}
	
	public static void setJsonAssign(JsonAssignModel jam) {
		JSONObject multijson =JSONObject.fromObject(CONFIG.get("multijson"));
		JSONObject assign =JSONObject.fromObject(multijson.get("assign"));
		assign.element("disable", jam.isDisable());
		assign.element("jsonfilepath", jam.getJsonFilePath());
		assign.element("valuesfilepath", jam.getValuesFilePath());
		assign.element("assignlistname", jam.getAssignListName());
		multijson.element("assign", assign);
		CONFIG.element("multijson", multijson);
		String jsonStr = CONFIG.toString();
		WriteConfig(jsonStr);
	}

}
