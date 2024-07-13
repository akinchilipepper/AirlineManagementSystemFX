package system;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {	
	@Override
	public void start(Stage stage) {
		try {
			Image image = new Image("/media/appIconPlane.png");
			Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPaneView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.getIcons().add(image);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		new Thread(() -> {
			try {
				FlightManagement.checkAndUpdateFlights();
				Thread.sleep(60000);
			} catch(InterruptedException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex, null);
			}
		}).start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}