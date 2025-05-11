package GamePlanetWars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
	
	public static Connection connect() throws SQLException, ClassNotFoundException {
        String URL = "jdbc:mysql://studingtimerkain.ddnsking.com:3307/space_wars?serverTimezone=UTC";
        String USER = "remoteroot";
        String password = "Millon202";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, password);
    }
}















