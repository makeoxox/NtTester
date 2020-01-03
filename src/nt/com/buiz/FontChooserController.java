package nt.com.buiz;

import org.fxmisc.flowless.VirtualizedScrollPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import nt.com.global.Config;
import nt.com.model.FontFamilyModel;
import nt.com.model.FontSizeModel;
import nt.com.view.init.FontChooser;
import nt.com.view.init.MainView;
import nt.com.view.init.RichEditTextArea;
/**
 * 字体选择器事件控制器
 * @author kege
 *
 */
public class FontChooserController {

	@FXML
	private FontChooser fontchooser;
	
    @FXML
    private ListView<FontFamilyModel> family;

    @FXML
    private ListView<FontSizeModel> size;
    
    @FXML
    private Label areatypelabel;
    
    @FXML
    private Button okbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    void cancel(ActionEvent event) {
    	((Stage) fontchooser.getScene().getWindow()).close();
    }

	@FXML
    void choose(ActionEvent event) {
    	FontFamilyModel ffm=family.getSelectionModel().getSelectedItem();
    	FontSizeModel  fsm = size.getSelectionModel().getSelectedItem();
    	String areatype= areatypelabel.getText();
    	if(areatype.equals("Edit")){
    		Config.setEditFont(ffm.getFamily(), fsm.getSize());
    		String fontFamily = "-fx-font-family:" + Config.getEditFont().getFamily() + ";";
    		String fontSize = "-fx-font-size:" + Config.getEditFont().getSize() + ";";
    		TabPane metp = (TabPane) MainView.parent.lookup("#edittabpane");
    		ObservableList<Tab> edittabList = metp.getTabs();
    		for (int j = 0; j < edittabList.size(); j++) {
    			Tab edittab = edittabList.get(j);
    			@SuppressWarnings("unchecked")
				VirtualizedScrollPane<RichEditTextArea> vp = (VirtualizedScrollPane<RichEditTextArea>) edittab.getContent();
    			vp.getContent().setStyle(fontFamily + fontSize);
    		}
    	}else {
    		Config.setConsoleFont(ffm.getFamily(), fsm.getSize());
    		String fontFamily = "-fx-font-family:" + Config.getConsoleFont().getFamily() + ";";
    		String fontSize = "-fx-font-size:" + Config.getConsoleFont().getSize() + ";";
    		TabPane mctp = (TabPane) MainView.parent.lookup("#consoletabpane");
    		ObservableList<Tab> edittabList = mctp.getTabs();
    		for (int j = 0; j < edittabList.size(); j++) {
    			Tab consoletab = edittabList.get(j);
    			consoletab.getContent().setStyle(fontFamily + fontSize);
    		}
    	}
    	((Stage) fontchooser.getScene().getWindow()).close();
    }
}
