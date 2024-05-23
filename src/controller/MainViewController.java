package controller;

import java.io.IOException;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Airport;
import model.Flight;
import model.Passenger;
import model.Plane;
import operations.AirportOperations;
import operations.FlightOperations;
import operations.PassengerOperations;
import operations.PlaneOperations;
import operations.TicketOperations;

public class MainViewController implements Initializable {

	@FXML private ComboBox<String> arrivalBox;
	@FXML private ComboBox<String> planeBox;
	@FXML private ComboBox<String> statusBox;
	@FXML private ComboBox<String> originBox;
	
	@FXML private Button closeButton;
	@FXML private Button dashboardButton;
	@FXML private Button flightsButton;
	@FXML private Button passengersButton;
	@FXML private Button flightManagementButton;
	@FXML private Button logoutButton;
	@FXML private Button addFlightButton;
	
	@FXML private AnchorPane dashboardPane;
	@FXML private AnchorPane flightManagementPane;
	@FXML private AnchorPane passengersPane;
	@FXML private AnchorPane personnelsPane;
	@FXML private AnchorPane flightsPane;
	@FXML private AnchorPane topBar;
	
	@FXML private TableView<Flight> flightManagementTable;
	@FXML private TableView<Flight> flightsTable;
	@FXML private TableView<Passenger> passengersTable;

	@FXML private TableColumn<Flight, String> mArColumn;
	@FXML private TableColumn<Flight, String> mDpColumn;
	@FXML private TableColumn<Flight, String> mFlightDateColumn;
	@FXML private TableColumn<Flight, String> mDpTimeColumn;
	@FXML private TableColumn<Flight, String> mFlightNumberColumn;
	@FXML private TableColumn<Flight, Void> mDetailsColumn;

	@FXML private TableColumn<Flight, String> flightNumberColumn;
	@FXML private TableColumn<Flight, String> arColumn;
	@FXML private TableColumn<Flight, String> dpColumn;
	@FXML private TableColumn<Flight, String> arTimeColumn;
	@FXML private TableColumn<Flight, String> dpTimeColumn;
	@FXML private TableColumn<Flight, String> flightStatusColumn;

	@FXML private TableColumn<Passenger, String> nameColumn;
	@FXML private TableColumn<Passenger, String> surnameColumn;
	@FXML private TableColumn<Passenger, String> genderColumn;
	@FXML private TableColumn<Passenger, String> birthdateColumn;
	@FXML private TableColumn<Passenger, Void> ticketsColumn;

	@FXML private DatePicker departureDatePicker;
	@FXML private DatePicker arrivalDatePicker;
	
	@FXML private TextField departureTimeField;
	@FXML private TextField arrivalTimeField;
	@FXML private TextField flightNumberField;
	@FXML private TextField ticketPriceField;
	@FXML private TextField flightManagementSearchField;
	@FXML private TextField flightSearchField;
	@FXML private TextField passengerSearchField;
	
	@FXML private Label flightCountLabel;
	@FXML private Label userCountLabel;
	@FXML private Label incomeLabel;
	
	@FXML private AreaChart<String, Double> dashboardChart;

	private Flight[] flights;
	private Passenger[] passengers;
	
	private ObservableList<Flight> flightData;
	private ObservableList<Passenger> passengerData;
	private Image image;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		image = new Image("/media/appIconPlane.png");
		setAllBoxItems();
		setFlightsTable();
		setPassengersTable();
		
		displayFlightsCount();
		displayUserCount();
		displayTotalIncome();
		displayAreaChart();
		
		
		departureDatePicker.setConverter(converter);
		arrivalDatePicker.setConverter(converter);
	}

	public void setPassengersTable() {
		passengers = PassengerOperations.getPassengers();
		passengerData = FXCollections.observableArrayList();

		for (Passenger passenger : passengers) {
			passengerData.add(passenger);
		}

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
		surnameColumn.setCellValueFactory(new PropertyValueFactory<>("soyad"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("cinsiyet"));
		birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("dogumtarihi"));
		ticketsColumn.setCellFactory(param -> new PassengerButtonCell());

		passengersTable.setItems(passengerData);
	}

	public void setFlightsTable() {
		flights = FlightOperations.getFlights();
		flightData = FXCollections.observableArrayList();

		for (Flight flight : flights) {
			flightData.add(flight);
		}

		mDpColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getKalkisYeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		mArColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getVarisYeri();
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
			Airport airport = cellData.getValue().getKalkisYeri();
			if (airport != null) {
				return new SimpleStringProperty(airport.getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		arColumn.setCellValueFactory(cellData -> {
			Airport airport = cellData.getValue().getVarisYeri();
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

		flightManagementTable.setItems(flightData);
		flightsTable.setItems(flightData);
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
		if (areFieldsAreEmpty()) {
			JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun.", "Uçuş Ekleme", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Uçuş eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
			} else if(isFlightAdded == 2) {
				JOptionPane.showMessageDialog(null, "Lütfen benzersiz bir uçuş numarası girin", "Hata", JOptionPane.ERROR_MESSAGE);
			}

			setFlightsTable();
		}
	}
	
	public void displayFlightsCount() {
		int flightCount = FlightOperations.getFlightsCount();
		flightCountLabel.setText(String.valueOf(flightCount));
	}
	
	public void displayUserCount() {
		int userCount = PassengerOperations.getPassengersCount();
		userCountLabel.setText(String.valueOf(userCount));
	}
	
	public void displayTotalIncome() {
		double totalIncome = TicketOperations.getTotalIncome();
		incomeLabel.setText(String.valueOf(totalIncome) + " TL");
	}
	
	public void displayAreaChart() {
		dashboardChart.getData().clear();
		XYChart.Series<String, Double> data = TicketOperations.getIncomeChart();
		dashboardChart.getData().add(data);
	}
	
	public void flightManagementSearch() {
		String search = flightManagementSearchField.getText();
		ObservableList<Flight> list = FlightOperations.getSearchedFlight(search);
		
		flightManagementTable.setItems(list);
	}
	
	public void flightSearch() {
		String search = flightSearchField.getText();
		ObservableList<Flight> list = FlightOperations.getSearchedFlight(search);
		
		flightsTable.setItems(list);
	}
	
	public void passengerSearch() {
		String search = passengerSearchField.getText();
		ObservableList<Passenger> list = PassengerOperations.getSearchedPassengers(search);
		
		passengersTable.setItems(list);
	}
	
	public boolean areFieldsAreEmpty() {
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
	    Stage currentStage = (Stage) logoutButton.getScene().getWindow();
	    currentStage.close();
	    
	    try {
	        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
	        Scene scene = new Scene(root);
	        
	        Stage newStage = new Stage();
	        newStage.setScene(scene);
	        newStage.initStyle(StageStyle.UNDECORATED);
	        newStage.getIcons().add(image);
	        newStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
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
			displayFlightsCount();
			displayUserCount();
			displayTotalIncome();
			displayAreaChart();
		}
	}

	StringConverter<LocalDate> converter=new StringConverter<LocalDate>(){DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override public String toString(LocalDate date){if(date!=null){return dateFormatter.format(date);}else{return"";}}

	@Override public LocalDate fromString(String string){if(string!=null&&!string.isEmpty()){return LocalDate.parse(string,dateFormatter);}else{return null;}}};

	
	class FlightButtonCell extends TableCell<Flight, Void> {
		private final JFXButton button;

		public FlightButtonCell() {
			this.button = new JFXButton("Uçuş Bilgileri");
			button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-text-fill: white;");
			button.setPrefWidth(120);
			button.setOnAction(event -> {
				try {
					Flight flight = flightManagementTable.getSelectionModel().getSelectedItem();
					int passengerCount = PassengerOperations.getPassengersCount(flight);

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InfoPageView.fxml"));
					Parent root = loader.load();
					InfoPageViewController controller = loader.getController();
					controller.initializeObjects(flight, passengerCount, MainViewController.this);

					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("Uçuş Detayları");
					stage.setResizable(false);
					stage.getIcons().add(image);
					stage.show();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
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
					Passenger passenger = passengersTable.getSelectionModel().getSelectedItem();

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PassengerTicketsView.fxml"));
					Parent root = loader.load();
					PassengerTicketsViewController controller = loader.getController();
					controller.initalizeObjects(passenger);

					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("Biletler");
					stage.setResizable(false);
					stage.getIcons().add(image);
					stage.show();
				} catch (NullPointerException e) {
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
