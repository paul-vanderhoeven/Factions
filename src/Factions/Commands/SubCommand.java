package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

}
