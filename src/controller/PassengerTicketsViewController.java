package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Flight;
import model.Passenger;
import model.Seat;
import model.Ticket;

import operations.TicketOperations;

public class PassengerTicketsViewController implements Initializable {

	@FXML private TableView<Ticket> ticketsTable;
	@FXML private TableColumn<Ticket, String> flightNumberColumn;
	@FXML private TableColumn<Ticket, String> dpColumn;
	@FXML private TableColumn<Ticket, String> arColumn;
	@FXML private TableColumn<Ticket, String> seatColumn;
	@FXML private TableColumn<Ticket, String> pnrNumberColumn;
	@FXML private TableColumn<Ticket, Void> buttonColumn;
	@FXML private TextField ticketSearchField;
	
	private Passenger passenger;
	private ObservableList<Ticket> ticketData;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public void initalizeObjects(Passenger passenger) {
		this.passenger = passenger;
		setTicketsTable();
	}

	public void setTicketsTable() {
		ticketData = FXCollections.observableArrayList();
		Ticket[] tickets = TicketOperations.getPassengerTickets(passenger);
		for (Ticket ticket : tickets) {
			ticketData.add(ticket);
		}
		flightNumberColumn.setCellValueFactory(cellData -> {
			Flight flight = cellData.getValue().getFlight();
			if (flight != null) {
				return new SimpleStringProperty(flight.getUcusNo());
			} else {
				return new SimpleStringProperty("");
			}
		});
		dpColumn.setCellValueFactory(cellData -> {
			Flight flight = cellData.getValue().getFlight();
			if (flight != null) {
				return new SimpleStringProperty(flight.getKalkisYeri().getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		arColumn.setCellValueFactory(cellData -> {
			Flight flight = cellData.getValue().getFlight();
			if (flight != null) {
				return new SimpleStringProperty(flight.getVarisYeri().getHavaalani());
			} else {
				return new SimpleStringProperty("");
			}
		});
		seatColumn.setCellValueFactory(cellData -> {
			Seat seat = cellData.getValue().getSeat();
			if (seat != null) {
				return new SimpleStringProperty(seat.getKoltuknumarasi() + " / " + seat.getKoltukturu());
			} else {
				return new SimpleStringProperty("");
			}
		});
		pnrNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pnr"));
		buttonColumn.setCellFactory(param -> new ButtonCell());
		ticketsTable.setItems(ticketData);
	}
	
	public void ticketSearch() {
		String search = ticketSearchField.getText();
		ObservableList<Ticket> list = TicketOperations.getSearchedTickets(passenger, search);
		
		ticketsTable.setItems(list);
	}

	class ButtonCell extends TableCell<Ticket, Void> {
		private final JFXButton button;

		public ButtonCell() {
			this.button = new JFXButton("BİLETİ SİL");
			button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-text-fill: white;");
			button.setPrefWidth(120);
			button.setOnAction(event -> {
				try {
					int choice = JOptionPane.showConfirmDialog(null, "Bu bileti silmek istediğinize emin misiniz?", "Bilet İşlemleri", JOptionPane.YES_NO_OPTION);
					if (choice == 0) {
						Ticket ticket = ticketsTable.getSelectionModel().getSelectedItem();
						boolean result = TicketOperations.deleteTicket(ticket);
						if (result) {
							JOptionPane.showMessageDialog(null, "Bilet başarıyla silindi", "Bilet İşlemleri", JOptionPane.INFORMATION_MESSAGE);
							setTicketsTable();
						} else {
							JOptionPane.showMessageDialog(null, "Bilet silinemedi", "Hata", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin", "Hata", JOptionPane.ERROR_MESSAGE);
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
