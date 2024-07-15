package com.flaaiairlines.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginPaneViewController implements Initializable {
	@FXML private AnchorPane mainPane;
	@FXML private JFXButton confirmButton;
    @FXML private JFXTextField txtUsername;
    @FXML private JFXPasswordField txtPassword;
    @FXML private Button closeButton;
    @FXML private Label mesajLabel;
    @FXML private Label denemeLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private ImageView planeImgView;
    @FXML private ImageView flaaiImgView;
    @FXML private ImageView closeImgView;
    @FXML private Image appIcon;
    @FXML private FontAwesomeIconView excIcon;
    private int deneme = 3;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	appIcon = new Image("/com/flaaiairlines/media/appIconPlane.png");
    	Image flaaiImg = new Image("/com/flaaiairlines/media/flaai.png");
    	flaaiImgView.setImage(flaaiImg);
    	Image planeImg = new Image("/com/flaaiairlines/media/planeAnimation.gif");
    	planeImgView.setImage(planeImg);
    	Image closeImg = new Image("/com/flaaiairlines/media/blackcross.png");
    	closeImgView.setImage(closeImg);
    	clock();
    }

    public void onConfirmButtonAction() {
        try {
        	if(txtUsername.getText().equals("") && txtPassword.getText().equals("")) {
        		txtUsername.setUnFocusColor(Color.RED);
                txtPassword.setUnFocusColor(Color.RED);
        		mesajLabel.setText("Kullanıcı adı ve parola alanı boş olamaz");
                mesajLabel.setVisible(true);
                excIcon.setVisible(true);
                error();
        	}else if(txtUsername.getText().equals("")){
        		txtUsername.setUnFocusColor(Color.RED);
        		mesajLabel.setText("Kullanıcı adı alanı boş olamaz!");
                mesajLabel.setVisible(true);
                excIcon.setVisible(true);
                error();
        	}else if(txtPassword.getText().equals("")){
                txtPassword.setUnFocusColor(Color.RED);
                mesajLabel.setText("Parola alanı boş olamaz!");
                mesajLabel.setVisible(true);
                excIcon.setVisible(true);
                error();
        	}else if(!txtUsername.getText().equals("admin") || !txtPassword.getText().equals("admin")) {
                txtUsername.setUnFocusColor(Color.RED);
                txtPassword.setUnFocusColor(Color.RED);
                mesajLabel.setText("Kullanıcı adı veya parola hatalı");
                mesajLabel.setVisible(true);
                excIcon.setVisible(true);
                error();
        	}else if (txtUsername.getText().equals("admin") && txtPassword.getText().equals("admin")) {
        		Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/flaaiairlines/view/MainPaneView.fxml"));
                AnchorPane topBar = (AnchorPane) root.lookup("#topBar");
                topBar.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
                topBar.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.getIcons().add(appIcon);
                stage.show();
                
                mainPane.getScene().getWindow().hide();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onFieldClicked() {
    	Color paint = new Color(0.302, 0.302, 0.302, 1.0);
    	txtUsername.setUnFocusColor(paint);
    	txtPassword.setUnFocusColor(paint);
    }
    
    public void onCloseButtonClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    public void showDesignerInfoView() {
    	Stage stage = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/flaaiairlines/view/DesignerInfoPaneView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.getIcons().add(appIcon);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void error() {
    	txtUsername.setUnFocusColor(Color.RED);
        txtPassword.setUnFocusColor(Color.RED);
        deneme--;
        denemeLabel.setText(deneme + " Hakkınız Kaldı");
        denemeLabel.setVisible(true);
        if(deneme == 0) {
        	mainPane.getScene().getWindow().hide();
        	Alert alert = new Alert(AlertType.ERROR);
        	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/com/flaaiairlines/media/appIconPlane.png").toString()));
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Deneme hakkınız kalmadı\nSistemden çıkılıyor...");
            alert.show();
        }
    }
    
    public void clock() {
        Thread clock = new Thread() {
            public void run() {
                try {
                    while(true) {
                        Platform.runLater(() -> {
                            LocalDate d = LocalDate.now();
                            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            String tarih = d.format(dateFormat);

                            LocalTime t = LocalTime.now();
                            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                            String saat = t.format(timeFormat);

                            dateLabel.setText(tarih);
                            timeLabel.setText(saat);
                        });
                        sleep(1000);
                    }   
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        clock.start();
    }

}
