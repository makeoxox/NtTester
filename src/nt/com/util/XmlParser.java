package nt.com.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * �򵥽���Xml
 * @author kege
 *
 */
public class XmlParser {
	
	private static Log log = LogFactory.getLog(XmlParser.class);
	
	static SAXReader reader = new SAXReader();

	//��ȡ��Ŀ��xml
	public static Document getDocByClassPath(String path){
		try {
			Document document = reader.read(XmlParser.class.getClassLoader().getResourceAsStream(path));
			return document;  
		} catch (DocumentException e) {
			log.error(e.getLocalizedMessage());
		}
		return null;  
	}
	
	//��ȡ�ļ�ϵͳ��xml
	public static Document getDocByAbsolutePath(String path){
		try {
			Document document = reader.read(new File(path));
			return document;  
		} catch (DocumentException e) {
			log.error(e.getLocalizedMessage());
		}
		return null;  
	}
	
	//��ȡ�ַ���xml
	public static Document getDocByString(String text,String Codec){
		try {
			Document document = reader.read(new ByteArrayInputStream(text.getBytes(Codec)));
			return document;  
		} catch (DocumentException e) {
		} catch (UnsupportedEncodingException e) {
			log.error(e.getLocalizedMessage());
		}
		return null;  
	}
	
}
