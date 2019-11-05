package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import nt.com.config.Config;
/**
 * �·���ʾ�ı�
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
		
		//���뷽ʽ
		
		Label codelabel =(Label)(this.getItems().get(0));
		String encode =Config.getEncode();
		if(encode.equals("GBK")) {
			codelabel.setText("���뷽ʽ��GBK");
		}else if(encode.equals("UTF-8")){
			codelabel.setText("���뷽ʽ��UTF-8");
		}
		//ͨѶ��ʽ
		Label connectLabel =(Label)(this.getItems().get(2));
		String connect = Config.getConnect();
		if(connect.equals("sync")) {
			connectLabel.setText("ͨѶ��ʽ��ͬ������");
		}else if(connect.equals("asyn")){
			connectLabel.setText("ͨѶ��ʽ���첽����");
		}
		//Э�鷽ʽ
		Label protocolLabel =(Label)(this.getItems().get(4));
		String protocol = Config.getProtocol();
		if(protocol.equals("tcp")) {
			protocolLabel.setText("����Э�飺TCP");
		}else if(protocol.equals("udp")){
			protocolLabel.setText("����Э�飺UDP");
		}else if(protocol.equals("http")){
			protocolLabel.setText("����Э�飺HTTP");
		}
		//���ܷ�ʽ
		Label macLabel =(Label)(this.getItems().get(6));
		String mac = Config.getMac();
		if(mac.equals("nomac")) {
			macLabel.setText("�����㷨������");
		}else if(mac.equals("3des")){
			macLabel.setText("�����㷨��3DES");
		}else if(mac.equals("sm4")){
			macLabel.setText("�����㷨��SM4");
		}
		//������ʽ
		Label multiLabel =(Label)(this.getItems().get(8));
		String multi = Config.getMulti();
		if(multi.equals("single")) {
			multiLabel.setText("�������ͣ�����������");
		}else if(multi.equals("multiN")){
			multiLabel.setText("�������ͣ�����������");
		}else if(multi.equals("multiB")){
			multiLabel.setText("�������ͣ�����������");
		}
		//���ķ�ʽ
		Label msgLabel =(Label)(this.getItems().get(10));
		String msg = Config.getMsg();
		if(msg.equals("xml")) {
			msgLabel.setText("�������ͣ�XML");
		}else if(msg.equals("json")){
			msgLabel.setText("�������ͣ�JSON");
		}else if(msg.equals("txt")){
			msgLabel.setText("�������ͣ��ı�");
		}
		//�ű���ʽ
		Label scriptLabel =(Label)(this.getItems().get(12));
		String script = Config.getScript();
		if(script.equals("disable")) {
			scriptLabel.setText("�ű�������");
		}else if(script.equals("enable")){
			scriptLabel.setText("�ű�������");
		}
		
	}
}
