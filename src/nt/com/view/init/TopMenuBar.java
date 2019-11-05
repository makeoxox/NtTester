package nt.com.view.init;

import java.io.IOException;


import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import nt.com.config.Config;
/**
 * 上方菜单栏
 * @author kege
 *
 */
public class TopMenuBar extends MenuBar {

	public TopMenuBar() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/TopMenuBar.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		//加载配置文件中默认选中的菜单
		ObservableList<Menu> list =this.getMenus();
		//编码方式
		ObservableList<MenuItem> encodeItems = list.get(0).getItems();
		String encode =Config.getEncode();
		if(encode.equals("GBK")) {
			((RadioMenuItem) (encodeItems.get(0))).setSelected(true);
		}else if(encode.equals("UTF-8")){
			((RadioMenuItem) (encodeItems.get(1))).setSelected(true);
		}
		//通讯方式
		ObservableList<MenuItem> connectItems = list.get(1).getItems();
		String connect = Config.getConnect();
		if(connect.equals("sync")) {
			((RadioMenuItem) (connectItems.get(0))).setSelected(true);
		}else if(connect.equals("asyn")){
			((RadioMenuItem) (connectItems.get(1))).setSelected(true);
		}
		//协议方式
		ObservableList<MenuItem> protocolItems = list.get(2).getItems();
		String protocol = Config.getProtocol();
		if(protocol.equals("tcp")) {
			((RadioMenuItem) (protocolItems.get(0))).setSelected(true);
		}else if(protocol.equals("udp")){
			((RadioMenuItem) (protocolItems.get(1))).setSelected(true);
		}else if(protocol.equals("http")){
			((RadioMenuItem) (protocolItems.get(2))).setSelected(true);
		}
		//加密方式
		ObservableList<MenuItem> macItems = list.get(3).getItems();
		String mac = Config.getMac();
		if(mac.equals("nomac")) {
			((RadioMenuItem) (macItems.get(0))).setSelected(true);
		}else if(mac.equals("3des")){
			((RadioMenuItem) (macItems.get(1))).setSelected(true);
		}else if(mac.equals("sm4")){
			((RadioMenuItem) (macItems.get(2))).setSelected(true);
		}
		//并发方式
		ObservableList<MenuItem> multiItems = list.get(4).getItems();
		String multi = Config.getMulti();
		if(multi.equals("single")) {
			((RadioMenuItem) (multiItems.get(0))).setSelected(true);
		}else if(multi.equals("multiN")){
			((RadioMenuItem) (multiItems.get(1))).setSelected(true);
		}else if(multi.equals("multiB")){
			((RadioMenuItem) (multiItems.get(2))).setSelected(true);
		}
		//报文方式
		ObservableList<MenuItem> msgItems = list.get(5).getItems();
		String msg = Config.getMsg();
		if(msg.equals("xml")) {
			((RadioMenuItem) (msgItems.get(0))).setSelected(true);
		}else if(msg.equals("json")){
			((RadioMenuItem) (msgItems.get(1))).setSelected(true);
		}else if(msg.equals("txt")){
			((RadioMenuItem) (msgItems.get(2))).setSelected(true);
		}
		//脚本方式
		ObservableList<MenuItem> scriptItems = list.get(6).getItems();
		String script = Config.getScript();
		if(script.equals("disable")) {
			((RadioMenuItem) (scriptItems.get(0))).setSelected(true);
		}else if(script.equals("enable")){
			((RadioMenuItem) (scriptItems.get(1))).setSelected(true);
		}
		
	}
}
