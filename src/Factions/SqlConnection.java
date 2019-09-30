package Factions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SqlConnection {
	
	private Connection connection;
	private Plugin main;
	private String host;
	private String database;
	private String user;
	private String pass;

	/**
	 * Constructeur
	 * Se connecte à la base de donnée
	 */
	public SqlConnection(String host, String database, String user, String pass) {
		main=Main.getMain();
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}
	
	public Connection connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, pass);
			main.getServer().getConsoleSender().sendMessage("§aConnecte a la base de donnees.");
				
		} catch (SQLException e) {
			main.getServer().getConsoleSender().sendMessage("§4Erreur de connection a la base de donnees.");
			main.getServer().getConsoleSender().sendMessage("§4Desactivation du plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			e.printStackTrace();
			main.getServer().getConsoleSender().sendMessage(e.getMessage());
		}
		return connection;
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
	


	public boolean isConnected() {
		return connection != null;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Crée les tables dans le bon ordre.
	 */
	public void createTables() {
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
	private void createTableJoueurs() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Joueurs (uuid varchar(100) PRIMARY KEY, nom varchar(100));");
		} catch (SQLException e) {
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Joueurs.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Pré-requis: on considère que la connection à été effectué donc l'attribut connection n'est pas null
	 * Crée la table Factions et affiche un message si erreur
	 */
	private void createTableFactions() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Factions (nom varchar(100) PRIMARY KEY, uuidChef varchar(100),\n" + 
					"                                    CONSTRAINT fk_uuid FOREIGN KEY (uuidChef) REFERENCES Joueurs(uuid));");
		} catch (SQLException e) {
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Factions.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Pré-requis: on considère que la connection à été effectué donc l'attribut connection n'est pas null
	 * Crée la table Appartenir et affiche un message si erreur
	 */
	private void createTableAppartenir() {
		try {			
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Appartenir (nom varchar(100), uuid varchar(100), \n" + 
					"                         PRIMARY KEY (nom, uuid), \n" + 
					"                         CONSTRAINT fk_nom_appartenir FOREIGN KEY (nom) REFERENCES Factions(nom),\n" + 
					"                         CONSTRAINT fk_uuid_appartenir	 FOREIGN KEY (uuid) REFERENCES Joueurs(uuid));");
		} catch (SQLException e) {
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Appartenir.");
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
