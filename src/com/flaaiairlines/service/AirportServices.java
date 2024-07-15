package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.AirportDAO;
import com.flaaiairlines.dao.IAirportDAO;
import com.flaaiairlines.model.Airport;

import javafx.collections.ObservableList;

public class AirportServices {
	
	private final IAirportDAO airportDAO;
	
	public AirportServices() {
		this.airportDAO = new AirportDAO();	
	}
	
	public ObservableList<Airport> getAllAirports() {
		try {
			return airportDAO.getAllAirports();
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public Airport getAirportInfoByName(String airportName) {
		try {
			return airportDAO.getAirportInfoByName(airportName);
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
}
