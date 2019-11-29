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
 * �򵥽���Xml
 * @author kege
 *
 */
public class XmlParser {
	
	static SAXReader reader = new SAXReader();

	//��ȡ��Ŀ��xml
	public static Document getDocByClassPath(String path) throws DocumentException{
	
			Document document = reader.read(XmlParser.class.getClassLoader().getResourceAsStream(path));
			return document;  
	 
	}
	
	//��ȡ�ļ�ϵͳ��xml
	public static Document getDocByAbsolutePath(String path) throws DocumentException{
		
			Document document = reader.read(new File(path));
			return document;  
		
	}
	
	//��ȡ�ַ���xml
	public static Document getDocByString(String text,String Codec) throws UnsupportedEncodingException, DocumentException{
		
			Document document = reader.read(new ByteArrayInputStream(text.getBytes(Codec)));
			return document;  
	
	}
	
	//��ʽ��xml
	public static String convertFormatXMLStr(Document doc,String codec) throws IOException {
		XMLWriter out = null;
		StringWriter stringWriter = new StringWriter();
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setNewLineAfterDeclaration(false); // ȡ���ڶ��п���
		outputFormat.setIndent(true); // �����Ƿ�����
		outputFormat.setIndent("    "); // ���ĸ��ո�ʽʵ������
		outputFormat.setNewlines(true); // �����Ƿ���
		outputFormat.setEncoding(codec);
		out = new XMLWriter(stringWriter, outputFormat);
		out.write(doc);
		stringWriter.close();
		out.close();
		return stringWriter.toString();
	}
}
