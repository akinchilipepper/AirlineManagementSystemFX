package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Airport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AirportDAO implements IAirportDAO {
	private final Connection con;

	public AirportDAO() {
		con = DatabaseConnection.getConnection();
	}
	
	@Override
	public ObservableList<Airport> getAllAirports() throws SQLException {
		String query = "SELECT " + "H.ID, " + "S.SEHIR, " + "H.HAVAALANI, " + "H.IATAKODU " + "FROM "
				+ "HAVAALANLARI H " + "JOIN SEHIRLER S ON S.ID = H.SEHIRID " + "ORDER BY ID";
		ObservableList<Airport> airportList = FXCollections.observableArrayList();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			int id = rs.getInt(1);
			String sehir = rs.getString(2);
			String havaalani = rs.getString(3);
			String iatakodu = rs.getString(4);
			Airport airport = new Airport(id, sehir, havaalani, iatakodu);
			airportList.add(airport);
		}

		return airportList;
	}

	@Override
	public Airport getAirportInfoByName(String airportName) throws SQLException {
		String query = "SELECT * FROM HAVAALANLARI H " + "JOIN " + "SEHIRLER S ON H.SEHIRID = S.ID "
				+ "WHERE HAVAALANI = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, airportName);
		ResultSet rs = ps.executeQuery();

		Airport airport = null;
		while (rs.next()) {
			int id = rs.getInt(1);
			String sehir = rs.getString(2);
			String havaalani = rs.getString(3);
			String iatakodu = rs.getString(4);
			airport = new Airport(id, sehir, havaalani, iatakodu);
		}
		
		return airport;
	}
}
