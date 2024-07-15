package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.model.Passenger;
import com.flaaiairlines.model.Seat;
import com.flaaiairlines.model.Ticket;
import com.flaaiairlines.service.FlightServices;
import com.flaaiairlines.service.SeatServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class TicketDAO implements ITicketDAO {

	private final Connection con;
	private final FlightServices flightService;
	private final SeatServices seatService;

	public TicketDAO() {
		this.con = DatabaseConnection.getConnection();
		this.flightService = new FlightServices();
		this.seatService = new SeatServices();
	}
	
	@Override
	public ObservableList<Ticket> getPassengerTickets(Passenger passenger) throws SQLException {
		String query = "SELECT ID, UCUSID, KOLTUKID, PNR, UCRET FROM BILETLER WHERE YOLCUID = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, passenger.getId());
		ResultSet rs = ps.executeQuery();

		ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

		while (rs.next()) {
			int id = rs.getInt(1);
			int ucusId = rs.getInt(2);
			int koltukId = rs.getInt(3);
			String pnr = rs.getString(4);
			double ucret = rs.getDouble(5);

			Flight flight = flightService.getFlightInfoById(ucusId);
			Seat seat = seatService.getSeatById(flight, koltukId);

			Ticket ticket = new Ticket(id, pnr, passenger, flight, seat, ucret);
			ticketList.add(ticket);
		}

		return ticketList;

	}

	@Override
	public boolean deleteTicket(Ticket ticket) throws SQLException {
		String deleteTicketQuery = "DELETE FROM BILETLER WHERE ID = ?";
		String deleteUserFromFlightQuery = "DELETE FROM UCUSYOLCU WHERE UCUSID = ? AND YOLCUID = ?";
		String setReservedSeatFalseQuery = "UPDATE KOLTUKLAR SET REZERVEDURUMU = 0 "
				+ "WHERE UCUSID = ? AND KOLTUKNUMARASI = ?";

		PreparedStatement ps = con.prepareStatement(deleteTicketQuery);
		ps.setInt(1, ticket.getId());
		int deleteTicketResult = ps.executeUpdate();

		ps = con.prepareStatement(deleteUserFromFlightQuery);
		ps.setInt(1, ticket.getFlight().getId());
		ps.setInt(2, ticket.getPassenger().getId());
		int deleteUserFromFlightResult = ps.executeUpdate();

		ps = con.prepareStatement(setReservedSeatFalseQuery);
		ps.setInt(1, ticket.getSeat().getFlight().getId());
		ps.setString(2, ticket.getSeat().getKoltuknumarasi());
		int setReservedSeatFalseResult = ps.executeUpdate();

		return (deleteTicketResult > 0) && (deleteUserFromFlightResult > 0) && (setReservedSeatFalseResult > 0);

	}

	@Override
	public ObservableList<Ticket> getSearchedTickets(Passenger passenger, String search) throws SQLException {
		String query = "SELECT ID, UCUSID, KOLTUKID, PNR, UCRET FROM BILETLER WHERE YOLCUID = ? AND PNR LIKE ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, passenger.getId());
		ps.setString(2, "%" + search + "%");
		ResultSet rs = ps.executeQuery();
		ObservableList<Ticket> list = FXCollections.observableArrayList();
		while (rs.next()) {
			int id = rs.getInt(1);
			int ucusId = rs.getInt(2);
			int koltukId = rs.getInt(3);
			String pnr = rs.getString(4);
			double ucret = rs.getDouble(5);

			Flight flight = flightService.getFlightInfoById(ucusId);
			Seat seat = seatService.getSeatById(flight, koltukId);

			Ticket ticket = new Ticket(id, pnr, passenger, flight, seat, ucret);
			list.add(ticket);
		}
		return list;

	}
	
	@Override
	public double getTotalIncome() throws SQLException {
		String query = "SELECT SUM(UCRET) FROM BILETLER";

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		double totalIncome = 0;
		while (rs.next()) {
			totalIncome = rs.getDouble("SUM(UCRET)");
		}
		return totalIncome;

	}
	
	@Override
	public XYChart.Series<String, Double> getIncomeChart() throws SQLException {
		String query = "SELECT UC.KALKISTARIHI AS TARIH, B.UCRET " + "FROM BILETLER B "
				+ "JOIN UCUSLAR UC ON UC.ID = B.UCUSID " + "WHERE UC.KALKISTARIHI IS NOT NULL "
				+ "GROUP BY UC.KALKISTARIHI, B.UCRET " + "ORDER BY TIMESTAMP(UC.KALKISTARIHI) ASC " + "LIMIT 9;";

		XYChart.Series<String, Double> chart = new XYChart.Series<>();

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			chart.getData().add(new XYChart.Data<String, Double>(rs.getString(1), rs.getDouble(2)));
		}
		return chart;

	}
}
