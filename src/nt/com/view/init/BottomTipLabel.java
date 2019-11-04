package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToolBar;
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
	}
}
