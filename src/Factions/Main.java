package Factions;

import java.sql.*;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Factions.Commands.FCommand;
import Factions.Events.onPlayerJoin;
import Factions.Events.onPvp;

public class Main extends JavaPlugin{
	
	static Plugin main;
	static Connection connection;
	
	public void onEnable() {
		main = this;
		
		registerCommands();
		registerListeners();
		
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aChargement...");
		createTables();
		getServer().getConsoleSender().sendMessage("§a=======================");
	}

	public void onDisable() {
		
	}
	
	private void registerCommands() {

		CommandExecutor fcommand = new FCommand();
		getCommand("f").setExecutor(fcommand);
		TabCompleter tc = new FCommand();
		getCommand("f").setTabCompleter(tc);
	}
	
	private void registerListeners() {

		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new onPlayerJoin(), this);
		pm.registerEvents(new onPvp(), this);
	}
	
	public static Plugin getMain() {
		return main;
	}
	
	public void createTables() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin", "phpmyadmin", "root");
			getServer().getConsoleSender().sendMessage("§aConnection a la base de donnees.");
			
			try {
				Statement statement = connection.createStatement();
				
				statement.executeUpdate("create table if not exists Factions (nom VARCHAR(100) primary key, "
						+ "chef VARCHAR(100) not null);");
				
				statement.executeUpdate("create table if not exists Membres (faction VARCHAR(100) references Factions(nom), "
						+ "joueur VARCHAR(100) primary key);");

				getServer().getConsoleSender().sendMessage("§aTables crees.");
			} catch (SQLException e) {
				getServer().getConsoleSender().sendMessage("§4Impossible de creer les tables.");
				e.printStackTrace();
			}
			
		} catch (SQLException e) {

			getServer().getConsoleSender().sendMessage("§4Erreur de connection a la base de donnees.");
			getServer().getConsoleSender().sendMessage("§4Desactivation du plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
		}
		

	}

}
