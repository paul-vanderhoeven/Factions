package Factions.Commands;

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.FJoinDemand;
import Factions.Faction;
import net.minecraft.server.v1_14_R1.CustomFunction.d;

public class FJoin implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length==1) {
				p.sendMessage("§4Quelle faction ?");
				return false;
			}
			if(Faction.getPlayerFaction().containsKey(p)) {
				p.sendMessage("§4Vous êtes déjà dans une faction.");
				return false;
			}
			Faction f = Faction.getFaction(args[1]);
			if(f==null) {
				p.sendMessage("§4La faction n'existe pas.");
				return false;
			}
			
			
			else{
				p.sendMessage("Une demande a été envoyé au chef de la faction");
				f.getChef().sendMessage("Le joueur " + p.getName() + " demande à rejoindre la faction, vous avez 60s pour accepter => /f accept");
				Date date = new Date();
				f.setDemande(new FJoinDemand(p, f, date.getTime()+60000));	//60s pour accepter la demande
			}
			
		}
		return false;
	}

}
