package Factions.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Factions.Faction;

public class FList implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		sender.sendMessage("=== Liste des factions ===");
		StringJoiner joiner = new StringJoiner("\n");
		for(Faction faction : Faction.getListFaction()) {
			joiner.add(faction.getNom());
		}
		sender.sendMessage(joiner.toString());
		sender.sendMessage("==========================");
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
