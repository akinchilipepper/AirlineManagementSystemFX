package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASENAME = "airlinemsdb";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private Connection con = null;

    public Connection connDb() {
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASENAME
                + "?useUnicode=true&characterEncoding=utf8";
        try {
            con = DriverManager.getConnection(url, USERNAME, PASSWORD);
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            return con;
        }
    }
}