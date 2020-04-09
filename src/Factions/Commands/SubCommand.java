package Factions.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface SubCommand {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

	public List<String> getTabCompleter(Player sender);
	
	public boolean isCommandAdmin();
}
