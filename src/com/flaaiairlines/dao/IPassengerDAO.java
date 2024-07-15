package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Passenger;

import javafx.collections.ObservableList;

public interface IPassengerDAO {
	
	public ObservableList<Passenger> getPassengers() throws SQLException;
	
	public ObservableList<Passenger> getSearchedPassengers(String search) throws SQLException;
	
	public int getPassengersCountByFlight(Flight flight) throws SQLException;
	
	public int getPassengersCount() throws SQLException;
	
}
