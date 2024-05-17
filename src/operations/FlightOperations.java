package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Flight;
import model.Plane;
import model.Airport;
import system.DBConnection;

public class FlightOperations {
	
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps;
    private static ResultSet rs;
	
    public static boolean addFlight(String departureAirport, String arrivalAirport, String departureDate,
    		String arrivalDate, String departureTime, String arrivalTime, String plane,
    		String flightStatus, String flightNumber, int ticketPrice
    ) {
    	String query = "INSERT INTO UCUSLAR "
    	        + "(UCAKID, KALKISYERIID, VARISYERIID, KALKISTARIHI, VARISTARIHI, KALKISZAMANI, VARISZAMANI, DURUMID, UCUSNO, BILETFIYATI) "
    	        + "VALUES ("
    	        + "(SELECT ID FROM UCAKLAR WHERE MODEL = ?), "
    	        + "(SELECT ID FROM HAVAALANLARI WHERE HAVAALANI = ?), "
    	        + "(SELECT ID FROM HAVAALANLARI WHERE HAVAALANI = ?), "
    	        + "?,?,?,?,"
    	        + "(SELECT ID FROM DURUMLAR WHERE UCUSDURUMU = ?), "
    	        + "?,?)";

    	try {
    		ps = con.prepareStatement(query);
    		ps.setString(1, plane);
    		ps.setString(2, departureAirport);
    		ps.setString(3, arrivalAirport);
    		ps.setString(4, departureDate);
    		ps.setString(5, arrivalDate);
    		ps.setString(6, departureTime);
    		ps.setString(7, arrivalTime);
    		ps.setString(8, flightStatus);
    		ps.setString(9, flightNumber);
    		ps.setInt(10, ticketPrice);
    		
    		rs = ps.getGeneratedKeys();
    		
    		if(rs.next()) {
    			int lastInsertedId = rs.getInt(1);
    			Flight flight = getFlight(lastInsertedId);
    			createSeats(flight);
    			return true;
    		} else {
    			return false;
    		}
    		
    	} catch(SQLException ex) {
    		Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
    		return false;
    	}
    }
	
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
                Plane ucak = PlaneOperations.getPlane(rs.getString(2));
                Airport kalkisYeri = AirportOperations.getAirport(rs.getString(3));
                Airport varisYeri = AirportOperations.getAirport(rs.getString(4));
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
	
	public static Flight getFlight(int ucusid) {
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
                + "DURUMLAR D ON D.ID = UC.DURUMID "
                + "WHERE "
                + "UC.ID = ?";
    	Flight flight = null;
    	try {
    		ps = con.prepareStatement(query);
    		ps.setInt(1, ucusid);
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			int id = rs.getInt(1);
                Plane ucak = PlaneOperations.getPlane(rs.getString(2));
                Airport kalkisYeri = AirportOperations.getAirport(rs.getString(3));
                Airport varisYeri = AirportOperations.getAirport(rs.getString(4));
                String kalkisTarihi = rs.getString(5);
                String varisTarihi = rs.getString(6);
                String kalkisZamani = rs.getString(7);
                String varisZamani = rs.getString(8);
                String durum = rs.getString(9);
                String ucusNo = rs.getString(10);

                flight = new Flight(id, ucak, kalkisYeri, varisYeri, kalkisTarihi, varisTarihi, kalkisZamani, varisZamani, durum, ucusNo);
    		}
    		return flight;
    	} catch(SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
    		return null;
    	}
    }
	
	public static boolean deleteFlight(int id) {
		return false;
	}
	
	public static boolean createSeats(Flight flight) {
		String query = "INSERT INTO koltuklar (UCUSID, KOLTUKNUMARASI, KOLTUKTURU, REZERVEDURUMU) "
				+ "SELECT "
				+ "    ? AS UCUSID, "
				+ "    CONCAT(sira, seat_column) AS KOLTUKNUMARASI, "
				+ "    CASE "
				+ "        WHEN seat_column IN ('A', 'F') THEN 'PENCERE' "
				+ "        WHEN seat_column IN ('B', 'E') THEN 'ORTA' "
				+ "        ELSE 'KORİDOR' "
				+ "    END AS KOLTUKTURU, "
				+ "    FALSE AS REZERVEDURUMU "
				+ "FROM "
				+ "    (SELECT "
				+ "        sira, "
				+ "        seat_column "
				+ "    FROM "
				+ "        (SELECT num AS sira FROM numbers WHERE num <= ?) AS sira "
				+ "    CROSS JOIN "
				+ "        (SELECT 'A' AS seat_column UNION ALL SELECT 'B' UNION ALL SELECT 'C' UNION ALL SELECT 'D' UNION ALL SELECT 'E' UNION ALL SELECT 'F') AS columns\r\n"
				+ "    ORDER BY sira, seat_column) AS koltuklar;";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, flight.getId());
			ps.setInt(2, flight.getUcak().getKapasite() / 6);
			int affectedRow = ps.executeUpdate();
			
			if(affectedRow > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
	}
}
