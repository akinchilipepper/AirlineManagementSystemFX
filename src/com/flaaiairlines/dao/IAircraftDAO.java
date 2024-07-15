package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Aircraft;

import javafx.collections.ObservableList;

public interface IAircraftDAO {
	
	public ObservableList<Aircraft> getAllAircrafts() throws SQLException;
	
	public Aircraft getAircraftByModel(String aircraftModel) throws SQLException;
	
}
