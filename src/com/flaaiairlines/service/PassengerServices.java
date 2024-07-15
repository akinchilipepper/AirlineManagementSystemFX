package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.IPassengerDAO;
import com.flaaiairlines.dao.PassengerDAO;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Passenger;

import javafx.collections.ObservableList;

public class PassengerServices {

	private final IPassengerDAO passengerDAO;
	
	public PassengerServices() {
		this.passengerDAO = new PassengerDAO();
	}
	
	public ObservableList<Passenger> getPassengers() {
		try {
			return passengerDAO.getPassengers();
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public ObservableList<Passenger> getSearchedPassengers(String search) {
		try {
			return passengerDAO.getSearchedPassengers(search);
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public int getPassengersCountByFlight(Flight flight) {
		try {
			return passengerDAO.getPassengersCountByFlight(flight);
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public int getPassengersCount() {
		try {
			return passengerDAO.getPassengersCount();
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
}
