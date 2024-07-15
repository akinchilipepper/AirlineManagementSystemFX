package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.ISeatDAO;
import com.flaaiairlines.dao.SeatDAO;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Seat;

public class SeatServices {
	
	private final ISeatDAO seatDAO;
	
	public SeatServices() {
		this.seatDAO = new SeatDAO();
	}
	
	public Seat getSeatById(Flight flight, int koltukId) {
		try {
			return seatDAO.getSeatById(flight, koltukId);
		} catch (SQLException ex) {
			Logger.getLogger(AirportServices.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
