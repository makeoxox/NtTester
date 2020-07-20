package nt.com.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.NumberFormat;


/**
 * 字符串常用操作
 * @author kege
 *
 */
public class Str {

	public static String toNotNullString(Object obj) {
		return obj != null ? obj.toString().trim() : "";
	}
	
	public static String toStringIfNotNull(Object obj) {
		return obj ==null?null:""+obj;
	}
	
	public static StringBuffer appendln(StringBuffer strBuf, Object obj) {
		strBuf.append(obj);
		return strBuf.append("\n");
	}
	
	
	public static float toFloat(String str) {
		float result = -1.1211f;
		try{
			result = Float.parseFloat(str);
		}catch(Exception e) {
			return result;
		}
		
		
		return result;
	}
	
	public static int toInt(String str) {
		int result = -132365;
		try{
			result = Integer.parseInt(str);
		}catch(Exception e) {
			return result;
		}
		
		
		return result;
	}
	
	public static String getFirstString(String[] array) {
		if(array == null || array.length<1) {
			return "未知";
		}
		
		for(int i=0; i<array.length; i++) {
			if(array[i] != null && !"".equals(array[i].trim())) {
				return array[i].trim();
			}
		}
		
		return "未知";
	}
	
	// 返回两个时间相差的长�?  时间格式:yyyyMMddHHmmss
	public static final long getLongBetween(String flag, String time1,
			String time2) {
		try {
			Date date1 = new SimpleDateFormat("yyyyMMddHHmmss")
					.parse(time1);
			Date date2 = new SimpleDateFormat("yyyyMMddHHmmss")
					.parse(time2);
			long ltime = date1.getTime() - date2.getTime() < 0 ? date2
					.getTime()
					- date1.getTime() : date1.getTime() - date2.getTime();
			if (flag.equals("s")) {
				return ltime / 1000;// 返回�?
			} else if (flag.equals("m")) {
				return ltime / 60000;// 返回分钟
			} else if (flag.equals("h")) {
				return ltime / 3600000;// 返回小时
			} else if (flag.equals("d")) {
				return ltime / 86400000;// 返回天数
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1121318425;
		}

	}
	/**
	 * get the time now
	 * @return
	 */
	public static String getNowTime() {
		Date nowTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(nowTime);
	}
	
	/**
	 * get the time now
	 * @return
	 */
	public static String getNowTimeCMForm() {
		Date nowTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(nowTime);
	}
	
	/**
	 * get the time now
	 * @return
	 */
	public static String getNowTimeCNForm() {
		Date nowTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss�?");
		return formatter.format(nowTime);
	}
	
	/**
	 * set the time in china form (param must like :yyyyMMddHHmmss)
	 * @return
	 */
	public static String setTimeToCNForm(String time) {
		if(time == null || time.trim().length()<4) {
			return time;
		}
		time = time.trim();
		StringBuffer strBuf = new StringBuffer("");
		if(time.length() > 3) {
			strBuf.append(time.substring(0, 4) + "�?");
			if(time.length() > 5) {
				strBuf.append(time.substring(4, 6) + "�?");
				if(time.length() > 7) {
					strBuf.append(time.substring(6, 8) + "�? ");
					if(time.length() > 9) {
						strBuf.append(time.substring(8, 10) + "�?");
						if(time.length() > 11) {
							strBuf.append(time.substring(10, 12) + "�?");
							if(time.length() > 13) {
								strBuf.append(time.substring(12, 14) + "�?");
							}
						}
					}
				}
			}
			
		}
		
		
		return strBuf.toString();
	}
	
	/**
	 * set the time in china form (param must like :yyyyMMddHHmmss)
	 * @return
	 */
	public static String setTimeToCommonForm(String time) {
		if(time == null || time.trim().length()<4) {
			return time;
		}
		time = time.trim();
		StringBuffer strBuf = new StringBuffer("");
		if(time.length() > 3) {
			strBuf.append(time.substring(0, 4) + "");
			if(time.length() > 5) {
				strBuf.append(time.substring(4, 6) + "");
				if(time.length() > 7) {
					strBuf.append(time.substring(6, 8) + " ");
					if(time.length() > 9) {
						strBuf.append(time.substring(8, 10) + ":");
						if(time.length() > 11) {
							strBuf.append(time.substring(10, 12) + ":");
							if(time.length() > 13) {
								strBuf.append(time.substring(12, 14) + "");
							}
						}
					}
				}
			}
			
		}
		
		
		return strBuf.toString();
	}
	
	public static String getCNString(String disorderStr) throws UnsupportedEncodingException {
		return (new String(disorderStr.getBytes("iso-8859-1"), "utf-8")).trim();
	}

	public  static String[] split(String str, String regex) {
		String tempStr = str.trim();
		List list = new ArrayList();
		int indexOfRegex = tempStr.indexOf(regex);
		while(indexOfRegex >= 0) {
			String itemStr = tempStr.substring(0, indexOfRegex);
		    tempStr = tempStr.substring(indexOfRegex+regex.length());
		    list.add(itemStr.trim());
		    tempStr = tempStr.trim();
		    indexOfRegex = tempStr.indexOf(regex);
		}
		list.add(tempStr);
		if(list.size()<1) {
			return null;
		}
		String[] resultArray = new String[list.size()];
		Iterator its = list.iterator();
		int circleCount = 0;
		while(its.hasNext()) {
			String strItem = (String)its.next();
			resultArray[circleCount] = strItem;
			circleCount++;
		}
		return resultArray;
	}
	
	public static boolean isNull(String str)  {
		if(str == null || str.trim().length() < 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isSNull(String str)  {
		if(str == null || str.trim().length() < 1 || str.trim().equalsIgnoreCase("null")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static int lastIndexOf(String regex, String str) {
	    Pattern p = Pattern.compile(getRegex(regex));
	    Matcher m = p.matcher(str);
	    int lastIndex = -1;
	    int pos = 0;
	    while (m.find(pos)) {
	        pos = m.start() + 1;
	        lastIndex = m.start();
	    }	
	    return lastIndex;
	}
	
	public static int lastIndexOfRegex(String regex, String str) {
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(str);
	    int lastIndex = -1;
	    int pos = 0;
	    while (m.find(pos)) {
	        pos = m.start() + 1;
	        lastIndex = m.start();
	    }	
	    return lastIndex;
	}
	
	public static int indexOf(String regex, String str) {
	    Pattern p = Pattern.compile(getRegex(regex));
	    Matcher m = p.matcher(str);
	    while (m.find(0)) {
	    	return m.start();
	    }	
	    return -1;
	}
	
	public static String getRegex(String regex) {
		if(Str.isNull(regex)) {
			return "";
		}
		StringBuffer strBuf = new StringBuffer();
		for(int i=0; i<regex.length(); i++) {
			String m = "" + regex.charAt(i);
			if(m.equals(" ")) {
				strBuf.append("\\s");
				continue;
			}
			strBuf.append("[");
			strBuf.append( ( "" + regex.charAt(i) ).toUpperCase() );
			strBuf.append("|");
			strBuf.append( ( "" + regex.charAt(i) ).toLowerCase() );
			strBuf.append("]");
		}
		return strBuf.toString();
	}
	
	
	public static String formatNumber(long number) {
		 NumberFormat format = NumberFormat.getInstance(); 
		 return format.format(number);
	}
	
	public static String getFileName(String fileName) {
		fileName = fileName.replaceAll("\\\\", "/");

		int start = fileName.lastIndexOf("/") == -1 ? 0 : fileName
				.lastIndexOf("/") + 1;

		String filename = "undefined";
		if (fileName.lastIndexOf('.') != -1) {

			filename = fileName.substring(start);
		} else {
			filename = fileName.substring(start);
		}
		return filename;
	}
	
		static int dataLength = 256;
	
	    static private char[] encode(byte[] data)
	    {
	        char[] out = new char[((data.length + 2) / 3) * 4];

	        for (int i = 0, index = 0; i < data.length; i += 3, index += 4)
	        {
	                boolean quad = false;
	                boolean trip = false;
	                int val = (0xFF & (int) data[i]);
	                val <<= 8;
	                if ((i + 1) < data.length)
	                {
	                        val |= (0xFF & (int) data[i + 1]);
	                        trip = true;
	                }
	                val <<= 8;
	                if ((i + 2) < data.length)
	                {
	                        val |= (0xFF & (int) data[i + 2]);
	                        quad = true;
	                }
	                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
	                val >>= 6;
	                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
	                val >>= 6;
	                out[index + 1] = alphabet[val & 0x3F];
	                val >>= 6;
	                out[index + 0] = alphabet[val & 0x3F];
	        }
	        return out;
	    }
	    
	    static private byte[] decode(char[] data)
	    {
	      int len = ((data.length + 3) / 4) * 3;
	      if(data.length > 0 && data[data.length - 1] == '=')
	        --len;
	      if(data.length > 1 && data[data.length - 2] == '=')
	        --len;
	      byte[] out = new byte[len];
	      int shift = 0;
	      int accum = 0;
	      int index = 0;
	      for(int ix = 0; ix < data.length; ix++)
	      {
	        int value = codes[data[ix] & 0xFF];
	        if(value >= 0)
	        {
	          accum <<= 6;
	          shift += 6;
	          accum |= value;
	          if(shift >= 8)
	          {
	            shift -= 8;
	            out[index++] = (byte)((accum >> shift) & 0xff);
	          }
	        }
	      }
	      if(index != out.length)
	        throw new Error("miscalculated data length!");
	      return out;
	    }

	    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	    static private byte[] codes = new byte[dataLength];
	    static {
	            for (int i = 0; i < dataLength; i++)
	                    codes[i] = -1;
	            for (int i = 'A'; i <= 'Z'; i++)
	                    codes[i] = (byte) (i - 'A');
	            for (int i = 'a'; i <= 'z'; i++)
	                    codes[i] = (byte) (26 + i - 'a');
	            for (int i = '0'; i <= '9'; i++)
	                    codes[i] = (byte) (52 + i - '0');
	            codes['+'] = 62;
	            codes['/'] = 63;
	    }
	    
	    public static String encodeToBase64(String chString) {
	    	String strOut = new String(encode(chString.getBytes()));
	    	strOut = strOut.replaceAll("\\+", "\\*");
	    	strOut = strOut.replaceAll("\\/", "\\_");
	    	return strOut;
	    }
	    
	    
	    public static String decodeToNormalString(String base64String) {
	    	base64String = base64String.replaceAll("\\*", "\\+");
	    	base64String = base64String.replaceAll("\\_", "\\/");
	    	String strOut = new String(decode(base64String.toCharArray()));
	    	return strOut;
	    }
	    
	  //左补位
	  	public static String leftFix(int num, int tag,String placeholder) {
	  				String str = "";
	  				int len = ("" + num).length();
	  				for (int i = len; i < tag; ++i) {
	  					str = placeholder + str;
	  				}
	  				return str + num;
	  			}
	  		
	  		//右补位
	  	public static String rightFix(int num, int tag,String placeholder) {
	  			String str = "";
	  			int len = ("" + num).length();
	  			for (int i = len; i < tag; ++i) {
	  				str = placeholder + str;
	  			}
	  			return num + str;
	  		}
	    

		//kmp算法匹配子串坐标
		public static List<Integer> kmp(String s, String p){
			List<Integer> res = new ArrayList<Integer>();
			if(s == null || p == null || s.length()<p.length())
				return res;
			char[] sArr = s.toCharArray();
			char[] pArr = p.toCharArray();
			int[] next = getNext(p);
			int i=0, j=0;
			while(i<sArr.length){
				while(j<pArr.length){
					//加判断防止i溢出
					if(i>=sArr.length)
						break;
					//j为-1或匹配，则两数组往后遍历
					if(j==-1 || sArr[i]==pArr[j]){
						i++;
						j++;
					}else{
						//匹配失败，在next数组中找到应该移动到的位置
						j = next[j];
					}
				}
				if(j==pArr.length){
					res.add(i-j);
					j=0;
				}
			}
			return res;
		}
		
		//根据模式串计算next数组, next数组记录如果当前j不匹配应该跳到哪个位置
		private static  int[] getNext(String p){
			char[] pArr = p.toCharArray();
			int[] next = new int[pArr.length];
			//如果第一个都不匹配，标记为-1
			next[0] = -1;
			//k表示j不匹配时的下标值
			int j=0;
			int k=-1;
			while(j<pArr.length-1){
				if(k==-1 || pArr[j]==pArr[k]){
					next[++j] = ++k;
				}else{
					k = next[k];
				}
			}
			return next;
		}

	    
}
