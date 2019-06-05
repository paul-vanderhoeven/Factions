package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FAccept implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			Faction f=Faction.getPlayerFaction(p);
			if(f==null) {
				p.sendMessage("§4Vous n'êtes pas dans une faction.");
				return true;
			}
			else {
				if(f.getChef().equals(p)) {
					if(f.getDemande() != null) {
						f.getDemande().accept();
						return true;
					}
					else {
						p.sendMessage("§4Il n'y a pas de demande pour rejoindre la faction.");
						return true;
					}
					
				}
				else {
					p.sendMessage("§4Vous n'êtes pas le chef de votre faction.");
					return true;
				}
			}
		}
		return false;
	}

}
