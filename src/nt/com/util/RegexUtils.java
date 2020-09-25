package nt.com.util;

import java.util.regex.Pattern;

/**
 * ������ʽ��ع��� 
 * 
 * @author kege
 */
public class RegexUtils {
	
	
	//ƥ���Զ���������ʽ
	public static boolean RegexMatche(String target,String reg) {
		Pattern pattern = Pattern.compile(reg);
		return  pattern.matcher(target).matches();
	}
	
	//ƥ��Ԥ�����������ʽ -- java����
	public static boolean ClassNameMatche(String targer) {
		Pattern pattern = Pattern.compile("[a-zA-Z]+[0-9a-zA-Z_]*(\\\\.[a-zA-Z]+[0-9a-zA-Z_]*)*");
		return pattern.matcher(targer).matches();
	}
	
	
	public static void main(String[] args) {
		System.out.println(RegexUtils.RegexMatche("15", "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$"));
														  
	}
}
