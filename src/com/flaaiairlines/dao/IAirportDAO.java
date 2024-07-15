package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Airport;

import javafx.collections.ObservableList;

public interface IAirportDAO {
	
	public ObservableList<Airport> getAllAirports() throws SQLException;
	
	public Airport getAirportInfoByName(String airportName) throws SQLException;
	
}
