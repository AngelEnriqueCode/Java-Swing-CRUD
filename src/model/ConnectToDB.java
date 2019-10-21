package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectToDB {
	
	public static Connection getConnection() {
		String url = "jdbc:sqlite:database";
				Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			conn = null;
		}
		return conn;
	}
}
