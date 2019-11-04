package nt.com.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
/**
 * ¼òµ¥½âÎöjson
 * @author kege
 *
 */
public class JsonParser {
	
	public static String arrayStringify(List<Map> list) {
	
		return JSON.toJSONString(list);
	}
	
	public static String objectStringify(Map map) {
		return JSON.toJSONString(map);
	}
	
	public static String XmlToJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
	    String json = xmlSerializer.read(xml).toString();
		return json;
	}
	
	public static String JsonToXml(String json,String root) {
		XMLSerializer xmlSerializer = new XMLSerializer();
	    xmlSerializer.setRootName(root);
	    xmlSerializer.setTypeHintsEnabled(false);
	    String xmlstr="";
	    if (json.contains("[") && json.contains("]")) {
	    	JSONArray jobj = JSONArray.fromObject(json);
	    	xmlstr = xmlSerializer.write(jobj);
	    } else {
	    	JSONObject jobj = JSONObject.fromObject(json);
	    	xmlstr = xmlSerializer.write(jobj);
	    }
		return xmlstr;
	}
	
	public static String convertFormatJsonStr(String jsonStr) {
		 JSON object = JSON.parseObject(jsonStr);
		 String formatStr = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
		 return formatStr; 
	}
	
}
