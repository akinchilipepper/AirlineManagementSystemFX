package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.AircraftDAO;
import com.flaaiairlines.dao.IAircraftDAO;
import com.flaaiairlines.model.Aircraft;

import javafx.collections.ObservableList;

public class AircraftServices {
	
	private final IAircraftDAO aircraftDAO;
	
	public AircraftServices() {
		this.aircraftDAO = new AircraftDAO();
	}
	
	public ObservableList<Aircraft> getAllAircrafts() {
		try {
			return aircraftDAO.getAllAircrafts();
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public Aircraft getAircraftByModel(String aircraftModel) {
		try {
			return aircraftDAO.getAircraftByModel(aircraftModel);
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
