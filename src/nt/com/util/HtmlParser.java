package nt.com.util;


import java.io.IOException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;


/**
 * ºÚµ•Ω‚ŒˆHtml
 * @author kege
 *
 *
 */
public class HtmlParser {
	
	public static String  convertFormatHTMLStr(String htmlStr,String encode) throws SAXException, IOException, TransformerException{
		Document doc = Jsoup.parse(htmlStr);
		return doc.html();
	}

	
	
	
	
	
	
}