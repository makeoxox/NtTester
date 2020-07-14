package nt.com.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import nt.com.model.RangeListModel;
import nt.com.model.TxtFieldModel;
import nt.com.model.XmlElementModel;

/**
 * ����ȫ������
 * 
 * @author kege
 *
 */
public class GlobalData {
	
	//��ǰ�ؼ�������range����
	public static List<Map<String,RangeListModel>> rangeKeyList = new ArrayList<>();
	//��ǰ�༭ҳrange����
	public static RangeListModel currRLM;
	//xml�����ڵ㼯��
	public static Map<String,List<XmlElementModel>> xmlIncrementNodeMap = new HashMap<>();
	//xml��ֵ�ڵ㼯��
	public static Map<String,List<XmlElementModel>> xmlAssignNodeMap = new HashMap<>();
	//json�����Ӷ��󼯺�
	public static Map<String,List<JSON>> jsonIncrementNodeMap = new HashMap<>();
	//json��ֵ�Ӷ��󼯺�
	public static Map<String,List<JSON>> jsonAssignNodeMap = new HashMap<>();
	//txt�����Ӷ��󼯺�
	public static Map<String,List<TxtFieldModel>> txtIncrementNodeMap = new HashMap<>();
	//txt��ֵ�Ӷ��󼯺�
	public static Map<String,List<TxtFieldModel>> txtAssignNodeMap = new HashMap<>();
	
}
