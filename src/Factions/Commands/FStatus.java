package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FStatus implements SubCommand {
	
	/**
	 * Commandes 	/f status
	 * 				/f status <nom>
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			//f status => faction du joueur
			
			if(args.length==1) {
				
				if(Faction.getPlayerFaction().containsKey(p)) {
					p.sendMessage(Faction.getPlayerFaction(p).toString());
					return true;
				}
				else {
					p.sendMessage("§4Vous n'êtes pas dans une faction");
					return false;
				}	
			}
			
			//f status <nom>
			
			else if(args.length==2) {
				
				for(Faction f : Faction.getListFaction()) {
					if(f.getNom().equalsIgnoreCase(args[1])) {
						p.sendMessage(f.toString());
						return true;
					}
				}
				p.sendMessage("§4La faction " + args[1] + " n'existe pas");
			}
		}

		return false;
	}

}
