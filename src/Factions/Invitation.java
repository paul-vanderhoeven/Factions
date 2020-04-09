package Factions;

import java.util.Date;

import org.bukkit.entity.Player;

public class Invitation {

	private String faction;
	private Player player;
	private long time;
	
	
	public Invitation(String faction, Player player) {
		this.faction = faction;
		this.player = player;
		Date date = new Date();
		this.time = date.getTime();
	}
	
	public void accept() {
		Date date = new Date();
		if(time+60000 >= date.getTime()) {
			
			Faction f = Faction.getFaction(faction);
			f.addPlayer(player);
			
			f.broadcastFaction("�aLe joueur " + player.getName() + " a rejoint la faction.");
			player.sendMessage("�aVous avez rejoint la faction.");
		}
		else {
			player.sendMessage("�4Trop tard!");
		}
	}
	
	
	public Player getPlayer() {
		return player;
	}
}
