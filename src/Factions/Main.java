package Factions;

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
	public SqlConnection sql;
	
	// méthode au lancement du plugin
	public void onEnable() {
		main = this;
		

		
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aChargement...");
		
		registerCommands();
		registerListeners();
		
		sql = new SqlConnection("localhost", "phpmyadmin", "phpmyadmin", "root");
		sql.connection();
		sql.createTables();
		
		getServer().getConsoleSender().sendMessage("§aOK");
		getServer().getConsoleSender().sendMessage("§a=======================");
	}

	// méthode à la désactivation du plugin
	public void onDisable() {
		
	}
	
	/**
	 * Enregistrement des commandes
	 */
	private void registerCommands() {

		CommandExecutor fcommand = new FCommand();
		getCommand("f").setExecutor(fcommand);
		TabCompleter tc = new FCommand();
		getCommand("f").setTabCompleter(tc);
	}
	
	/**
	 * Enregistrement des évenements
	 */
	private void registerListeners() {

		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new onPlayerJoin(), this);
		pm.registerEvents(new onPvp(), this);
	}
	
	/**
	 * Méthode static retourne le main
	 * @return main
	 */
	public static Plugin getMain() {
		return main;
	}

}
