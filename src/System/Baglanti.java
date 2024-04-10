package System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;

public class Baglanti {
	private String kullaniciAdi = "root";
	private String parola = "";
	private String dbAdi = "airlinemsdb";
	private String host = "localhost";
	private int port = 3306;
	private Connection con = null;
	private Statement st = null;
	
	public Baglanti() {
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbAdi
				+ "?useUnicode=true&characterEncoding=utf8";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException ex){
			System.out.println("Driver bulunamadı");
		}
		
		try {
			con = DriverManager.getConnection(url,kullaniciAdi,parola);
			System.out.println("Bağlantı başarılı");
		}catch(SQLException ex) {
			System.out.println("Bağlantı başarısız");
			Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	public void ucusSil(int ucusid) {
	    String sorgu = "DELETE FROM UCUSLAR WHERE ID = " + ucusid;
	    try {
	        Statement st = con.createStatement();
	        int affectedRows = st.executeUpdate(sorgu);
	        if (affectedRows > 0) {
	            System.out.println("Uçuş silindi");
	        } else {
	            System.out.println("Uçuş bulunamadı veya silinemedi");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public void ucuslariGoster() {
		String sorgu = "SELECT"
				+ "    U.MODEL,"
				+ "    H1.H_ADI AS KALKIS,"
				+ "    H2.H_ADI AS VARIS,"
				+ "    UC.KZAMANI,"
				+ "    UC.VZAMANI"
				+ "FROM"
				+ "    UCUSLAR UC"
				+ "JOIN"
				+ "    havaalanlari H1 ON H1.ID = UC.KALKISYERID"
				+ "JOIN"
				+ "    havaalanlari H2 ON H2.ID = UC.VARISYERIDn"
				+ "JOIN"
				+ "	   ucaklar U ON U.ID = UC.UÇAKID";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			while(rs.next()) {
				String model = rs.getString("MODEL");
				String kalkis = rs.getString("KALKIS");
				String varis = rs.getString("VARIS");
				Timestamp kzaman = rs.getTimestamp("KZAMANI");
				Timestamp vzaman = rs.getTimestamp("VZAMANI");
				
				System.out.println("Uçak Modeli : " + model + "\nKalkış Yeri : " + kalkis + "\nVarış Yeri : " + varis + "\nKalkış zamanı : " + kzaman.toString() + "\nVarış zamanı : " + vzaman.toString());
			}
		}catch(SQLException e) {
			Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE,null,e);
		}
	}
}
