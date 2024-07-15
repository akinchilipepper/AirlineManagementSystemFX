package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Aircraft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AircraftDAO implements IAircraftDAO {
	private final Connection con;

	public AircraftDAO() {
		this.con = DatabaseConnection.getConnection();
	}
	
	@Override
	public ObservableList<Aircraft> getAllAircrafts() throws SQLException {

		String query = "SELECT * FROM UCAKLAR";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		ObservableList<Aircraft> aircraftList = FXCollections.observableArrayList();

		while (rs.next()) {
			int id = rs.getInt(1);
			String model = rs.getString(2);
			int capacity = rs.getInt(3);

			Aircraft aircraft = new Aircraft(id, model, capacity);

			aircraftList.add(aircraft);
		}
		
		return aircraftList;
	}
	
	@Override
	public Aircraft getAircraftByModel(String aircraftModel) throws SQLException {
		String query = "SELECT * FROM UCAKLAR WHERE MODEL = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, aircraftModel);
		ResultSet rs = ps.executeQuery();

		Aircraft aircraft = null;

		while (rs.next()) {
			int id = rs.getInt(1);
			String model = rs.getString(2);
			int capacity = rs.getInt(3);
			aircraft = new Aircraft(id, model, capacity);
		}
		return aircraft;

	}
}
