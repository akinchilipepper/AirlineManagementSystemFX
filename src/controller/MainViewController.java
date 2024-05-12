package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.Airport;
import model.Flight;
import operations.AirportOperations;
import operations.FlightOperations;

public class MainViewController implements Initializable{
	
	@FXML
    private ComboBox<String> arrivalBox;

    @FXML
    private Button closeButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private Button flightManagementButton;

    @FXML
    private AnchorPane flightsPane;

    @FXML
    private ComboBox<String> originBox;

    @FXML
    private Button passengersButton;

    @FXML
    private AnchorPane passengersPane;

    @FXML
    private Button personnelsButton;

    @FXML
    private AnchorPane personnelsPane;

    @FXML
    private Button planesButton;

    @FXML
    private AnchorPane planesPane;

    @FXML
    private AnchorPane topBar;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private TableView<Flight> flightsTable;
    
    @FXML
    private TableColumn<Flight, String> table_column_arrival;

    @FXML
    private TableColumn<Flight, String> table_column_arrivalDate;

    @FXML
    private TableColumn<Flight, String> table_column_arrivalTime;

    @FXML
    private TableColumn<Flight, String> table_column_departure;

    @FXML
    private TableColumn<Flight, String> table_column_departureDate;

    @FXML
    private TableColumn<Flight, String> table_column_departureTime;

    @FXML
    private TableColumn<Flight, String> table_column_flightNumber;

    @FXML
    private TableColumn<Flight, String> table_column_plane;

    @FXML
    private TableColumn<Flight, String> table_column_status;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setComboBoxItems();
		setFlightsTable();
	}
	
	public void setFlightsTable() {
	    Flight[] flights = FlightOperations.getFlights();	    
	    ObservableList<Flight> data = FXCollections.observableArrayList();
	    
	    for(Flight flight : flights) {
	    	data.add(flight);
	    }

	    table_column_departure.setCellValueFactory(new PropertyValueFactory<>("kalkisyeri"));
	    table_column_arrival.setCellValueFactory(new PropertyValueFactory<>("varisyeri"));
	    table_column_departureDate.setCellValueFactory(new PropertyValueFactory<>("kalkisTarihi"));
	    table_column_arrivalDate.setCellValueFactory(new PropertyValueFactory<>("varisTarihi"));
	    table_column_departureTime.setCellValueFactory(new PropertyValueFactory<>("kalkisZamani"));
	    table_column_arrivalTime.setCellValueFactory(new PropertyValueFactory<>("varisZamani"));
	    table_column_plane.setCellValueFactory(new PropertyValueFactory<>("ucak"));
	    table_column_flightNumber.setCellValueFactory(new PropertyValueFactory<>("ucusNo"));
	    table_column_status.setCellValueFactory(new PropertyValueFactory<>("durum"));
	    flightsTable.setItems(data);
	}

	
	public void setComboBoxItems() {
	    Airport[] airports = AirportOperations.getAirports();
	    String[] allAirports = new String[airports.length];
	    
	    for (int i = 0; i < airports.length; i++) {
	        allAirports[i] = airports[i].getHavaalani();
	    }
	    
	    arrivalBox.getItems().addAll(allAirports);
	    originBox.getItems().addAll(allAirports);
	}

	
	
	public void onCloseButtonClicked() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
	
	public void logout() {
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
	}
	
	public void switchForm(ActionEvent event) {
		if(event.getSource() == flightManagementButton) {
			flightsPane.setVisible(true);
			planesPane.setVisible(false);
			passengersPane.setVisible(false);
			personnelsPane.setVisible(false);
			dashboardPane.setVisible(false);
		} else if(event.getSource() == planesButton) {
			flightsPane.setVisible(false);
			planesPane.setVisible(true);
			passengersPane.setVisible(false);
			personnelsPane.setVisible(false);
			dashboardPane.setVisible(false);
		} else if(event.getSource() == passengersButton) {
			flightsPane.setVisible(false);
			planesPane.setVisible(false);
			passengersPane.setVisible(true);
			personnelsPane.setVisible(false);
			dashboardPane.setVisible(false);
		} else if(event.getSource() == personnelsButton) {
			flightsPane.setVisible(false);
			planesPane.setVisible(false);
			passengersPane.setVisible(false);
			personnelsPane.setVisible(true);
			dashboardPane.setVisible(false);
		} else if(event.getSource() == dashboardButton) {
			flightsPane.setVisible(false);
			planesPane.setVisible(false);
			passengersPane.setVisible(false);
			personnelsPane.setVisible(false);
			dashboardPane.setVisible(true);
		}
	}
}
