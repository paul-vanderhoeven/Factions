package Factions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

public class SqlConnection {
	
	private static Connection connection;
	private static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/plugins/factions.db";

	/**
	 * Constructeur
	 */

	public static Connection connect() {		
		try {
			connection = DriverManager.getConnection(url);
			System.out.println("Connecté");
				
		} catch (SQLException e) {
			System.out.println("§4Erreur de connection à la base de données");
			System.out.println("§4Désactivation du plugin");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			e.printStackTrace();
		}
		return connection;
	}

	public static void createDatabase() {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println("Base de données crée");
			}
		} catch (SQLException e) {
            System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Si la connection est établie alors on se déconnecte.
	 */
	public void disconnect() {
		if(isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	


	public static boolean isConnected() {
		return connection != null;
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	/**
	 * Crée les tables dans le bon ordre.
	 */
	public static void createTables() {
		if(isConnected()) {
			createTableJoueurs();
			createTableFactions();
			createTableAppartenir();
		}
	}
	
	/**
	 * Pré-requis: on considère que la connection à été effectué donc l'attribut connection n'est pas null
	 * Crée la table Joueurs et affiche un message si erreur
	 */
	private static void createTableJoueurs() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Joueurs (uuid varchar(100) PRIMARY KEY, nom varchar(100));");
		} catch (SQLException e) {
			System.out.println("§4Erreur lors de la création de la table Joueurs.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Pré-requis: on considère que la connection à été effectué donc l'attribut connection n'est pas null
	 * Crée la table Factions et affiche un message si erreur
	 */
	private static void createTableFactions() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Factions (nom varchar(100) PRIMARY KEY, uuidChef varchar(100),\n" + 
					"                                    CONSTRAINT fk_uuid FOREIGN KEY (uuidChef) REFERENCES Joueurs(uuid));");
		} catch (SQLException e) {
			System.out.println("§4Erreur lors de la création de la table Factions.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Pré-requis: on considère que la connection à été effectué donc l'attribut connection n'est pas null
	 * Crée la table Appartenir et affiche un message si erreur
	 */
	private static void createTableAppartenir() {
		try {			
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Appartenir (nom varchar(100), uuid varchar(100), \n" + 
					"                         PRIMARY KEY (nom, uuid), \n" + 
					"                         CONSTRAINT fk_nom_appartenir FOREIGN KEY (nom) REFERENCES Factions(nom),\n" + 
					"                         CONSTRAINT fk_uuid_appartenir	 FOREIGN KEY (uuid) REFERENCES Joueurs(uuid));");
		} catch (SQLException e) {
			System.out.println("§4Erreur lors de la création de la table Appartenir.");
			e.printStackTrace();
		}
	}

	
	
	public boolean existeJoueursUUID(String value) {
		try {
			
			String sql = "SELECT * FROM Joueurs WHERE uuid=?;";
			
			PreparedStatement sta = connection.prepareStatement(sql);
			
			sta.setString(1, value);
			boolean res = sta.execute();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getPlayerByUUID(String uuid) {
		
		try {
			String sql = "SELECT nom FROM Joueurs WHERE uuid=?;";
			PreparedStatement prep = connection.prepareStatement(sql);
		
			prep.setString(1, uuid);
			
			ResultSet name = prep.executeQuery();
			return name.toString();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
