package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nt.com.global.Config;
import nt.com.view.init.MainView;

/**
 * 上部菜单栏事件控制器
 * @author kege
 *
 */
public class TopMenuBarController {

	@FXML
	protected Menu codeMenu;

	@FXML
	protected RadioMenuItem gbkMenuItem;

	@FXML
	protected ToggleGroup CodeGroup;

	@FXML
	protected RadioMenuItem utfMenuItem;

	@FXML
	protected Menu connectMenu;

	@FXML
	protected RadioMenuItem syncMenuItem;

	@FXML
	protected ToggleGroup AsynGroup;

	@FXML
	protected RadioMenuItem asynMenuItem;

	@FXML
	protected Menu protocolMenu;

	@FXML
	protected RadioMenuItem tcpMenuItem;

	@FXML
	protected ToggleGroup ProtocolGroup;

	@FXML
	protected RadioMenuItem ucpMenuItem;

	@FXML
	protected RadioMenuItem httpMenuItem;


	@FXML
	protected Menu multiMenu;

	@FXML
	protected RadioMenuItem singleMenuItem;

	@FXML
	protected ToggleGroup MultiGroup;

	@FXML
	protected RadioMenuItem multiNMenuItem;

	@FXML
	protected RadioMenuItem multiMenuItem;

	@FXML
	protected Menu MsgMenu;

	@FXML
	protected RadioMenuItem xmlMenuItem;
	
	@FXML
	protected RadioMenuItem jsonMenuItem;

	@FXML
	protected ToggleGroup MsgGroup;

	@FXML
	protected RadioMenuItem txtMenuItem;

	@FXML
	protected Menu scriptMenu;

	@FXML
	protected RadioMenuItem disMenuItem;

	@FXML
	protected ToggleGroup ScriptGroup;

	@FXML
	protected RadioMenuItem enMenuItem;
	
	/**
	 * 选择编码类型
	 */
	@FXML 
	void codeSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)CodeGroup.getSelectedToggle();
		String code = rmi.getText();
		Label codelabel =(Label)(MainView.parent.lookup("#codeLabel"));
		if (code.equals("GBK")) {
			codelabel.setText("编码方式：GBK");
			Config.setEncode("GBK");
		} else if (code.equals("UTF-8")) {
			codelabel.setText("编码方式：UTF-8");
			Config.setEncode("UTF-8");
		}
	}
	
	/**
	 * 选择连接方式
	 */
	@FXML
	void connectSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)AsynGroup.getSelectedToggle();
		String connect = rmi.getText();
		Label connectLabel =(Label)(MainView.parent.lookup("#connectLabel"));
		if (connect.equals("同步连接")) {
			connectLabel.setText("通讯方式：同步连接");
			Config.setConnect("sync");
		} else if (connect.equals("异步连接")) {
			connectLabel.setText("通讯方式：异步连接");
			Config.setConnect("asyn");
		}
	}
	
	/**
	 * 选择协议类型
	 */
	@FXML
	void protocolSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)ProtocolGroup.getSelectedToggle();
		String protocol = rmi.getText();
		Label protocolLabel =(Label)(MainView.parent.lookup("#protocolLabel"));
		if (protocol.equals("TCP")) {
			protocolLabel.setText("请求协议：TCP");
			Config.setProtocol("tcp");
		} else if (protocol.equals("UDP")) {
			protocolLabel.setText("请求协议：UDP");
			Config.setProtocol("udp");
		} else if (protocol.equals("HTTP")) {
			protocolLabel.setText("请求协议：HTTP");
			Config.setProtocol("http");
		}
	}
	
	/**
	 * 选择报文类型
	 */
	@FXML
	void msgSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)MsgGroup.getSelectedToggle();
		String msg = rmi.getText();
		Label msgLabel =(Label)(MainView.parent.lookup("#msgLabel"));
		if (msg.equals("XML")) {
			msgLabel.setText("报文类型：XML");
			Config.setMsg("xml");
		} else if (msg.equals("文本")) {
			msgLabel.setText("报文类型：文本");
			Config.setMsg("txt");
		}else if (msg.equals("JSON")) {
			msgLabel.setText("报文类型：JSON");
			Config.setMsg("json");
		}
	}
	
	/**
	 * 选择并发类型
	 */
	@FXML
	void multiSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)MultiGroup.getSelectedToggle();
		String multi = rmi.getText();
		Label multiLabel =(Label)(MainView.parent.lookup("#multiLabel"));
		if (multi.equals("单发无阻塞")) {
			multiLabel.setText("并发类型：单发无阻塞");
			Config.setMulti("single");
		} else if (multi.equals("并发无阻塞")) {
			multiLabel.setText("并发类型：并发无阻塞");
			Config.setMulti("multiN");
		} else if (multi.equals("并发有阻塞")) {
			multiLabel.setText("并发类型：并发有阻塞");
			Config.setMulti("multiB");
		}
	}
	
	
	/**
	 * 选择是否启用脚本
	 */
	@FXML
	void scriptSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)ScriptGroup.getSelectedToggle();
		String script = rmi.getText();
		Label scriptLabel =(Label)(MainView.parent.lookup("#scriptLabel"));
		if (script.equals("启用")) {
			scriptLabel.setText("脚本：启用");
			Config.setScript("enable");
		} else if (script.equals("禁用")) {
			scriptLabel.setText("脚本：禁用");
			Config.setScript("disable");
		}
	}


}
