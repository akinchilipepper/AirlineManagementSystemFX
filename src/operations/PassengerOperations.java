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
import model.Flight;
import model.Passenger;

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
	
	public static ObservableList<Passenger> getSearchedPassengers(String search) {
		String query = "SELECT * FROM YOLCULAR "
				+ "WHERE "
				+ "AD LIKE ? OR "
				+ "SOYAD LIKE ? OR "
				+ "CINSIYET LIKE ? OR "
				+ "DOGUMTARIHI LIKE ?;";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, "%" + search + "%");
			ps.setString(2, "%" + search + "%");
			ps.setString(3, "%" + search + "%");
			ps.setString(4, "%" + search + "%");
			rs = ps.executeQuery();
			ObservableList<Passenger> list = FXCollections.observableArrayList();
			while(rs.next()) {
				int id = rs.getInt(1);
				String ad = rs.getString(2);
				String soyad = rs.getString(3);
				String telno = rs.getString(4);
				String e_posta = rs.getString(5);
				String cinsiyet = rs.getString(6);
				String dogumtarihi = rs.getString(7);
				String parola = rs.getString(8);

				Passenger passenger = new Passenger(id, ad, soyad, telno, e_posta, cinsiyet, dogumtarihi, parola);
				list.add(passenger);
			}
			return list;
		} catch(SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
    		return null;
    	}
	}
	
	public static int getPassengersCount(Flight flight) {
		String query = "SELECT COUNT(*) FROM UCUSYOLCU WHERE UCUSID = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, flight.getId());
			rs = ps.executeQuery();
			if(rs.next()) {return rs.getInt(1);}
			else {return 0;}
		} catch(SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
    		return 0;
    	}
	}
	
	public static int getPassengersCount() {
		String query = "SELECT COUNT(*) FROM YOLCULAR";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {return rs.getInt(1);}
			else {return 0;}
		} catch(SQLException ex) {
            Logger.getLogger(FlightOperations.class.getName()).log(Level.SEVERE, null, ex);
    		return 0;
    	}
	}
}