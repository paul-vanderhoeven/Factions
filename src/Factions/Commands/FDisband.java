package Factions.Commands;

import java.util.LinkedList;
import java.util.List;

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
					p.sendMessage("�4La faction n'existe pas.");
					return true;
				}
				else {
					f.disband(p);
					return true;
				}
			}
			else {
				p.sendMessage("Seuls les administrateur peuvent supprimer les factions !");
				return true;
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
		return true;
	}

}
