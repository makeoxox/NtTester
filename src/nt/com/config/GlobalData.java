package nt.com.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;

import net.sf.json.JSON;
import nt.com.model.TxtFieldModel;

/**
 * 程序全局数据
 * 
 * @author kege
 *
 */
public class GlobalData {
	//xml递增节点集合
	public static Map<String,List<Node>> xmlIncrementNodeMap = new HashMap<>();
	//xml赋值节点集合
	public static Map<String,List<Node>> xmlAssignNodeMap = new HashMap<>();
	//json递增子对象集合
	public static Map<String,List<JSON>> jsonIncrementNodeMap = new HashMap<>();
	//json赋值子对象集合
	public static Map<String,List<JSON>> jsonAssignNodeMap = new HashMap<>();
	//txt递增子对象集合
	public static Map<String,List<TxtFieldModel>> txtIncrementNodeMap = new HashMap<>();
	//txt赋值子对象集合
	public static Map<String,List<TxtFieldModel>> txtAssignNodeMap = new HashMap<>();
}
