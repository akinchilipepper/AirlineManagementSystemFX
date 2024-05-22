package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import model.Flight;
import model.Passenger;
import model.Seat;
import model.Ticket;
import system.DBConnection;

public class TicketOperations {
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    public static Ticket[] getPassengerTickets(Passenger passenger) {
		String query = "SELECT ID, UCUSID, KOLTUKID, PNR, UCRET FROM BILETLER WHERE YOLCUID = ?";
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
				double ucret = rs.getDouble(5);

				Flight flight = FlightOperations.getFlight(ucusId);
				Seat seat = SeatOperations.getSeat(flight, koltukId);

				Ticket ticket = new Ticket(id, pnr, passenger, flight, seat, ucret);
				ticketList.add(ticket);
			}

			Ticket[] tickets = ticketList.toArray(Ticket[]::new);

			return tickets;
		} catch (SQLException ex) {
			Logger.getLogger(PassengerOperations.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static boolean deleteTicket(Ticket ticket) {
    	String deleteTicketQuery = "DELETE FROM BILETLER WHERE ID = ?";
    	String deleteUserFromFlightQuery = "DELETE FROM UCUSYOLCU WHERE UCUSID = ? AND YOLCUID = ?";
    	String setReservedSeatFalseQuery = "UPDATE KOLTUKLAR SET REZERVEDURUMU = 0 "
    			+ "WHERE UCUSID = ? AND KOLTUKNUMARASI = ?";
    	try {
    		ps = con.prepareStatement(deleteTicketQuery);
    		ps.setInt(1, ticket.getId());
    		int deleteTicketResult = ps.executeUpdate();
    		
    		ps = con.prepareStatement(deleteUserFromFlightQuery);
    		ps.setInt(1, ticket.getFlight().getId());
    		ps.setInt(2, ticket.getPassenger().getId());
    		int deleteUserFromFlightResult = ps.executeUpdate();
    		
    		ps = con.prepareStatement(setReservedSeatFalseQuery);
    		ps.setInt(1, ticket.getSeat().getFlight().getId());
    		ps.setString(2, ticket.getSeat().getKoltuknumarasi());
    		int setReservedSeatFalseResult = ps.executeUpdate();
    		
    		return (deleteTicketResult > 0) && (deleteUserFromFlightResult > 0) && (setReservedSeatFalseResult > 0);
    	} catch (SQLException e) {
            Logger.getLogger(TicketOperations.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
	
	public static ObservableList<Ticket> getSearchedTickets(Passenger passenger, String search){
		String query = "SELECT ID, UCUSID, KOLTUKID, PNR, UCRET FROM BILETLER WHERE YOLCUID = ? AND PNR LIKE ?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, passenger.getId());
			ps.setString(2, "%" + search + "%");
			rs = ps.executeQuery();
			ObservableList<Ticket> list = FXCollections.observableArrayList();
			while(rs.next()) {
				int id = rs.getInt(1);
				int ucusId = rs.getInt(2);
				int koltukId = rs.getInt(3);
				String pnr = rs.getString(4);
				double ucret = rs.getDouble(5);

				Flight flight = FlightOperations.getFlight(ucusId);
				Seat seat = SeatOperations.getSeat(flight, koltukId);

				Ticket ticket = new Ticket(id, pnr, passenger, flight, seat, ucret);
				list.add(ticket);
			}
			return list;
		} catch (SQLException e) {
            Logger.getLogger(TicketOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
	}
	
	public static double getTotalIncome() {
		String query = "SELECT SUM(UCRET) FROM BILETLER";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			double totalIncome = 0;
			while(rs.next()) {
				totalIncome = rs.getDouble("SUM(UCRET)");
			}
			return totalIncome;
		} catch (SQLException e) {
            Logger.getLogger(TicketOperations.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
	}
	
	public static XYChart.Series<String, Double> getIncomeChart(){
		String query = "SELECT UC.KALKISTARIHI AS TARIH, B.UCRET "
				+ "FROM BILETLER B "
				+ "JOIN UCUSLAR UC ON UC.ID = B.UCUSID "
				+ "WHERE UC.KALKISTARIHI IS NOT NULL "
				+ "GROUP BY UC.KALKISTARIHI, B.UCRET "
				+ "ORDER BY TIMESTAMP(UC.KALKISTARIHI) ASC "
				+ "LIMIT 9;";
		
		XYChart.Series<String, Double> chart = new XYChart.Series<>();
		
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				chart.getData().add(new XYChart.Data<String, Double>(rs.getString(1), rs.getDouble(2)));
			}
			return chart;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
