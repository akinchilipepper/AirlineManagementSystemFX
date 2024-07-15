package com.flaaiairlines.system;

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
			Image image = new Image("/com/flaaiairlines/media/appIconPlane.png");
			Parent root = FXMLLoader.load(getClass().getResource("/com/flaaiairlines/view/LoginPaneView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.getIcons().add(image);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}