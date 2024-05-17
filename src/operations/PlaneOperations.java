package operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Plane;
import system.DBConnection;

public class PlaneOperations {
	private static final DBConnection conn = new DBConnection();
    private static final Connection con = conn.connDb();
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    public static Plane[] getPlanes() {
		String query = "SELECT * FROM UCAKLAR";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			ArrayList<Plane> planeList = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String model = rs.getString(2);
                int kapasite = rs.getInt(3);

                Plane plane = new Plane(id, model, kapasite);

                planeList.add(plane);
            }

            Plane[] planes = planeList.toArray(Plane[]::new);
            
            return planes;			
		} catch(SQLException ex) {
			Logger.getLogger(AirportOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
		}
    }
    public static Plane getPlane(String planeModel) {
		String query = "SELECT * FROM UCAKLAR WHERE MODEL = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, planeModel);
			rs = ps.executeQuery();
			
			Plane plane = null;
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String model = rs.getString(2);
				int kapasite = rs.getInt(3);
				plane = new Plane(id, model, kapasite);
			}
			return plane;		
		} catch(SQLException ex) {
			Logger.getLogger(AirportOperations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
		}
    }
}
