package nt.com.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;

import net.sf.json.JSON;

/**
 * ����ȫ������
 * 
 * @author kege
 *
 */
public class GlobalData {
	//xml�����ڵ㼯��
	public static Map<String,List<Node>> xmlIncrementNodeMap = new HashMap<>();
	//xml��ֵ�ڵ㼯��
	public static Map<String,List<Node>> xmlAssignNodeMap = new HashMap<>();
	//json�����Ӷ��󼯺�
	public static Map<String,List<JSON>> jsonIncrementNodeMap = new HashMap<>();
	//json��ֵ�Ӷ��󼯺�
	public static Map<String,List<JSON>> jsonAssignNodeMap = new HashMap<>();
	
}
