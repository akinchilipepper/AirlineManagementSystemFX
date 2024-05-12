package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Flight;
import system.DBConnection;

public class FlightOperations {
	
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps;
    private static ResultSet rs;
	
	
	public static Flight[] getFlights() {       
        String query = "SELECT "
                + "UC.ID, "
                + "UCAK.MODEL, "
                + "H1.HAVAALANI, "
                + "H2.HAVAALANI, "
                + "UC.KALKISTARIHI, "
                + "UC.VARISTARIHI,"
                + "UC.KALKISZAMANI, "
                + "UC.VARISZAMANI, "
                + "D.UCUSDURUMU,"
                + "UC.UCUSNO "
                + "FROM "
                + "UCUSLAR UC "
                + "JOIN "
                + "UCAKLAR UCAK ON UCAK.ID = UC.UCAKID "
                + "JOIN "
                + "HAVAALANLARI H1 ON H1.ID = UC.KALKISYERIID "
                + "JOIN "
                + "HAVAALANLARI H2 ON H2.ID = UC.VARISYERIID "
                + "JOIN "
                + "DURUMLAR D ON D.ID = UC.DURUMID; ";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList<Flight> flightList = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String ucak = rs.getString(2);
                String kalkisYeri = rs.getString(3);
                String varisYeri = rs.getString(4);
                String kalkisTarihi = rs.getString(5);
                String varisTarihi = rs.getString(6);
                String kalkisZamani = rs.getString(7);
                String varisZamani = rs.getString(8);
                String durum = rs.getString(9);
                String ucusNo = rs.getString(10);

                Flight flight = new Flight(id, ucak, kalkisYeri, varisYeri, kalkisTarihi, varisTarihi, kalkisZamani, varisZamani, durum, ucusNo);

                flightList.add(flight);
            }

            Flight[] flights = flightList.toArray(Flight[]::new);

            return flights;
        } catch (SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
