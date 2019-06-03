package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FJoin implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length==1) {
				p.sendMessage("§4Quelle faction ?");
				return false;
			}
			if(Faction.getPlayerFaction().containsKey(p)) {
				p.sendMessage("§4Vous êtes déjà dans une faction.");
				return false;
			}
			Faction f = Faction.getFaction(args[1]);
			if(f!=null) {
				f.addPlayer(p);
				p.sendMessage("§aVous avez rejoint la faction.");
				f.broadcast("Le joueur " + p.getName() + " a rejoint la faction.");
			}
			else {
				p.sendMessage("§4La faction n'existe pas.");
				return false;
			}
			
		}
		return false;
	}

}
