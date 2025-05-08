package GamePlanetWars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
	
	public static void main(String[] args) {
		String URL = "jdbc:mysql://studingtimerkain.ddnsking.com:3307/space_wars?serverTimezone=UTC";
		String USER = "remoteroot";
		String password = "Millon202";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver cargado correctante");
			
			Connection conn = DriverManager.getConnection(URL, USER, password);
			System.out.println("COnexión creada correctamente");
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * FROM Planet_stats");
			while (rs.next()) {
				System.out.println("Planeta: " + rs.getString("name"));
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no se ha cargado correctamente");
		} catch (SQLException e) {
			System.out.println("Conexión no creada correctamente");
		}
	}
	
	public void crear_planeta(Connection conn, int planet_id, String name) throws SQLException {
		String query = "INSERT INTO Planet_stats (planet_id, name, resource_metal_amount, "
				+ "resource_deuterion_amount, technology_defense_level, technology_attack_level, "
				+ "battles_counter, missile_launcher_remaining, ion_cannon_remaining, "
				+ "plasma_canon_remainint, light_hunter_remaining, heavy_hunter_remaining, "
				+ "battleship_remaining, armored_ship_remaining)"
				+ "VALUES (?, ?, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0)";
	
		PreparedStatement pdsmt = conn.prepareStatement(query);
		pdsmt.setInt(1, planet_id);
		pdsmt.setString(2, name);
		pdsmt.executeQuery();
		System.out.println("Planeta creado correctamente!");
	}
	
	public void construir_unidad(Connection conn, int planet_id, int metal_cost, int deuterion_cost, String unidad) throws SQLException {
		String query = "UPDATE Planet_stats SET resource_metal_amount = resouce_metal_amount - ?, "
				+ "resource_deuterion_amount = resource_deuterion_amount - ?, "
				+ unidad + "_remaining = " + unidad + "_remaining + 1 " + "WHERE planet_id = ?";
		
		PreparedStatement pdsmt = conn.prepareStatement(query);
		pdsmt.setInt(1, metal_cost);
		pdsmt.setInt(2, deuterion_cost);
		pdsmt.setInt(3, planet_id);
		pdsmt.executeQuery();
		System.out.println("Unidad " + unidad + " creada!");
	}
	
	public void iniciar_batalla(Connection conn, int planet_id, int numBatalla) throws SQLException {
		String query = "INSERT INTO Battle_stats (planet_id, num_battle, resource_metal_acquired, resource_deuterion_acquired) "
				+ "VALUES (?, ?, 0, 0)";
		
		PreparedStatement pdsmt = conn.prepareStatement(query);
		pdsmt.setInt(1, planet_id);
		pdsmt.setInt(2, numBatalla);
		pdsmt.executeQuery();
		
		String query2 = "UPDATE Planet_stats SET battles_counter = battles_counter + 1 WHERE planet_id = ?";
		PreparedStatement pdsmt2 = conn.prepareStatement(query2);
		pdsmt2.setInt(1, planet_id);
		System.out.println("Batalla iniciada y contador actualizado!!");
	}
	
	public void registrar_baja(Connection conn, int planet_id, int numBatalla, String unidad) throws SQLException {
		String query = "UPDATE Planet_battle_army SET " + unidad + "_destroyed = " + unidad + "_destroyed - 1, "
				+ "WHERE planet_id = ? AND num_battle = ?";
		
		PreparedStatement pdsmt = conn.prepareStatement(query);
		pdsmt.setInt(1, planet_id);
		pdsmt.setInt(2, numBatalla);
		pdsmt.executeQuery();
		System.out.println("Baja registrada!");
	}

}
