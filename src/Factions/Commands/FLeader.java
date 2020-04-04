package Factions.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FLeader implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player && args.length == 2) {
			Player p = (Player) sender;
			
			if(Faction.estDansUneFaction(p)) {
				Faction f = Faction.getPlayerFaction(p);

				Player ciblePlayer = f.getPlayer(args[1]);
				if(ciblePlayer == null) {
					p.sendMessage("�4Le joueur ne fait pas partie de la faction.");
				}
				else if(!f.getChef().equals(p)) {
					p.sendMessage("�4Vous n'�tes pas le chef de votre faction, demandez lui.");
				}
				else {
					f.changerChef(ciblePlayer);
				}
				return true;
			}
			p.sendMessage("Vous n'êtes pas dans une faction");
		}
		return false;
	}

	@Override
	public List<String> getTabCompleter(Player p) {
		
		ArrayList<String> tab = new ArrayList<String>();
		
		if(Faction.estDansUneFaction(p)) {
			for(Player p1 : Faction.getPlayerFaction(p).getMembres()) {
				tab.add(p1.getName());
			}
			return tab;
		}
		return new ArrayList<String>();
	}


}
