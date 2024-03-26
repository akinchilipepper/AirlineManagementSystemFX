package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainViewController implements Initializable{
	
	@FXML private AnchorPane topBar;
	@FXML private Button closeButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void onCloseButtonClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
