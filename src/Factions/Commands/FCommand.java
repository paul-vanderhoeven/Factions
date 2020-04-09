package Factions.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class FCommand implements CommandExecutor, TabCompleter {
	
	private static HashMap<String, SubCommand> subcommand = new HashMap<String, SubCommand>();

	static {
		subcommand.put("create", new FCreate());
		subcommand.put("status", new FStatus());
		subcommand.put("list", new FList());
		subcommand.put("join", new FJoin());
		subcommand.put("invite", new FInvite());
		subcommand.put("leave", new FLeave());
		subcommand.put("disband", new FDisband());
		subcommand.put("leader", new FLeader());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length>=1 && subcommand.containsKey(args[0])) {
			return subcommand.get(args[0]).onCommand(sender, cmd, label, args);
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		List<String> tab = new ArrayList<String>();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length==1) {
				for (String s : subcommand.keySet()) {
					if(!subcommand.get(s).isCommandAdmin() || p.isOp()) {
						tab.add(s);
					}
				}
			}
			else {
				if(subcommand.containsKey(args[0])) {
					tab.addAll(subcommand.get(args[0]).getTabCompleter(p));
				}
			}
		}
		return tab;
	}

}

//f create <nomFaction>
//f status
//f f status <faction>
//f list
//f invite <joueur>
//f join <faction>
//f leave
//f disband <faction>