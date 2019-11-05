package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import nt.com.config.Config;
/**
 * 下方提示文本
 * @author kege
 *
 */
public class BottomTipLabel extends ToolBar {

	public BottomTipLabel() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/BottomTipLabel.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		//编码方式
		
		Label codelabel =(Label)(this.getItems().get(0));
		String encode =Config.getEncode();
		if(encode.equals("GBK")) {
			codelabel.setText("编码方式：GBK");
		}else if(encode.equals("UTF-8")){
			codelabel.setText("编码方式：UTF-8");
		}
		//通讯方式
		Label connectLabel =(Label)(this.getItems().get(2));
		String connect = Config.getConnect();
		if(connect.equals("sync")) {
			connectLabel.setText("通讯方式：同步连接");
		}else if(connect.equals("asyn")){
			connectLabel.setText("通讯方式：异步连接");
		}
		//协议方式
		Label protocolLabel =(Label)(this.getItems().get(4));
		String protocol = Config.getProtocol();
		if(protocol.equals("tcp")) {
			protocolLabel.setText("请求协议：TCP");
		}else if(protocol.equals("udp")){
			protocolLabel.setText("请求协议：UDP");
		}else if(protocol.equals("http")){
			protocolLabel.setText("请求协议：HTTP");
		}
		//加密方式
		Label macLabel =(Label)(this.getItems().get(6));
		String mac = Config.getMac();
		if(mac.equals("nomac")) {
			macLabel.setText("加密算法：无密");
		}else if(mac.equals("3des")){
			macLabel.setText("加密算法：3DES");
		}else if(mac.equals("sm4")){
			macLabel.setText("加密算法：SM4");
		}
		//并发方式
		Label multiLabel =(Label)(this.getItems().get(8));
		String multi = Config.getMulti();
		if(multi.equals("single")) {
			multiLabel.setText("并发类型：单发无阻塞");
		}else if(multi.equals("multiN")){
			multiLabel.setText("并发类型：并发无阻塞");
		}else if(multi.equals("multiB")){
			multiLabel.setText("并发类型：并发有阻塞");
		}
		//报文方式
		Label msgLabel =(Label)(this.getItems().get(10));
		String msg = Config.getMsg();
		if(msg.equals("xml")) {
			msgLabel.setText("报文类型：XML");
		}else if(msg.equals("json")){
			msgLabel.setText("报文类型：JSON");
		}else if(msg.equals("txt")){
			msgLabel.setText("报文类型：文本");
		}
		//脚本方式
		Label scriptLabel =(Label)(this.getItems().get(12));
		String script = Config.getScript();
		if(script.equals("disable")) {
			scriptLabel.setText("脚本：禁用");
		}else if(script.equals("enable")){
			scriptLabel.setText("脚本：启用");
		}
		
	}
}
