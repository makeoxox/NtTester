package nt.com.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
/**
 * base64×Ö·û´®×ªImage¶ÔÏó
 * @author kege
 *
 */
public class Picture {
	public static Image Base64Img(String b64imgStr) {
		try {
			byte[] base64 = Base64.getDecoder().decode(b64imgStr);
			ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(base64));
			Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputstream);
			BufferedImage bi = null;
			while (it.hasNext()) {
				ImageReader imageReader = it.next();
				imageReader.setInput(imageInputstream, true, true);
				try {
					bi = imageReader.read(0, imageReader.getDefaultReadParam());
				} catch (Exception e) {
					imageReader.dispose();

				}
			}
			imageInputstream.close();
			return (Image) bi;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
