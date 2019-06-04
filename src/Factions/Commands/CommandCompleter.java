package Factions.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		ArrayList<String> tab = new ArrayList<>();
		
		tab.add("create");
		tab.add("status");
		tab.add("list");
		tab.add("join");
		tab.add("accept");
		return tab;
	}

}
