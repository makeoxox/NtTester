package nt.com.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 简单解析Xml
 * @author kege
 *
 */
public class XmlParser {
	
	static SAXReader reader = new SAXReader();

	//读取项目内xml
	public static Document getDocByClassPath(String path) throws DocumentException{
	
			Document document = reader.read(XmlParser.class.getClassLoader().getResourceAsStream(path));
			return document;  
	 
	}
	
	//读取文件系统内xml
	public static Document getDocByAbsolutePath(String path) throws DocumentException{
		
			Document document = reader.read(new File(path));
			return document;  
		
	}
	
	//读取字符串xml
	public static Document getDocByString(String text,String Codec) throws UnsupportedEncodingException, DocumentException{
		
			Document document = reader.read(new ByteArrayInputStream(text.getBytes(Codec)));
			return document;  
	
	}
	
	//格式化xml
	public static String convertFormatXMLStr(Document doc,String codec) throws IOException {
		XMLWriter out = null;
		StringWriter stringWriter = new StringWriter();
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setNewLineAfterDeclaration(false); // 取消第二行空行
		outputFormat.setIndent(true); // 设置是否缩进
		outputFormat.setIndent("    "); // 以四个空格方式实现缩进
		outputFormat.setNewlines(true); // 设置是否换行
		outputFormat.setEncoding(codec);
		out = new XMLWriter(stringWriter, outputFormat);
		out.write(doc);
		stringWriter.close();
		out.close();
		return stringWriter.toString();
	}
}
