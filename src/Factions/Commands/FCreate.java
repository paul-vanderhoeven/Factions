package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FCreate implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player && args.length>1) {
			Player p = (Player) sender;
			
			new Faction(args[1], p);
		}
		else {
			return false;
		}
		return true;
	}

}
