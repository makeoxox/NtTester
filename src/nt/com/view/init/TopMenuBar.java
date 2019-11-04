package nt.com.view.init;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
/**
 * …œ∑Ω≤Àµ•¿∏
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
	}
}
