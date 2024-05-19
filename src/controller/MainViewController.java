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
import model.Passenger;
import model.Plane;
import operations.AirportOperations;
import operations.FlightOperations;
import operations.PassengerOperations;
import operations.PlaneOperations;

public class MainViewController implements Initializable {

	@FXML
	private ComboBox<String> arrivalBox;
	@FXML
	private ComboBox<String> planeBox;
	@FXML
	private ComboBox<String> statusBox;
	@FXML
	private ComboBox<String> originBox;
	@FXML
	private Button closeButton;
	@FXML
	private Button dashboardButton;
	@FXML
	private Button flightsButton;
	@FXML
	private Button passengersButton;
	@FXML
	private Button flightManagementButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button addFlightButton;
	@FXML
	private AnchorPane dashboardPane;
	@FXML
	private AnchorPane flightManagementPane;
	@FXML
	private AnchorPane passengersPane;
	@FXML
	private AnchorPane personnelsPane;
	@FXML
	private AnchorPane flightsPane;
	@FXML
	private AnchorPane topBar;
	@FXML
	private TableView<Flight> flightManagementTable;
	@FXML
	private TableView<Flight> flightsTable;
	@FXML
	private TableView<Passenger> passengersTable;
	
	
	@FXML
	private TableColumn<Flight, String> mArColumn;
	@FXML
	private TableColumn<Flight, String> mDpColumn;
	@FXML
	private TableColumn<Flight, String> mFlightDateColumn;
	@FXML
	private TableColumn<Flight, String> mDpTimeColumn;
	@FXML
	private TableColumn<Flight, String> mFlightNumberColumn;
	@FXML
	private TableColumn<Flight, Void> mDetailsColumn;
	
	
	@FXML
	private TableColumn<Flight, String> flightNumberColumn;
	@FXML
	private TableColumn<Flight, String> arColumn;
	@FXML
	private TableColumn<Flight, String> dpColumn;
	@FXML
	private TableColumn<Flight, String> arTimeColumn;
	@FXML
	private TableColumn<Flight, String> dpTimeColumn;
	@FXML
	private TableColumn<Flight, String> flightStatusColumn;
	
	
	@FXML
	private TableColumn<Passenger, String> nameColumn;
	@FXML
	private TableColumn<Passenger, String> surnameColumn;
	@FXML
	private TableColumn<Passenger, String> genderColumn;
	@FXML
	private TableColumn<Passenger, String> birthdateColumn;
	@FXML
	private TableColumn<Passenger, Void> ticketsColumn;
	
	
	@FXML
	private DatePicker departureDatePicker;
	@FXML
	private DatePicker arrivalDatePicker;
	@FXML
	private TextField departureTimeField;
	@FXML
	private TextField arrivalTimeField;
	@FXML
	private TextField flightNumberField;
	@FXML
	private TextField ticketPriceField;
	
	private Flight[] flights;
	private Passenger[] passengers;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setAllBoxItems();
		setFlightsTable();
		departureDatePicker.setConverter(converter);
		arrivalDatePicker.setConverter(converter);
		setPassengersTable();
	}
	
	public void setPassengersTable() {
		passengers = PassengerOperations.getPassengers();
		ObservableList<Passenger> data = FXCollections.observableArrayList();
		
		for(Passenger passenger : passengers) {
			data.add(passenger);
		}
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
		surnameColumn.setCellValueFactory(new PropertyValueFactory<>("soyad"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("cinsiyet"));
		birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("dogumtarihi"));
		ticketsColumn.setCellFactory(param -> new PassengerButtonCell());
		
		passengersTable.setItems(data);
	}

	public void setFlightsTable() {
		flights = FlightOperations.getFlights();
		ObservableList<Flight> data = FXCollections.observableArrayList();

		for (Flight flight : flights) {
			data.add(flight);
		}

		mDpColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getKalkisyeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		mArColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getVarisyeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		mFlightDateColumn.setCellValueFactory(new PropertyValueFactory<>("kalkisTarihi"));
		mDpTimeColumn.setCellValueFactory(new PropertyValueFactory<>("kalkisZamani"));
		mFlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ucusNo"));
		mDetailsColumn.setCellFactory(param -> new FlightButtonCell());
		
		
		flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ucusNo"));
		dpColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getKalkisyeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		arColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getVarisyeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		dpTimeColumn.setCellValueFactory(cellData -> {
		    Flight model = cellData.getValue();
		    return new SimpleStringProperty(model.getKalkisTarihi() + " / " + model.getKalkisZamani());
		});
		arTimeColumn.setCellValueFactory(cellData -> {
		    Flight model = cellData.getValue();
		    return new SimpleStringProperty(model.getVarisTarihi() + " / " + model.getVarisZamani());
		});
		flightStatusColumn.setCellValueFactory(new PropertyValueFactory<>("durum"));

		flightManagementTable.setItems(data);
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

		for (int i = 0; i < planes.length; i++) {
			allPlanes[i] = planes[i].getModel();
		}

		statusBox.getItems().addAll("BEKLİYOR", "KALKIŞ YAPIYOR", "YOLDA", "İNİŞ YAPIYOR", "İNDİ");
		planeBox.getItems().addAll(allPlanes);
		arrivalBox.getItems().addAll(allAirports);
		originBox.getItems().addAll(allAirports);
	}

	public void addFlight() {
		if (isFieldsAreEmpty()) {
			JOptionPane.showMessageDialog(null, "LÜTFEN TÜM ALANLARI DOLDURUN");
		} else {
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

			int isFlightAdded = FlightOperations.addFlight(departureAirport, arrivalAirport, departureDate,
					arrivalDate, departureTime, arrivalTime, plane, flightStatus, flightNumber, ticketPrice);

			if (isFlightAdded == 1) {
				JOptionPane.showMessageDialog(null, "UÇUŞ EKLENEMEDİ!");
			} else if(isFlightAdded == 2) {
				JOptionPane.showMessageDialog(null, "LÜTFEN BENZERSİZ BİR UÇUŞ NUMARASI GİRİN");
			}

			setFlightsTable();
		}
	}

	public boolean isFieldsAreEmpty() {
		if (originBox.getValue() == null || arrivalBox.getValue() == null
				|| departureDatePicker.getValue() == null
				|| arrivalDatePicker.getValue() == null || departureTimeField.getText().isEmpty()
				|| arrivalTimeField.getText().isEmpty() || planeBox.getValue() == null
				|| statusBox.getValue() == null || flightNumberField.getText().isEmpty()
				|| ticketPriceField.getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
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
		if (event.getSource() == flightManagementButton) {
			flightManagementPane.setVisible(true);
			flightsPane.setVisible(false);
			passengersPane.setVisible(false);
			dashboardPane.setVisible(false);
		} else if (event.getSource() == flightsButton) {
			flightManagementPane.setVisible(false);
			flightsPane.setVisible(true);
			passengersPane.setVisible(false);
			dashboardPane.setVisible(false);
		} else if (event.getSource() == passengersButton) {
			flightManagementPane.setVisible(false);
			flightsPane.setVisible(false);
			passengersPane.setVisible(true);
			dashboardPane.setVisible(false);
		} else if (event.getSource() == dashboardButton) {
			flightManagementPane.setVisible(false);
			flightsPane.setVisible(false);
			passengersPane.setVisible(false);
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

	class FlightButtonCell extends TableCell<Flight, Void> {
		private final JFXButton button;

		public FlightButtonCell() {
			this.button = new JFXButton("Uçuş Bilgileri");
			button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-text-fill: white;");
			button.setPrefWidth(120);
			button.setOnAction(event -> {
				try {
					int selectedRow = flightManagementTable.getSelectionModel().getSelectedIndex();
					int passengerCount = FlightOperations.getPassengersCount(flights[selectedRow]);
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InfoPageView.fxml"));
					Parent root = loader.load();
					InfoPageViewController controller = loader.getController();
					controller.initializeObjects(flights[selectedRow], passengerCount, MainViewController.this);

					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin");
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
	
	class PassengerButtonCell extends TableCell<Passenger, Void> {
		private final JFXButton button;

		public PassengerButtonCell() {
			this.button = new JFXButton("BİLETLER");
			button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-text-fill: white;");
			button.setPrefWidth(130);
			button.setOnAction(event -> {
				try {
					int selectedRow = passengersTable.getSelectionModel().getSelectedIndex();
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PassengerTicketsView.fxml"));
					Parent root = loader.load();
					PassengerTicketsViewController controller = loader.getController();
					controller.initalizeObjects(passengers[selectedRow]);
					
					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin");
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
