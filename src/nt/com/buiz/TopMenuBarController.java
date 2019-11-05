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
	private Menu codeMenu;

	@FXML
	private RadioMenuItem gbkMenuItem;

	@FXML
	private ToggleGroup CodeGroup;

	@FXML
	private RadioMenuItem utfMenuItem;

	@FXML
	private Menu connectMenu;

	@FXML
	private RadioMenuItem syncMenuItem;

	@FXML
	private ToggleGroup AsynGroup;

	@FXML
	private RadioMenuItem asynMenuItem;

	@FXML
	private Menu protocolMenu;

	@FXML
	private RadioMenuItem tcpMenuItem;

	@FXML
	private ToggleGroup ProtocolGroup;

	@FXML
	private RadioMenuItem ucpMenuItem;

	@FXML
	private RadioMenuItem httpMenuItem;

	@FXML
	private Menu macMenu;

	@FXML
	private RadioMenuItem nomacMenuItem;

	@FXML
	private ToggleGroup MacGroup;

	@FXML
	private RadioMenuItem desMenuItem;

	@FXML
	private RadioMenuItem mdMenuItem;

	@FXML
	private Menu multiMenu;

	@FXML
	private RadioMenuItem singleMenuItem;

	@FXML
	private ToggleGroup MultiGroup;

	@FXML
	private RadioMenuItem multiNMenuItem;

	@FXML
	private RadioMenuItem multiMenuItem;

	@FXML
	private Menu MsgMenu;

	@FXML
	private RadioMenuItem xmlMenuItem;
	
	@FXML
	private RadioMenuItem jsonMenuItem;

	@FXML
	private ToggleGroup MsgGroup;

	@FXML
	private RadioMenuItem txtMenuItem;

	@FXML
	private Menu scriptMenu;

	@FXML
	private RadioMenuItem disMenuItem;

	@FXML
	private ToggleGroup ScriptGroup;

	@FXML
	private RadioMenuItem enMenuItem;
	
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
	 * ѡ���������
	 */
	@FXML
	void macSelected(ActionEvent event) {
		RadioMenuItem rmi = (RadioMenuItem)MacGroup.getSelectedToggle();
		String mac = rmi.getText();
		Label macLabel =(Label)(MainView.parent.lookup("#macLabel"));
		if (mac.equals("����")) {
			macLabel.setText("�����㷨������");
			Config.setMac("nomac");
		} else if (mac.equals("3DES")) {
			macLabel.setText("�����㷨��3DES");
			Config.setMac("3des");
		} else if (mac.equals("SM4")) {
			macLabel.setText("�����㷨��SM4");
			Config.setMac("sm4");
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
