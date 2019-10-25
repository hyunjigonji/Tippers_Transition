package tippersTree;

import java.sql.*;
import com.mysql.jdbc.Statement;

public class Tree_DB {
	private static String dbURL = "jdbc:mysql://localhost:3306/TIPPERS?useSSL=false";
	private static String dbUser = "root";
	private static String dbPassword = "tippers2019";
	
	public static String connectDB() {
		
		try {
			// 1. Load JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//2. Open DBMS connection
			Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			
			java.sql.Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO SENSORS VALUES (\"Camera\", 1, \"Camera1\", ");
			ResultSet result = statement.executeQuery("SELECT Name FROM SENSORS");
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
		return "";
	}
}
