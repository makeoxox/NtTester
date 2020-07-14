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
 * 程序全局数据
 * 
 * @author kege
 *
 */
public class GlobalData {
	
	//当前关键字所在range集合
	public static List<Map<String,RangeListModel>> rangeKeyList = new ArrayList<>();
	//当前编辑页range集合
	public static RangeListModel currRLM;
	//xml递增节点集合
	public static Map<String,List<XmlElementModel>> xmlIncrementNodeMap = new HashMap<>();
	//xml赋值节点集合
	public static Map<String,List<XmlElementModel>> xmlAssignNodeMap = new HashMap<>();
	//json递增子对象集合
	public static Map<String,List<JSON>> jsonIncrementNodeMap = new HashMap<>();
	//json赋值子对象集合
	public static Map<String,List<JSON>> jsonAssignNodeMap = new HashMap<>();
	//txt递增子对象集合
	public static Map<String,List<TxtFieldModel>> txtIncrementNodeMap = new HashMap<>();
	//txt赋值子对象集合
	public static Map<String,List<TxtFieldModel>> txtAssignNodeMap = new HashMap<>();
	
}
