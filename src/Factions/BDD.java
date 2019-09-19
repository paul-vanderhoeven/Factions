package Factions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BDD {
	
	private static Connection connection;
	private static Plugin main = Main.getMain();;
	
	/**
	 * Etablie la connection à la base de donnée et affiche un message si erreur
	 * Base de donnée mysql.
	 */
	public static void connection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin", "phpmyadmin", "root");
			main.getServer().getConsoleSender().sendMessage("§aConnecte a la base de donnees.");
			
		} catch (SQLException e) {

			main.getServer().getConsoleSender().sendMessage("§4Erreur de connection a la base de donnees.");
			main.getServer().getConsoleSender().sendMessage("§4Desactivation du plugin.");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			e.printStackTrace();
			main.getServer().getConsoleSender().sendMessage(e.getMessage());
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	/**
	 * Crée les tables dans le bon ordre.
	 */
	public static void createTables() {
		createTableJoueurs();
		createTableFactions();
		createTableAppartenir();
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
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Joueurs.");
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
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Factions.");
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
			main.getServer().getConsoleSender().sendMessage("§4Erreur lors de la creation de la table Appartenir.");
			e.printStackTrace();
		}
	}
}
