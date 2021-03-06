package Factions.Commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;
import Factions.Invitation;
import Factions.Main;

public class FInvite implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(!Faction.estDansUneFaction(p)) {
				p.sendMessage("�4Vous n'�tes pas dans une faction.");
			}
			else if(!Faction.getPlayerFaction(p).getChef().equals(p)) {
				p.sendMessage("�4Vous n'�tes pas le chef de votre faction.");
			}
			else if(args.length==1) {
				p.sendMessage("�4Qui ?");
			}
			else if(Main.getMain().getServer().getPlayer(args[1]) == null) {
				p.sendMessage("�4Le joueur n'est pas connect�.");
			}
			else {
				Faction f = Faction.getPlayerFaction(p);
				
				if(f.estMembre(args[1])) {
					p.sendMessage("Le joueur est déjà membre de la faction");
				} else {
					p.sendMessage("�avous avez envoy� une invitation � " + Main.getMain().getServer().getPlayer(args[1]).getName());
					
					Main.getMain().getServer().getPlayer(args[1]).sendMessage("�aVous avez re�u une invitation � rejoindre la faction " + f.getNom() + ". Vous avez 60s pour accepter => /f join " + f.getNom());
					f.invite(Main.getMain().getServer().getPlayer(args[1]));
				}

			}

		}
		return true;
	}
	
	public List<String> getTabCompleter(Player p1) {
		
		List<String> joueur = new LinkedList<>();
		
		for(Player p : p1.getServer().getOnlinePlayers()) {
			joueur.add(p.getName());
		}
		return joueur;
		
	}

	@Override
	public boolean isCommandAdmin() {
		return false;
	}

}
