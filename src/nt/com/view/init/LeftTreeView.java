package nt.com.view.init;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import nt.com.model.FileTreeModel;

/**
 * ×ó²¿ÎÄ¼þÊ÷
 * @author kege
 *
 */
public class LeftTreeView extends TreeView<FileTreeModel> {

	public LeftTreeView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/LeftTreeView.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		TreeItem<FileTreeModel> root = new TreeItem<FileTreeModel>();
		root.setExpanded(true);
		this.setRoot((TreeItem<FileTreeModel>) root);
		
		
	}

	
}
