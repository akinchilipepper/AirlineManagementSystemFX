package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    @FXML private Button cancelFlightButton;
    @FXML private Button updateCommitButton;
    @FXML private Button updateCancelButton;
    @FXML private TableView<Flight> flightsTable;
    private Flight flight;
    private MainViewController mainViewController;
    private String dpTimeBeforeChange;
    private String arTimeBeforeChange;
    private String flightStatusBeforeChange;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

    public void initializeObjects(Flight flight, int passengerCount, MainViewController mainViewController) {
        this.flight = flight;
        this.mainViewController = mainViewController;
        
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
				+ "Uçuşu iptal etmek yolcu biletlerinin iptal olmasına sebep olacaktır");
		if(choice == 0) {
			boolean result = FlightOperations.cancelFlight(flight);
			if(result) {
				mainViewController.setFlightsTable();
				JOptionPane.showMessageDialog(null, "Uçuş iptal edildi");
				Stage stage = (Stage) cancelFlightButton.getScene().getWindow();
		        stage.close();				
			} else {
				JOptionPane.showMessageDialog(null, "Uçuş iptal edilemedi");
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
		    	JOptionPane.showMessageDialog(null, "Herhangi bir değişiklik yapmadınız");
		    } else {
		    	int choice = JOptionPane.showConfirmDialog(null, "Uçuşu saatleri değiştirilecek. Onaylıyor musunuz?");
		    	if(choice == 0) {
		    		boolean result = FlightOperations.updateFlight(flight, dpTimeAfterChange, arTimeAfterChange, flightStatusAfterChange);
		    		if(result) {
		    			JOptionPane.showMessageDialog(null, "UÇUŞ GÜNCELLENDİ");
		    			mainViewController.setFlightsTable();
		    			updateCancel();
		    		}
		    	}
		    }
	    } else {
	    	JOptionPane.showMessageDialog(null, "Bu uçuşu güncelleyemezsiniz");
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
