package com.flaaiairlines.dao;

import java.sql.SQLException;

import com.flaaiairlines.model.Passenger;
import com.flaaiairlines.model.Ticket;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public interface ITicketDAO {
	
	public ObservableList<Ticket> getPassengerTickets(Passenger passenger) throws SQLException;
	
	public boolean deleteTicket(Ticket ticket) throws SQLException;
	
	public ObservableList<Ticket> getSearchedTickets(Passenger passenger, String search) throws SQLException;
	
	public double getTotalIncome() throws SQLException;
	
	public XYChart.Series<String, Double> getIncomeChart() throws SQLException;
	
}
