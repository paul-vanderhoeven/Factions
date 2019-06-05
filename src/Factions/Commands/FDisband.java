package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FDisband implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player && args.length == 2) {
			Player p = (Player) sender;
			if(p.isOp()) {
				Faction f = Faction.getFaction(args[1]);
				if(f == null) {
					p.sendMessage("§4La faction n'existe pas.");
					return true;
				}
				else {
					f.disband();
					return true;
				}
			}
			else {
				p.sendMessage("§4Vous n'êtes pas administrateur.");
				return true;
			}
		}
		return false;
	}

}
