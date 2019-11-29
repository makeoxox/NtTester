package nt.com.script.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphController {
	
	 @FXML
    private VBox graphview;

    @FXML
    private ImageView image;

    @FXML
    private Button cancelbtn;


    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage)graphview.getScene().getWindow();
    	stage.close();
    }
}
