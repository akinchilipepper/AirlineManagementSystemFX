package com.flaaiairlines.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flaaiairlines.dao.FlightDAO;
import com.flaaiairlines.dao.ITicketDAO;
import com.flaaiairlines.dao.TicketDAO;
import com.flaaiairlines.model.Passenger;
import com.flaaiairlines.model.Ticket;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class TicketServices {

	public final ITicketDAO ticketDAO;

	public TicketServices() {
		this.ticketDAO = new TicketDAO();
	}

	public ObservableList<Ticket> getPassengerTickets(Passenger passenger) {
		try {
			return ticketDAO.getPassengerTickets(passenger);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean deleteTicket(Ticket ticket) {
		try {
			return ticketDAO.deleteTicket(ticket);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public ObservableList<Ticket> getSearchedTickets(Passenger passenger, String search) {
		try {
			return ticketDAO.getPassengerTickets(passenger);
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public double getTotalIncome() {
		try {
			return ticketDAO.getTotalIncome();
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return 0;
		}
	}

	public XYChart.Series<String, Double> getIncomeChart() {
		try {
			return ticketDAO.getIncomeChart();
		} catch (SQLException ex) {
			Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
