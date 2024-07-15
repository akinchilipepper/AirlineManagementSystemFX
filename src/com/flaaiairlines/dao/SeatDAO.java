package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Seat;

public class SeatDAO implements ISeatDAO {
	private final Connection con;

	public SeatDAO() {
		this.con = DatabaseConnection.getConnection();
	}
	
	@Override
	public Seat getSeatById(Flight flight, int koltukId) throws SQLException {
		String query = "SELECT ID, KOLTUKNUMARASI, KOLTUKTURU FROM KOLTUKLAR WHERE ID = ?";
		Seat seat = null;

		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, koltukId);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int id = rs.getInt(1);
			String koltuknumarasi = rs.getString(2);
			String koltukturu = rs.getString(3);
			seat = new Seat(id, koltuknumarasi, koltukturu, flight);
		}
		return seat;
	}
}
