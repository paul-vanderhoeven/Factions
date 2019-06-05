package Factions;

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
	
	public void onEnable() {
		main = this;
		
		registerCommands();
		registerListeners();
		
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aPlugin activee !");
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

}
