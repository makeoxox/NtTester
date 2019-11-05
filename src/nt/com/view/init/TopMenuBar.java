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
 * �Ϸ��˵���
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
		//���������ļ���Ĭ��ѡ�еĲ˵�
		ObservableList<Menu> list =this.getMenus();
		//���뷽ʽ
		ObservableList<MenuItem> encodeItems = list.get(0).getItems();
		String encode =Config.getEncode();
		if(encode.equals("GBK")) {
			((RadioMenuItem) (encodeItems.get(0))).setSelected(true);
		}else if(encode.equals("UTF-8")){
			((RadioMenuItem) (encodeItems.get(1))).setSelected(true);
		}
		//ͨѶ��ʽ
		ObservableList<MenuItem> connectItems = list.get(1).getItems();
		String connect = Config.getConnect();
		if(connect.equals("sync")) {
			((RadioMenuItem) (connectItems.get(0))).setSelected(true);
		}else if(connect.equals("asyn")){
			((RadioMenuItem) (connectItems.get(1))).setSelected(true);
		}
		//Э�鷽ʽ
		ObservableList<MenuItem> protocolItems = list.get(2).getItems();
		String protocol = Config.getProtocol();
		if(protocol.equals("tcp")) {
			((RadioMenuItem) (protocolItems.get(0))).setSelected(true);
		}else if(protocol.equals("udp")){
			((RadioMenuItem) (protocolItems.get(1))).setSelected(true);
		}else if(protocol.equals("http")){
			((RadioMenuItem) (protocolItems.get(2))).setSelected(true);
		}
		//���ܷ�ʽ
		ObservableList<MenuItem> macItems = list.get(3).getItems();
		String mac = Config.getMac();
		if(mac.equals("nomac")) {
			((RadioMenuItem) (macItems.get(0))).setSelected(true);
		}else if(mac.equals("3des")){
			((RadioMenuItem) (macItems.get(1))).setSelected(true);
		}else if(mac.equals("sm4")){
			((RadioMenuItem) (macItems.get(2))).setSelected(true);
		}
		//������ʽ
		ObservableList<MenuItem> multiItems = list.get(4).getItems();
		String multi = Config.getMulti();
		if(multi.equals("single")) {
			((RadioMenuItem) (multiItems.get(0))).setSelected(true);
		}else if(multi.equals("multiN")){
			((RadioMenuItem) (multiItems.get(1))).setSelected(true);
		}else if(multi.equals("multiB")){
			((RadioMenuItem) (multiItems.get(2))).setSelected(true);
		}
		//���ķ�ʽ
		ObservableList<MenuItem> msgItems = list.get(5).getItems();
		String msg = Config.getMsg();
		if(msg.equals("xml")) {
			((RadioMenuItem) (msgItems.get(0))).setSelected(true);
		}else if(msg.equals("json")){
			((RadioMenuItem) (msgItems.get(1))).setSelected(true);
		}else if(msg.equals("txt")){
			((RadioMenuItem) (msgItems.get(2))).setSelected(true);
		}
		//�ű���ʽ
		ObservableList<MenuItem> scriptItems = list.get(6).getItems();
		String script = Config.getScript();
		if(script.equals("disable")) {
			((RadioMenuItem) (scriptItems.get(0))).setSelected(true);
		}else if(script.equals("enable")){
			((RadioMenuItem) (scriptItems.get(1))).setSelected(true);
		}
		
	}
}
