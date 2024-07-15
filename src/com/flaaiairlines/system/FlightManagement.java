package com.flaaiairlines.system;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.flaaiairlines.config.DatabaseConnection;

public class FlightManagement {

	public static void checkAndUpdateFlights() {
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtDelete = null;
        
        try {
        	String sqlUpdate = "UPDATE ucuslar SET DURUMID = CASE " +
                    "WHEN TIMESTAMP(CONCAT(KALKISTARIHI, ' ', KALKISZAMANI)) <= ? AND TIMESTAMP(CONCAT(VARISTARIHI, ' ', VARISZAMANI)) > ? THEN 3 " +
                    "WHEN TIMESTAMP(CONCAT(VARISTARIHI, ' ', VARISZAMANI)) <= ? THEN 5 " +
                    "ELSE DURUMID END, " +
                    "TAMAMLANMA_ZAMANI = CASE " +
                    "WHEN TIMESTAMP(CONCAT(VARISTARIHI, ' ', VARISZAMANI)) <= ? THEN ? " +
                    "ELSE TAMAMLANMA_ZAMANI END";
            pstmtUpdate = con.prepareStatement(sqlUpdate);

            Timestamp now = new Timestamp(new Date().getTime());
            pstmtUpdate.setTimestamp(1, now);
            pstmtUpdate.setTimestamp(2, now);
            pstmtUpdate.setTimestamp(3, now);
            pstmtUpdate.setTimestamp(4, now);
            pstmtUpdate.setTimestamp(5, now);
            pstmtUpdate.executeUpdate();

            // Delete flights that are 'Completed' and take more than 5 minutes to complete
            String sqlDelete = "DELETE FROM ucuslar WHERE DURUMID = 5 AND TAMAMLANMA_ZAMANI <= ?";
            pstmtDelete = con.prepareStatement(sqlDelete);
            pstmtDelete.setTimestamp(1, new Timestamp(now.getTime() - 5 * 60000)); // 5 minutes before
            pstmtDelete.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(FlightManagement.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (pstmtUpdate != null) pstmtUpdate.close();
                if (pstmtDelete != null) pstmtDelete.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
	}
	
}
