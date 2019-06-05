package Factions.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FCommand implements CommandExecutor {
	
	private static HashMap<String, SubCommand> subcommand = new HashMap<String, SubCommand>();

	static {
		subcommand.put("create", new FCreate());
		subcommand.put("status", new FStatus());
		subcommand.put("list", new FList());
		subcommand.put("join", new FJoin());
		subcommand.put("accept", new FAccept());
		subcommand.put("leave", new FLeave());
		subcommand.put("disband", new FDisband());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length>=1 && subcommand.containsKey(args[0])) {
			return subcommand.get(args[0]).onCommand(sender, cmd, label, args);
		}
		return false;
	}

}

//f create <nomFaction>
//f status
//f f status <faction>
//f list
//f join <faction>
//f accept
//f leave
//f disband <faction>