package operations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Airport;
import system.DBConnection;

public class AirportOperations {

    private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static Statement st = null;
    private static ResultSet rs = null;

    public static Airport[] getAirports() {
        String query = "SELECT "
                + "H.ID, "
                + "S.SEHIR, "
                + "H.HAVAALANI, "
                + "H.IATAKODU, "
                + "K.KULLANIM "
                + "FROM "
                + "HAVAALANLARI H "
                + "JOIN SEHIRLER S ON S.ID = H.SEHIRID "
                + "JOIN HAVAALANIKULLANIM K ON K.ID = H.KULLANIMID "
                + "ORDER BY ID";
        try {
            ArrayList<Airport> airportList = new ArrayList<>();
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt(1);
                String sehir = rs.getString(2);
                String havaalani = rs.getString(3);
                String iatakodu = rs.getString(4);
                String kullanim = rs.getString(5);
                Airport airport = new Airport(id, sehir, havaalani, iatakodu, kullanim);
                airportList.add(airport);
            }

            Airport[] airports = airportList.toArray(Airport[]::new);

            return airports;
        } catch (SQLException ex) {
            Logger.getLogger(AirportOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
