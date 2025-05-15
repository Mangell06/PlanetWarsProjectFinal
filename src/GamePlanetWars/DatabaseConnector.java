package GamePlanetWars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connect() throws SQLException, ClassNotFoundException {
        String URL = "jdbc:mysql://localhost:3307/space_wars?serverTimezone=UTC";//studingtimerkain.ddnsking.com
        String USER = "root"; //remoteroot
        String password = "!Lolmillon303"; // Millon202
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, password);
    }
}