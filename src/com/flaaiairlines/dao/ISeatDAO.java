package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Seat;

public interface ISeatDAO {
	
	public Seat getSeatById(Flight flight, int koltukId) throws SQLException;
	
}
