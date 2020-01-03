package nt.com.view.init;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToolBar;

/**
 * 上方工具栏
 * 
 * @author kege
 *
 */
public class TopToolBar extends ToolBar {

	public TopToolBar() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/TopToolBar.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
