package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Passenger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PassengerDAO implements IPassengerDAO {
	private final Connection con;

	public PassengerDAO() {
		this.con = DatabaseConnection.getConnection();
	}

	@Override
	public ObservableList<Passenger> getPassengers() throws SQLException {
		String query = "SELECT * FROM YOLCULAR";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		ObservableList<Passenger> passengerList = FXCollections.observableArrayList();

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

		return passengerList;
	}

	@Override
	public ObservableList<Passenger> getSearchedPassengers(String search) throws SQLException {
		String query = "SELECT * FROM YOLCULAR " + "WHERE " + "AD LIKE ? OR " + "SOYAD LIKE ? OR "
				+ "CINSIYET LIKE ? OR " + "DOGUMTARIHI LIKE ?;";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, "%" + search + "%");
		ps.setString(2, "%" + search + "%");
		ps.setString(3, "%" + search + "%");
		ps.setString(4, "%" + search + "%");
		ResultSet rs = ps.executeQuery();
		
		ObservableList<Passenger> list = FXCollections.observableArrayList();
		
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
			list.add(passenger);
		}
		
		return list;
	}

	@Override
	public int getPassengersCountByFlight(Flight flight) throws SQLException {
		String query = "SELECT COUNT(*) FROM UCUSYOLCU WHERE UCUSID = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, flight.getId());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}

	}
	
	@Override
	public int getPassengersCount() throws SQLException {
		String query = "SELECT COUNT(*) FROM YOLCULAR";

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}

	}
}