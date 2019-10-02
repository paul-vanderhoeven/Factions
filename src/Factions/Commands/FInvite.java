package Factions.Commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Main;

public class FInvite implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		/*if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(!Faction.estDansFaction(p)) {
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
				p.sendMessage("�avous avez envoy� une invitation � " + Main.getMain().getServer().getPlayer(args[1]).getName());
				
				Main.getMain().getServer().getPlayer(args[1]).sendMessage("�aVous avez re�u une invitation � rejoindre la faction " + Faction.getPlayerFaction(p).getNom() + ". Vous avez 60s pour accepter => /f accept.");
				f.setDemande(new Invitation(f, Main.getMain().getServer().getPlayer(args[1])));
			}

		}*/
		return true;
	}
	
	public List<String> getTabCompleter(Player p1) {
		
		List<String> joueur = new LinkedList<>();
		
		for(Player p : Main.getMain().getServer().getOnlinePlayers()) {
			joueur.add(p.getName());
		}
		return joueur;
		
	}

}
