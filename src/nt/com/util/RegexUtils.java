package nt.com.util;

import java.util.regex.Pattern;

/**
 * 正则表达式相关工具 
 * 
 * @author kege
 */
public class RegexUtils {
	
	
	//匹配自定义正则表达式
	public static boolean RegexMatche(String target,String reg) {
		Pattern pattern = Pattern.compile(reg);
		return  pattern.matcher(target).matches();
	}
	
	//匹配预定义的正则表达式 -- java类名
	public static boolean ClassNameMatche(String targer) {
		Pattern pattern = Pattern.compile("[a-zA-Z]+[0-9a-zA-Z_]*(\\\\.[a-zA-Z]+[0-9a-zA-Z_]*)*");
		return pattern.matcher(targer).matches();
	}
	
	
	public static void main(String[] args) {
		System.out.println(RegexUtils.RegexMatche("15", "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$"));
														  
	}
}
