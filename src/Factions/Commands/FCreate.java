package Factions.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FCreate implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player && args.length>1) {
			Player p = (Player) sender;
			
			Faction f = new Faction(args[1], p);
			f.save(p);

		}
		else {
			return false;
		}
		return true;
	}

	@Override
	public List<String> getTabCompleter(Player p) {
		return new ArrayList<String>();
	}

	@Override
	public boolean isCommandAdmin() {
		return false;
	}


}
