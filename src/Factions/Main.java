package Factions;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import Factions.Commands.FCommand;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		
		registerCommands();
		getServer().getConsoleSender().sendMessage("§a=== Plugin Factions ===");
		getServer().getConsoleSender().sendMessage("§aPlugin activee !");
	}
	
	



	public void onDisable() {
		
	}
	
	private void registerCommands() {

		CommandExecutor fcommand = new FCommand();
		getCommand("f").setExecutor(fcommand);
	}

}
