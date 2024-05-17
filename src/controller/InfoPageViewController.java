package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Flight;
import operations.FlightOperations;

public class InfoPageViewController implements Initializable {
	
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
    @FXML private Button deleteFlightButton;
    @FXML private Button updateCommitButton;
    @FXML private Button updateCancelButton;
    private Flight flight;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

    public void initializeFlight(Flight flight) {
        this.flight = flight;
        
        planeModelField.setText(flight.getUcak().getModel());
		passengerCountField.setText(String.valueOf(flight.getUcak().getKapasite()) + "/");
		flightStatusField.setText(flight.getDurum());
		dpTimeField.setText(flight.getKalkisZamani()); 
		dpDateField.setText(flight.getKalkisTarihi());
		dpAirportField.setText(flight.getKalkisyeri().getHavaalani());
		arTimeField.setText(flight.getVarisZamani());
		arDateField.setText(flight.getKalkisTarihi());
		arAirportField.setText(flight.getVarisyeri().getHavaalani());
		
		departureAirportIATALabel.setText(flight.getKalkisyeri().getIatakodu());
		arrivalAirportIATALabel.setText(flight.getVarisyeri().getIatakodu());
		departureTimeLabel.setText(flight.getKalkisZamani());
		arrivalTimeLabel.setText(flight.getVarisZamani());
		flightNumberLabel.setText(flight.getUcusNo());
    }
	
	public void deleteFlight() {
		boolean isFlightDeleted = FlightOperations.deleteFlight(flight.getId());
		if(isFlightDeleted) {
			
		} else {
			
		}
	}
	
	public void updateFlight() {
		planeModelField.setEditable(true);
		passengerCountField.setEditable(true);
		flightStatusField.setEditable(true);
		dpTimeField.setEditable(true); 
		dpDateField.setEditable(true);
		dpAirportField.setEditable(true);
		arTimeField.setEditable(true);
		arDateField.setEditable(true);
		arAirportField.setEditable(true);
		
		updateFlightButton.setVisible(false);
		deleteFlightButton.setVisible(false);
		
		updateCommitButton.setVisible(true);
		updateCancelButton.setVisible(true);
	}
	
	public void updateCommit() {
		
	}
	
	public void updateCancel() {
		planeModelField.setEditable(false);
		passengerCountField.setEditable(false);
		flightStatusField.setEditable(false);
		dpTimeField.setEditable(false); 
		dpDateField.setEditable(false);
		dpAirportField.setEditable(false);
		arTimeField.setEditable(false);
		arDateField.setEditable(false);
		arAirportField.setEditable(false);
		
		updateFlightButton.setVisible(true);
		deleteFlightButton.setVisible(true);
		
		updateCommitButton.setVisible(false);
		updateCancelButton.setVisible(false);
	}

}
