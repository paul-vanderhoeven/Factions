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
			}
			else {
				if(f.getChef().equals(p)) {
					f.getDemande().accept();
					
				}
				else {
					p.sendMessage("§4Vous n'êtes pas le chef de votre faction.");
				}
			}
		}
		return false;
	}

}
