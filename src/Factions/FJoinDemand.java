package Factions;

import java.util.Date;

import org.bukkit.entity.Player;

public class FJoinDemand {
	
	private Player p;
	private Faction f;
	
	private long dateButoire;

	public FJoinDemand(Player p, Faction f, long dateButoire) {
		this.p = p;
		this.f = f;
		this.dateButoire = dateButoire;
		f.setDemande(this);
	}
	
	public void accept() {
		
		Date date = new Date();

		if(Faction.estDansFaction(p)) {
			p.sendMessage("§4Vous êtes dans une faction.");
		}
		else if(date.getTime()>dateButoire) {
			f.getChef().sendMessage("§4Trop tard.");
		}
		else {
			f.addPlayer(p);
			p.sendMessage("§aVous avez rejoint la faction");
			f.broadcastFaction("§aLe joueur " + p.getName() + "§a a rejoint la faction");
		}
	}
	
	public void deny() {

	}
}