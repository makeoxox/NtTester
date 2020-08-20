package nt.com.global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Collections;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 读取模板文件
 * 
 * @author kege
 *
 */
public class TemplateFile {
		
	
	
	public static String JavaTemplate(String className) throws   IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDefaultEncoding("GBK");
		cfg.setTemplateLoader(new ClassTemplateLoader(TemplateFile.class, "/template"));
		Template template = cfg.getTemplate("newfile_java.ftl");
		StringWriter swr=new StringWriter();
		template.process(Collections.singletonMap("className", className), swr);
		String javaStr  = swr.toString();
		swr.close();
		return javaStr;
	}

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
