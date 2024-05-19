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

public class TicketOperations {
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps;
    private static ResultSet rs;
    
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
    		ps.setInt(2, ticket.getUser().getId());
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
}
