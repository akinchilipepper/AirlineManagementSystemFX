package com.flaaiairlines.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE_NAME = "airlinemsdb";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static Connection con = null;
    
    static {
    	String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME
                + "?useUnicode=true&characterEncoding=utf8";
        try {
            con = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        return con;
    }
}