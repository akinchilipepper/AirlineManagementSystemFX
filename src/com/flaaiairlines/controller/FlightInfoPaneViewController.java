package com.flaaiairlines.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.flaaiairlines.model.Flight;
import com.flaaiairlines.service.FlightServices;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FlightInfoPaneViewController implements Initializable {
	
	@FXML private TextField arAirportField;
    @FXML private TextField arDateField;
    @FXML private TextField arTimeField;
    @FXML private Label arrivalAirportIATALabel;
    @FXML private Label arrivalTimeLabel;
    @FXML private Label departureAirportIATALabel;
    @FXML private Label departureTimeLabel;
    @FXML private TextField dpAirportField;
    @FXML private TextField dpDateField;
    @FXML private TextField dpTimeField;
    @FXML private Label flightNumberLabel;
    @FXML private TextField flightStatusField;
    @FXML private Label flightTimeLabel;
    @FXML private TextField passengerCountField;
    @FXML private TextField planeModelField;
    @FXML private Button updateFlightButton;
    @FXML private Button cancelFlightButton;
    @FXML private Button updateCommitButton;
    @FXML private Button updateCancelButton;
    @FXML private TableView<Flight> flightsTable;
    private Flight flight;
    private MainPaneViewController mainPaneViewController;
    private String dpTimeBeforeChange;
    private String arTimeBeforeChange;
    private String flightStatusBeforeChange;
    
    private FlightServices flightService;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.flightService = new FlightServices();
	}

    public void initializeObjects(Flight flight, int passengerCount, MainPaneViewController mainPaneViewController) {
        this.flight = flight;
        this.mainPaneViewController = mainPaneViewController;
        
        planeModelField.setText(flight.getUcak().getModel());
		passengerCountField.setText(String.valueOf(flight.getUcak().getKapasite()) + "/" + passengerCount);
		flightStatusField.setText(flight.getDurum());
		dpTimeField.setText(flight.getKalkisZamani()); 
		dpDateField.setText(flight.getKalkisTarihi());
		dpAirportField.setText(flight.getKalkisYeri().getHavaalani());
		arTimeField.setText(flight.getVarisZamani());
		arDateField.setText(flight.getKalkisTarihi());
		arAirportField.setText(flight.getVarisYeri().getHavaalani());
		
		departureAirportIATALabel.setText(flight.getKalkisYeri().getIatakodu());
		arrivalAirportIATALabel.setText(flight.getVarisYeri().getIatakodu());
		departureTimeLabel.setText(flight.getKalkisZamani());
		arrivalTimeLabel.setText(flight.getVarisZamani());
		flightNumberLabel.setText(flight.getUcusNo());
    }
	
	public void cancelFlight() {
		int choice = JOptionPane.showConfirmDialog(null, "Uçuşu iptal etmek istediğinize emin misiniz?\n"
				+ "Uçuşu iptal etmek yolcu biletlerinin iptal olmasına sebep olacaktır.", "Uçuş İptali", JOptionPane.WARNING_MESSAGE);
		if(choice == 0) {
			boolean result = flightService.cancelFlight(flight);
			if(result) {
				mainPaneViewController.setFlightsTable();
				JOptionPane.showMessageDialog(null, "Uçuş iptal edildi!", "Uçuş İptali", JOptionPane.INFORMATION_MESSAGE);
				Stage stage = (Stage) cancelFlightButton.getScene().getWindow();
		        stage.close();				
			} else {
				JOptionPane.showMessageDialog(null, "Uçuş iptal edilemedi.", "Uçuş İptali", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void updateFlight() {
		dpTimeField.setEditable(true); 
		arTimeField.setEditable(true);
		flightStatusField.setEditable(true);
		
		updateFlightButton.setVisible(false);
		cancelFlightButton.setVisible(false);
		
		updateCommitButton.setVisible(true);
		updateCancelButton.setVisible(true);
		
		this.dpTimeBeforeChange = dpTimeField.getText();
		this.arTimeBeforeChange = arTimeField.getText();
		this.flightStatusBeforeChange = flightStatusField.getText();
		
	}
	
	public void updateCommit() {
		String dpTimeAfterChange = dpTimeField.getText();
	    String arTimeAfterChange = arTimeField.getText();
	    String flightStatusAfterChange = flightStatusField.getText();
	    
	    String flightStatus = flight.getDurum();
	    if(flightStatus.equals("BEKLİYOR")) {
	    	if(dpTimeAfterChange.equals(dpTimeBeforeChange) && arTimeAfterChange.equals(arTimeBeforeChange)
	    			&& flightStatusAfterChange.equals(flightStatusBeforeChange)) {
		    	JOptionPane.showMessageDialog(null, "Herhangi bir değişiklik yapmadınız.", "Hata", JOptionPane.ERROR_MESSAGE);
		    } else {
		    	int choice = JOptionPane.showConfirmDialog(null, "Uçuşu saatleri değiştirilecek. Onaylıyor musunuz?", "Uçuş Düzenleme", JOptionPane.YES_NO_OPTION);
		    	if(choice == 0) {
		    		int result = flightService.updateFlightStatus(flight, dpTimeAfterChange, arTimeAfterChange, flightStatusAfterChange);
		    		if(result == 0) {
		    			JOptionPane.showMessageDialog(null, "Uçuş güncellendi!", "Uçuş Düzenleme", JOptionPane.INFORMATION_MESSAGE);
		    			mainPaneViewController.setFlightsTable();
		    			updateCancel();
		    		} else if (result == 1) {
		    			JOptionPane.showMessageDialog(null, "Uçuş güncellenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
		    			updateCancel();
		    		} else if(result == 2) {
		    			JOptionPane.showMessageDialog(null, "Lütfen bilgileri doğru formatta giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
		    			updateCancel();
		    		} 
		    	}
		    }
	    } else {
	    	JOptionPane.showMessageDialog(null, "Bu uçuşu güncelleyemezsiniz!");
	    }
	}
	
	public void updateCancel() {
		dpTimeField.setEditable(false); 
		arTimeField.setEditable(false);
		flightStatusField.setEditable(false);

		updateFlightButton.setVisible(true);
		cancelFlightButton.setVisible(true);
		
		updateCommitButton.setVisible(false);
		updateCancelButton.setVisible(false);
	}
}
