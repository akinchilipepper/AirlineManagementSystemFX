package system;

import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FlightManagement {
	private static DBConnection conn = new DBConnection();
	
	
	public static void checkAndUpdateFlights() {
		Connection con = conn.connDb();
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
            pstmtDelete.setTimestamp(1, new Timestamp(now.getTime() - 5 * 60 * 1000)); // 5 minutes before
            pstmtDelete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
