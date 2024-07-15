package com.flaaiairlines.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.flaaiairlines.config.DatabaseConnection;
import com.flaaiairlines.model.Airport;
import com.flaaiairlines.model.Flight;
import com.flaaiairlines.service.AircraftServices;
import com.flaaiairlines.service.AirportServices;
import com.flaaiairlines.model.Aircraft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FlightDAO implements IFlightDAO {

	private final Connection con;
	private final AirportServices airportService;
	private final AircraftServices aircraftService;

	public FlightDAO() {
		this.con = DatabaseConnection.getConnection();
		this.airportService = new AirportServices();
		this.aircraftService = new AircraftServices();
	}
	
	@Override
	public int addFlight(String departureAirport, String arrivalAirport, String departureDate, String arrivalDate,
			String departureTime, String arrivalTime, String plane, String flightStatus, String flightNumber,
			int ticketPrice) throws SQLException {

		String query = "INSERT INTO UCUSLAR "
				+ "(UCAKID, KALKISYERIID, VARISYERIID, KALKISTARIHI, VARISTARIHI, KALKISZAMANI, VARISZAMANI, DURUMID, UCUSNO, BILETFIYATI) "
				+ "VALUES (" + "(SELECT ID FROM UCAKLAR WHERE MODEL = ?), "
				+ "(SELECT ID FROM HAVAALANLARI WHERE HAVAALANI = ?), "
				+ "(SELECT ID FROM HAVAALANLARI WHERE HAVAALANI = ?), " + "?,?,?,?,"
				+ "(SELECT ID FROM DURUMLAR WHERE UCUSDURUMU = ?), " + "?,?)";

		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, plane);
		ps.setString(2, departureAirport);
		ps.setString(3, arrivalAirport);
		ps.setString(4, departureDate);
		ps.setString(5, arrivalDate);
		ps.setString(6, departureTime);
		ps.setString(7, arrivalTime);
		ps.setString(8, flightStatus);
		ps.setString(9, flightNumber);
		ps.setInt(10, ticketPrice);

		int affectedRows = ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();

		if (affectedRows > 0) {
			if (rs.next()) {
				int lastInsertedId = rs.getInt(1);
				Flight flight = getFlightInfoById(lastInsertedId);
				createFlightSeats(flight);
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}

	}

	public ObservableList<Flight> getAllFlights() throws SQLException {
		String query = "SELECT " + "UC.ID, " + "UCAK.MODEL, " + "H1.HAVAALANI, " + "H2.HAVAALANI, "
				+ "UC.KALKISTARIHI, " + "UC.VARISTARIHI," + "UC.KALKISZAMANI, " + "UC.VARISZAMANI, " + "D.UCUSDURUMU, "
				+ "UC.UCUSNO, " + "UC.BILETFIYATI " + "FROM " + "UCUSLAR UC " + "JOIN "
				+ "UCAKLAR UCAK ON UCAK.ID = UC.UCAKID " + "JOIN " + "HAVAALANLARI H1 ON H1.ID = UC.KALKISYERIID "
				+ "JOIN " + "HAVAALANLARI H2 ON H2.ID = UC.VARISYERIID " + "JOIN "
				+ "DURUMLAR D ON D.ID = UC.DURUMID; ";

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		ObservableList<Flight> flightList = FXCollections.observableArrayList();

		while (rs.next()) {
			int id = rs.getInt(1);
			Aircraft ucak = aircraftService.getAircraftByModel(rs.getString(2));
			Airport kalkisYeri = airportService.getAirportInfoByName(rs.getString(3));
			Airport varisYeri = airportService.getAirportInfoByName(rs.getString(4));
			String kalkisTarihi = rs.getString(5);
			String varisTarihi = rs.getString(6);
			String kalkisZamani = rs.getString(7);
			String varisZamani = rs.getString(8);
			String durum = rs.getString(9);
			String ucusNo = rs.getString(10);
			int biletFiyati = rs.getInt(11);

			Flight flight = new Flight(id, ucak, kalkisYeri, varisYeri, kalkisTarihi, varisTarihi, kalkisZamani,
					varisZamani, durum, ucusNo, biletFiyati);

			flightList.add(flight);
		}

		return flightList;

	}
	
	@Override
	public Flight getFlightInfoById(int ucusid) throws SQLException {
		String query = "SELECT " + "UC.ID, " + "UCAK.MODEL, " + "H1.HAVAALANI, " + "H2.HAVAALANI, "
				+ "UC.KALKISTARIHI, " + "UC.VARISTARIHI," + "UC.KALKISZAMANI, " + "UC.VARISZAMANI, " + "D.UCUSDURUMU, "
				+ "UC.UCUSNO, " + "UC.BILETFIYATI " + "FROM " + "UCUSLAR UC " + "JOIN "
				+ "UCAKLAR UCAK ON UCAK.ID = UC.UCAKID " + "JOIN " + "HAVAALANLARI H1 ON H1.ID = UC.KALKISYERIID "
				+ "JOIN " + "HAVAALANLARI H2 ON H2.ID = UC.VARISYERIID " + "JOIN " + "DURUMLAR D ON D.ID = UC.DURUMID "
				+ "WHERE " + "UC.ID = ?";
		Flight flight = null;

		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, ucusid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			Aircraft ucak = aircraftService.getAircraftByModel(rs.getString(2));
			Airport kalkisYeri = airportService.getAirportInfoByName(rs.getString(3));
			Airport varisYeri = airportService.getAirportInfoByName(rs.getString(4));
			String kalkisTarihi = rs.getString(5);
			String varisTarihi = rs.getString(6);
			String kalkisZamani = rs.getString(7);
			String varisZamani = rs.getString(8);
			String durum = rs.getString(9);
			String ucusNo = rs.getString(10);
			int biletFiyati = rs.getInt(11);

			flight = new Flight(id, ucak, kalkisYeri, varisYeri, kalkisTarihi, varisTarihi, kalkisZamani, varisZamani,
					durum, ucusNo, biletFiyati);
		}
		return flight;

	}

	@Override
	public int getFlightsCount() throws SQLException {
		String query = "SELECT COUNT(*) FROM UCUSLAR";

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else
			return 0;

	}

	@Override
	public boolean cancelFlight(Flight flight) throws SQLException {
		String deleteTicketsQuery = "DELETE FROM BILETLER WHERE UCUSID = ?";
		String deletePassengersFromFlightQuery = "DELETE FROM UCUSYOLCU WHERE UCUSID = ?";
		String deleteSeatsQuery = "DELETE FROM KOLTUKLAR WHERE UCUSID = ?";
		String deleteFlightQuery = "DELETE FROM UCUSLAR WHERE ID = ?";

		PreparedStatement ps = con.prepareStatement(deleteTicketsQuery);
		ps.setInt(1, flight.getId());
		ps.executeUpdate();

		ps = con.prepareStatement(deletePassengersFromFlightQuery);
		ps.setInt(1, flight.getId());
		ps.executeUpdate();

		ps = con.prepareStatement(deleteSeatsQuery);
		ps.setInt(1, flight.getId());
		ps.executeUpdate();

		ps = con.prepareStatement(deleteFlightQuery);
		ps.setInt(1, flight.getId());

		int result = ps.executeUpdate();
		
		return result > 0;

	}

	@Override
	public int updateFlightStatus(Flight flight, String dpTime, String arTime, String durum) throws SQLException {
		String query = "UPDATE UCUSLAR SET KALKISZAMANI = ?, VARISZAMANI = ?, "
				+ "DURUMID = (SELECT ID FROM DURUMLAR WHERE UCUSDURUMU = ?) " + "WHERE ID = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, dpTime);
		ps.setString(2, arTime);
		ps.setString(3, durum);
		ps.setInt(4, flight.getId());
		int result = ps.executeUpdate();

		if (result > 0)
			return 0;
		else
			return 1;

	}

	@Override
	public ObservableList<Flight> getSearchedFlightInfo(String search) throws SQLException {
		String query = "SELECT " + "UC.ID, " + "UCAK.MODEL, " + "H1.HAVAALANI, " + "H2.HAVAALANI, "
				+ "UC.KALKISTARIHI, " + "UC.VARISTARIHI," + "UC.KALKISZAMANI, " + "UC.VARISZAMANI, " + "D.UCUSDURUMU, "
				+ "UC.UCUSNO, " + "UC.BILETFIYATI " + "FROM " + "UCUSLAR UC " + "JOIN "
				+ "UCAKLAR UCAK ON UCAK.ID = UC.UCAKID " + "JOIN " + "HAVAALANLARI H1 ON H1.ID = UC.KALKISYERIID "
				+ "JOIN " + "HAVAALANLARI H2 ON H2.ID = UC.VARISYERIID " + "JOIN " + "DURUMLAR D ON D.ID = UC.DURUMID "
				+ "WHERE " + "UC.UCUSNO LIKE ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%" + search + "%");
			ResultSet rs = ps.executeQuery();
			ObservableList<Flight> list = FXCollections.observableArrayList();
			while (rs.next()) {
				int id = rs.getInt(1);
				Aircraft ucak = aircraftService.getAircraftByModel(rs.getString(2));
				Airport kalkisYeri = airportService.getAirportInfoByName(rs.getString(3));
				Airport varisYeri = airportService.getAirportInfoByName(rs.getString(4));
				String kalkisTarihi = rs.getString(5);
				String varisTarihi = rs.getString(6);
				String kalkisZamani = rs.getString(7);
				String varisZamani = rs.getString(8);
				String durum = rs.getString(9);
				String ucusNo = rs.getString(10);
				int biletFiyati = rs.getInt(11);

				Flight flight = new Flight(id, ucak, kalkisYeri, varisYeri, kalkisTarihi, varisTarihi, kalkisZamani,
						varisZamani, durum, ucusNo, biletFiyati);
				list.add(flight);
			}
			return list;
		
	}

	@Override
	public boolean createFlightSeats(Flight flight) throws SQLException {
		String query = "INSERT INTO koltuklar (UCUSID, KOLTUKNUMARASI, KOLTUKTURU, REZERVEDURUMU) " + "SELECT "
				+ "    ? AS UCUSID, " + "    CONCAT(sira, seat_column) AS KOLTUKNUMARASI, " + "    CASE "
				+ "        WHEN seat_column IN ('A', 'F') THEN 'PENCERE' "
				+ "        WHEN seat_column IN ('B', 'E') THEN 'ORTA' " + "        ELSE 'KORİDOR' "
				+ "    END AS KOLTUKTURU, " + "    FALSE AS REZERVEDURUMU " + "FROM " + "    (SELECT "
				+ "        sira, " + "        seat_column " + "    FROM "
				+ "        (SELECT num AS sira FROM numbers WHERE num <= ?) AS sira " + "    CROSS JOIN "
				+ "        (SELECT 'A' AS seat_column UNION ALL SELECT 'B' UNION ALL SELECT 'C' UNION ALL SELECT 'D' UNION ALL SELECT 'E' UNION ALL SELECT 'F') AS columns\r\n"
				+ "    ORDER BY sira, seat_column) AS koltuklar;";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, flight.getId());
		ps.setInt(2, flight.getUcak().getKapasite() / 6);
		int affectedRow = ps.executeUpdate();

		return affectedRow > 0;
	}
}
