package nt.com.view.init;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import nt.com.buiz.LeftTreeViewController;
import nt.com.global.Config;
import nt.com.plugin.PluginModule;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/**
 * ���������
 * 
 * @author kege
 *
 */
public class MainView extends Application {

	public static Scene scene;
	public static BorderPane parent;

	static {
		try {
			parent = FXMLLoader.load(MainView.class.getResource("/nt/com/view/fxml/MainView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {
		scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("NT�ӿڵ����� v" + Config.getVersion() + " @Author - kege");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/title.png")));
		stage.show();
		// �������ȡ��ɺ������Ŀ��
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				LeftTreeViewController.LeftTreeLoad();
			}
		});
		// �������ȡ��ɺ���ز��
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				PluginModule.plug();
			}
		});
	}

}
