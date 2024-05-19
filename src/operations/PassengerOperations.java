package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Flight;
import model.Passenger;
import model.Seat;
import model.Ticket;
import system.DBConnection;

public class PassengerOperations {
	private static final DBConnection conn = new DBConnection();
	private static final Connection con = conn.connDb();
	private static PreparedStatement ps;
	private static ResultSet rs;

	public static Passenger[] getPassengers() {
		String query = "SELECT * FROM YOLCULAR";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			ArrayList<Passenger> passengerList = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String ad = rs.getString(2);
				String soyad = rs.getString(3);
				String telno = rs.getString(4);
				String e_posta = rs.getString(5);
				String cinsiyet = rs.getString(6);
				String dogumtarihi = rs.getString(7);
				String parola = rs.getString(8);

				Passenger passenger = new Passenger(id, ad, soyad, telno, e_posta, cinsiyet, dogumtarihi, parola);
				passengerList.add(passenger);
			}
			Passenger[] passengers = passengerList.toArray(Passenger[]::new);

			return passengers;
		} catch (SQLException ex) {
			Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static Ticket[] getPassengerTickets(Passenger passenger) {
		String query = "SELECT ID, UCUSID, KOLTUKID, PNR FROM BILETLER WHERE YOLCUID = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, passenger.getId());
			rs = ps.executeQuery();
			ArrayList<Ticket> ticketList = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt(1);
				int ucusId = rs.getInt(2);
				int koltukId = rs.getInt(3);
				String pnr = rs.getString(4);

				Flight flight = FlightOperations.getFlight(ucusId);
				Seat seat = SeatOperations.getSeat(flight, koltukId);

				Ticket ticket = new Ticket(id, pnr, passenger, flight, seat);
				ticketList.add(ticket);
			}

			Ticket[] tickets = ticketList.toArray(Ticket[]::new);

			return tickets;
		} catch (SQLException ex) {
			Logger.getLogger(PassengerOperations.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}