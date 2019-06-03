package Factions.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FCommand implements CommandExecutor {
	
	private static HashMap<String, SubCommand> subcommand = new HashMap<String, SubCommand>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(args.length>1 && subcommand.containsKey(args[0])) {
			return subcommand.get(args[1]).onCommand(sender, cmd, label, args);
		}
		return false;
	}

}
