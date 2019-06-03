package Factions;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class Faction {
	
	private static ArrayList<Faction> listeFactions = new ArrayList<Faction>();
	private static HashMap<Player, Faction> joueurFactions = new HashMap<Player, Faction>();

	private String nom;
	private Player chef;
	private ArrayList<Player> membres;
	
	public Faction(String nom, Player chef) {
		this.nom = nom;
		this.chef = chef;
		
		membres = new ArrayList<Player>();
		membres.add(chef);
		
		int i=0;
		while(i<listeFactions.size() && !listeFactions.get(i).getNom().equalsIgnoreCase(nom)) {
			i++;
		}
		if(i>=listeFactions.size() && !joueurFactions.containsKey(chef)) {
			listeFactions.add(this);
			joueurFactions.put(chef, this);
			chef.sendMessage("§aLa faction a été créé.");
		}
		else if(i<listeFactions.size()) {
			chef.sendMessage("§4Ce nom de faction existe déjà.");
		}
		else if(joueurFactions.containsKey(chef)) {
			chef.sendMessage("§4Vous faites déjà partie d'une faction.");
		}
	}
	
	public String getNom() {
		return nom;
	}
	
	public void addPlayer(Player p) {
		membres.add(p);
		joueurFactions.put(p, this);
	}
	public String toString() {
		return "=== Faction " + nom + " ===\n" +
				"Membres: " + membres.toString();
	}
	//--------------------------------Méthodes static
	
	public static HashMap<Player, Faction> getPlayerFaction() {
		return joueurFactions;
	}
	
	public static Faction getPlayerFaction(Player p) {
		return joueurFactions.get(p);
	}
	
	public static ArrayList<Faction> getListFaction() {
		return listeFactions;
	}
	
	/**
	 * Renvoie la faction qui a le nom nom
	 * @param nom nom de la faction
	 * @return retourne la faction si une faction avec le nom nom existe, null sinon
	 */
	public static Faction getFaction(String nom) {
		for(Faction faction : listeFactions) {
			if(nom.equalsIgnoreCase(faction.getNom())) {
				return faction;
			}
		}
		return null;
	}
	
	//--------------------------------------------------------------
	
	public boolean appartient(Player p) {
		return membres.contains(p);
	}
	public void broadcast(String message) {
		for(Player p : membres) {
			p.sendMessage(message);
		}
	}
}
