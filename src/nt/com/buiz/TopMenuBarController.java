package nt.com.buiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nt.com.config.Config;
import nt.com.view.init.MainView;

/**
 * �ϲ��˵����¼�������
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
	 * ѡ���������
	 */
	@FXML 
	void codeSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)CodeGroup.getSelectedToggle();
		String code = rmi.getText();
		Label codelabel =(Label)(MainView.parent.lookup("#codeLabel"));
		if (code.equals("GBK")) {
			codelabel.setText("���뷽ʽ��GBK");
			Config.setEncode("GBK");
		} else if (code.equals("UTF-8")) {
			codelabel.setText("���뷽ʽ��UTF-8");
			Config.setEncode("UTF-8");
		}
	}
	
	/**
	 * ѡ�����ӷ�ʽ
	 */
	@FXML
	void connectSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)AsynGroup.getSelectedToggle();
		String connect = rmi.getText();
		Label connectLabel =(Label)(MainView.parent.lookup("#connectLabel"));
		if (connect.equals("ͬ������")) {
			connectLabel.setText("ͨѶ��ʽ��ͬ������");
			Config.setConnect("sync");
		} else if (connect.equals("�첽����")) {
			connectLabel.setText("ͨѶ��ʽ���첽����");
			Config.setConnect("asyn");
		}
	}
	
	/**
	 * ѡ��Э������
	 */
	@FXML
	void protocolSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)ProtocolGroup.getSelectedToggle();
		String protocol = rmi.getText();
		Label protocolLabel =(Label)(MainView.parent.lookup("#protocolLabel"));
		if (protocol.equals("TCP")) {
			protocolLabel.setText("����Э�飺TCP");
			Config.setProtocol("tcp");
		} else if (protocol.equals("UDP")) {
			protocolLabel.setText("����Э�飺UDP");
			Config.setProtocol("udp");
		} else if (protocol.equals("HTTP")) {
			protocolLabel.setText("����Э�飺HTTP");
			Config.setProtocol("http");
		}
	}
	
	/**
	 * ѡ��������
	 */
	@FXML
	void msgSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)MsgGroup.getSelectedToggle();
		String msg = rmi.getText();
		Label msgLabel =(Label)(MainView.parent.lookup("#msgLabel"));
		if (msg.equals("XML")) {
			msgLabel.setText("�������ͣ�XML");
			Config.setMsg("xml");
		} else if (msg.equals("�ı�")) {
			msgLabel.setText("�������ͣ��ı�");
			Config.setMsg("txt");
		}else if (msg.equals("JSON")) {
			msgLabel.setText("�������ͣ�JSON");
			Config.setMsg("json");
		}
	}
	
	/**
	 * ѡ�񲢷�����
	 */
	@FXML
	void multiSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)MultiGroup.getSelectedToggle();
		String multi = rmi.getText();
		Label multiLabel =(Label)(MainView.parent.lookup("#multiLabel"));
		if (multi.equals("����������")) {
			multiLabel.setText("�������ͣ�����������");
			Config.setMulti("single");
		} else if (multi.equals("����������")) {
			multiLabel.setText("�������ͣ�����������");
			Config.setMulti("multiN");
		} else if (multi.equals("����������")) {
			multiLabel.setText("�������ͣ�����������");
			Config.setMulti("multiB");
		}
	}
	
	
	/**
	 * ѡ���Ƿ����ýű�
	 */
	@FXML
	void scriptSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)ScriptGroup.getSelectedToggle();
		String script = rmi.getText();
		Label scriptLabel =(Label)(MainView.parent.lookup("#scriptLabel"));
		if (script.equals("����")) {
			scriptLabel.setText("�ű�������");
			Config.setScript("enable");
		} else if (script.equals("����")) {
			scriptLabel.setText("�ű�������");
			Config.setScript("disable");
		}
	}


}
