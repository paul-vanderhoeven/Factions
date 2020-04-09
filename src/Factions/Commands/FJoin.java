package Factions.Commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FJoin implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length == 1) {
				p.sendMessage("Quelle faction ?");
			} else if(!Faction.existe(args[1])) {
				p.sendMessage("La faction n'existe pas !");
			} else {
				Faction.getFaction(args[1]).join(p);
			}
		}
		return true;
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
