package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Flight;
import model.Seat;
import system.DBConnection;

public class SeatOperations {
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
		
	public static Seat getSeat(Flight flight, int koltukId) {
    	String query = "SELECT ID, KOLTUKNUMARASI, KOLTUKTURU FROM KOLTUKLAR WHERE ID = ?";
    	Seat seat = null;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, koltukId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String koltuknumarasi = rs.getString(2);
                String koltukturu = rs.getString(3);
                seat = new Seat(id, koltuknumarasi, koltukturu, flight);
            }
            return seat;
        } catch (SQLException ex) {
            Logger.getLogger(SeatOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
