package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Flight;

import javafx.collections.ObservableList;

public interface IFlightDAO {
	
	public int addFlight(String departureAirport, String arrivalAirport, String departureDate, String arrivalDate,
			String departureTime, String arrivalTime, String plane, String flightStatus, String flightNumber,
			int ticketPrice) throws SQLException;
	
	public ObservableList<Flight> getAllFlights() throws SQLException;
	
	public Flight getFlightInfoById(int ucusid) throws SQLException;
	
	public int getFlightsCount() throws SQLException;
	
	public boolean cancelFlight(Flight flight) throws SQLException;
	
	public int updateFlightStatus(Flight flight, String dpTime, String arTime, String durum) throws SQLException;
	
	public ObservableList<Flight> getSearchedFlightInfo(String search) throws SQLException;
	
	public boolean createFlightSeats(Flight flight) throws SQLException;
	
}
