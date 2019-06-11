package Factions.Commands;

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
			
			if(!Faction.estDansFaction(p)) {
				p.sendMessage("§4Vous n'êtes pas dans une faction.");
			}
			else if(!Faction.getPlayerFaction(p).getChef().equals(p)) {
				p.sendMessage("§4Vous n'êtes pas le chef de votre faction.");
			}
			else if(args.length==1) {
				p.sendMessage("§4Qui ?");
			}
			else if(Main.getMain().getServer().getPlayer(args[1]) == null) {
				p.sendMessage("§4Le joueur n'est pas connecté.");
			}
			else {
				Faction f = Faction.getPlayerFaction(p);
				p.sendMessage("§avous avez envoyé une invitation à " + Main.getMain().getServer().getPlayer(args[1]).getName());
				
				Main.getMain().getServer().getPlayer(args[1]).sendMessage("§aVous avez reçu une invitation à rejoindre la faction " + Faction.getPlayerFaction(p).getNom() + ". Vous avez 60s pour accepter => /f accept.");
				f.setDemande(new Invitation(f, Main.getMain().getServer().getPlayer(args[1])));
			}

		}
		return true;
	}

}
