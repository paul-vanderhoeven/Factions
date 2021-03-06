package Factions.Commands;

import java.util.LinkedList;
import java.util.List;

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
				
				if(Faction.estDansUneFaction(p)) {
					p.sendMessage(Faction.getPlayerFaction(p).toString());
					return true;
				}
				else {
					p.sendMessage("�4Vous n'�tes pas dans une faction");
					return true;
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
				p.sendMessage("�4La faction " + args[1] + " n'existe pas");
			}
		}

		return false;
	}

	@Override
	public List<String> getTabCompleter(Player p) {

		LinkedList<String> tab = new LinkedList<String>();
		
		for(Faction f : Faction.getListFaction()) {
			tab.add(f.getNom());
		}
		return tab;
	}

	@Override
	public boolean isCommandAdmin() {
		return false;
	}
	
	

}
