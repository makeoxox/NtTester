package nt.com.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;


import javafx.scene.image.Image;
/**
 * base64×Ö·û´®×ªImage¶ÔÏó
 * @author kege
 *
 */
public class Picture {
	public static Image Base64Img(String b64imgStr) throws IOException {
		
			byte[] base64 = Base64.getDecoder().decode(b64imgStr);
			Image img = new Image(new ByteArrayInputStream(base64));
			return img;
		
	}
	
}
