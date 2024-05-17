package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airport;
import model.Flight;
import model.Plane;
import operations.AirportOperations;
import operations.FlightOperations;
import operations.PlaneOperations;

public class MainViewController implements Initializable{
	
	@FXML private ComboBox<String> arrivalBox;
	@FXML private ComboBox<String> planeBox;	
	@FXML private ComboBox<String> statusBox;	
	@FXML private ComboBox<String> originBox;
    @FXML private Button closeButton;
    @FXML private Button dashboardButton;    
    @FXML private Button planesButton;    
    @FXML private Button personnelsButton;    
    @FXML private Button passengersButton;    
    @FXML private Button flightManagementButton;    
    @FXML private Button logoutButton;    
    @FXML private Button addFlightButton;
    @FXML private AnchorPane dashboardPane;
    @FXML private AnchorPane flightsPane;
    @FXML private AnchorPane passengersPane;
    @FXML private AnchorPane personnelsPane;
    @FXML private AnchorPane planesPane;
    @FXML private AnchorPane topBar;    
    @FXML private TableView<Flight> flightsTable;    
    @FXML private TableColumn<Flight, String> table_column_arrival;
    @FXML private TableColumn<Flight, String> table_column_arrivalTime;
    @FXML private TableColumn<Flight, String> table_column_departure;
    @FXML private TableColumn<Flight, String> table_column_flightDate;
    @FXML private TableColumn<Flight, String> table_column_departureTime;
    @FXML private TableColumn<Flight, String> table_column_flightNumber;   
    @FXML private TableColumn<Flight, Void> table_column_flightDetails;   
    @FXML private DatePicker departureDatePicker;   
    @FXML private DatePicker arrivalDatePicker;   
    @FXML private TextField departureTimeField;   
    @FXML private TextField arrivalTimeField;   
    @FXML private TextField flightNumberField;   
    @FXML private TextField ticketPriceField;
    private Flight[] flights;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setAllBoxItems();
		setFlightsTable();
		departureDatePicker.setConverter(converter);
		arrivalDatePicker.setConverter(converter);
	}
	
	public void setFlightsTable() {
	    flights = FlightOperations.getFlights();	    
	    ObservableList<Flight> data = FXCollections.observableArrayList();
	    
	    for(Flight flight : flights) {
	    	data.add(flight);
	    }

	    table_column_departure.setCellValueFactory(cellData -> {
	        Airport airport = cellData.getValue().getKalkisyeri();
	        if (airport != null) {
	            return new SimpleStringProperty(airport.getHavaalani());
	        } else {
	            return new SimpleStringProperty("");
	        }
	    });
	    table_column_arrival.setCellValueFactory(cellData -> {
	        Airport airport = cellData.getValue().getVarisyeri();
	        if (airport != null) {
	            return new SimpleStringProperty(airport.getHavaalani());
	        } else {
	            return new SimpleStringProperty("");
	        }
	    });  
	    table_column_flightDate.setCellValueFactory(new PropertyValueFactory<>("kalkisTarihi"));
	    table_column_departureTime.setCellValueFactory(new PropertyValueFactory<>("kalkisZamani"));
	    table_column_arrivalTime.setCellValueFactory(new PropertyValueFactory<>("varisZamani"));
	    table_column_flightNumber.setCellValueFactory(new PropertyValueFactory<>("ucusNo"));
	    table_column_flightNumber.setCellValueFactory(new PropertyValueFactory<>("ucusNo"));
	    table_column_flightDetails.setCellFactory(param -> new ButtonCell());

	    flightsTable.setItems(data);
	}
	
	public void setAllBoxItems() {
	    Airport[] airports = AirportOperations.getAirports();
	    String[] allAirports = new String[airports.length];
	    
	    for (int i = 0; i < airports.length; i++) {
	        allAirports[i] = airports[i].getHavaalani();
	    }
	    
	    Plane[] planes = PlaneOperations.getPlanes();
		String[] allPlanes = new String[planes.length];
		
		for(int i = 0; i < planes.length; i++) {
			allPlanes[i] = planes[i].getModel(); 
		}
			
		statusBox.getItems().addAll("BEKLİYOR", "KALKIŞ YAPIYOR", "YOLDA", "İNİŞ YAPIYOR", "İNDİ");
		planeBox.getItems().addAll(allPlanes);	    
	    arrivalBox.getItems().addAll(allAirports);
	    originBox.getItems().addAll(allAirports);
	}

	public void addFlight() {
		String departureAirport = originBox.getValue();
		String arrivalAirport = arrivalBox.getValue();
		String departureDate = departureDatePicker.getValue().toString();
		String arrivalDate = arrivalDatePicker.getValue().toString();
		String departureTime = departureTimeField.getText();
		String arrivalTime = arrivalTimeField.getText();
		String plane = planeBox.getValue();
		String flightStatus = statusBox.getValue();
		String flightNumber = flightNumberField.getText();
		int ticketPrice = Integer.parseInt(ticketPriceField.getText());

		
		boolean isFlightAdded = FlightOperations.addFlight(departureAirport, arrivalAirport, departureDate, arrivalDate,
				departureTime, arrivalTime, plane, flightStatus, flightNumber, ticketPrice);
		
		if(!isFlightAdded) {
			JOptionPane.showMessageDialog(null, "UÇUŞ EKLENEMEDİ!");
		} 
		
		setFlightsTable();	
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
	
	StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };
    
    class ButtonCell extends TableCell<Flight, Void> {
        private final JFXButton button;

        public ButtonCell() {
            this.button = new JFXButton("Uçuş Bilgileri");
            button.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            button.setPrefWidth(115);
            button.setOnAction(event -> {
            	try {
            	    int selectedRow = flightsTable.getSelectionModel().getSelectedIndex();
            	    
            	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InfoPageView.fxml"));
            	    Parent root = loader.load();
            	    InfoPageViewController controller = loader.getController();
            	    controller.initializeFlight(flights[selectedRow]);
            	    
            	    Scene scene = new Scene(root);
            	    Stage stage = new Stage();
            	    stage.setScene(scene);
            	    stage.show();
            	} catch (Exception e) {
            	    e.printStackTrace();
            	}
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(button);
            }
        }
    }
}
