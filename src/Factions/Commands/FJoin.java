package Factions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FJoin implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {

			Player p = (Player) sender;
			
			if(args.length==1) {
				return false;
			}
			else {
				Faction f = Faction.getFaction(args[1]);
				if(f != null && f.getDemande() != null && f.getDemande().getPlayer().equals(p)) {
					f.getDemande().accept();
					return true;
				}
				else {
					return false;
				}

			}
			
		}
		return false;
	}

}
