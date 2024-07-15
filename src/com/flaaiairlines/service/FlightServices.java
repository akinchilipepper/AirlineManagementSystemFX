package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.FlightDAO;
import com.flaaiairlines.dao.IFlightDAO;
import com.flaaiairlines.model.Flight;

import javafx.collections.ObservableList;

public class FlightServices {
	
	private IFlightDAO flightDAO;

    public FlightServices() {
        this.flightDAO = new FlightDAO();
    }
	
	public int addFlight(String departureAirport, String arrivalAirport, String departureDate,
            String arrivalDate, String departureTime, String arrivalTime, String plane,
            String flightStatus, String flightNumber, int ticketPrice) {
		try {
			return flightDAO.addFlight(departureAirport, arrivalAirport, departureDate,
		            arrivalDate, departureTime, arrivalTime, plane,
		            flightStatus, flightNumber, ticketPrice); 
		} catch (SQLException ex) {
        	if ("1062".equals(ex.getSQLState()) || "23000".equals(ex.getSQLState())) {
                return 3;
            } else if ("22001".equals(ex.getSQLState()) || "22003".equals(ex.getSQLState())
            		|| "2207".equals(ex.getSQLState()) || "22018".equals(ex.getSQLState())) {
                return 2;
            } else if (ex.getErrorCode() == 1265) {
                return 2;
            } else {
            	Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
                return 1;
            }
        }
	}
	
	public ObservableList<Flight> getAllFlights() {
		try {
			return flightDAO.getAllFlights();
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public Flight getFlightInfoById(int ucusid) {
		try {
			return flightDAO.getFlightInfoById(ucusid);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public int getFlightsCount() {
		try {
			return flightDAO.getFlightsCount();
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public boolean cancelFlight(Flight flight) {
		try {
			return flightDAO.cancelFlight(flight);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
	public int updateFlightStatus(Flight flight, String dpTime, String arTime, String durum) {
		try {
			return flightDAO.updateFlightStatus(flight, dpTime, arTime, durum);
		} catch(SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            if ("22001".equals(ex.getSQLState()) || "22003".equals(ex.getSQLState())
            		|| "2207".equals(ex.getSQLState()) || "22018".equals(ex.getSQLState())) {
                return 2;
            } else if (ex.getErrorCode() == 1265) {
                return 2;
            } else {
            	Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
                return 1;
            }
    	}
	}

	public ObservableList<Flight> getSearchedFlightInfo(String search) {
		try {
			return flightDAO.getSearchedFlightInfo(search);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public boolean createFlightSeats(Flight flight) {
		try {
			return flightDAO.createFlightSeats(flight);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

}
