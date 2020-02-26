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
	
	private static Plugin main;
	
	// méthode au lancement du plugin
	public void onEnable() {
		main = this;
		
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aChargement...");
		
		registerCommands();
		registerListeners();
		
		SqlConnection.connect();
		SqlConnection.createTables();
		
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
	/**
	 * Méthode static retourne la connection à la base de donnée
	 * @return connection
	 */
}
