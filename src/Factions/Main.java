package Factions;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Factions.Commands.FCommand;
import Factions.Events.onPlayerJoin;
import Factions.Events.onPvp;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		
		registerCommands();
		registerListeners();
		
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aPlugin activee !");
	}

	public void onDisable() {
		
	}
	
	private void registerCommands() {

		CommandExecutor fcommand = new FCommand();
		getCommand("f").setExecutor(fcommand);
	}
	
	private void registerListeners() {

		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new onPlayerJoin(), this);
		pm.registerEvents(new onPvp(), this);
	}

}
