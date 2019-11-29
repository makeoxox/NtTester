package nt.com.script.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableController {
	
	@FXML
    private VBox tableview;

    @FXML
    private TableView<?> table;

    @FXML
    private Button cancelbtn;

    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage)tableview.getScene().getWindow();
    	stage.close();
    }
}
