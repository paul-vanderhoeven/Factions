package Factions.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FLeave implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			Faction f = Faction.getPlayerFaction(p);
			f.leave(p);
			return true;
		}
		return false;
	}

	@Override
	public List<String> getTabCompleter(Player p) {
		return new ArrayList<String>();
	}

}
