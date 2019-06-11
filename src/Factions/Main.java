package Factions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Factions.Commands.CommandCompleter;
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
		TabCompleter tc = new CommandCompleter();
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/paul", "root", "");
			getServer().getConsoleSender().sendMessage("§aConnecté à la base de données.");
			
			try {
				Statement statement = connection.createStatement();
				
				int resultat = statement.executeUpdate("create table if not exists Factions (nom VARCHAR(100) primary key, "
						+ "chef VARCHAR(100) unique not null);");
				
				resultat = statement.executeUpdate("create table if not exists Membres (faction VARCHAR(100), "
						+ "joueur VARCHAR(100));");			
				resultat = statement.executeUpdate("alter table Membres add constraint PK_Membres primary key (faction, joueur);");
				resultat = statement.executeUpdate("alter table Membres add constraint FK_Faction foreign key (faction) references Factions(nom);");

				getServer().getConsoleSender().sendMessage("§aTables crées.");
			} catch (SQLException e) {
				getServer().getConsoleSender().sendMessage("§4Impossible de créer les tables.");
				e.printStackTrace();
			}
			
		} catch (SQLException e) {

			getServer().getConsoleSender().sendMessage("§4Erreur de connection à la base de données.");
			getServer().getConsoleSender().sendMessage("§4Désactivation du plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
			e.printStackTrace();
		}
		

	}

}
