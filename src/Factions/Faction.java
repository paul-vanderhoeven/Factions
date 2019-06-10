package Factions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

import org.bukkit.entity.Player;

public class Faction {
	
	private static ArrayList<Faction> listeFactions = new ArrayList<Faction>();
	private static HashMap<Player, Faction> joueurFactions = new HashMap<Player, Faction>();

	private String nom;
	private Player chef;
	private ArrayList<Player> membres;
	private FJoinDemand demande;
	
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
		StringJoiner joiner=new StringJoiner(",");
		for(Player p : membres) {
			joiner.add(p.getName());
		}
		return "=== Faction " + nom + " ===\n" +
				"Chef: " + chef.getName() +"\n" +
				"Membres: " + joiner.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faction other = (Faction) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	public Player getChef() {
		return chef;
	}

	public FJoinDemand getDemande() {
		return demande;
	}

	public void setDemande(FJoinDemand demande) {
		this.demande = demande;
	}
	
	public ArrayList<Player> getMembres() {
		return membres;
	}

	public boolean appartient(Player p) {
		return membres.contains(p);
	}
	public boolean appartient(String p) {
		return appartient(Main.getMain().getServer().getPlayer(p));
	}
	public Player getPlayer(String p) {
		if(appartient(p)) {
			for(Player membre : membres) {
				if(membre.getName().equals(p)) {
					return membre;
				}
			}
		}
		return null;
	}
	public void broadcastFaction(String message) {
		for(Player p : membres) {
			p.sendMessage(message);
		}
	}
	
	public void removePlayer(Player p) {
		joueurFactions.remove(p);
		membres.remove(p);

		
	}
	/**
	 * Retire le joueur de la faction, l'enlève de membres.
	 * Si le joueur est le chef => impossible, si il est le dernier joueur => supprime la faction
	 * @param p joueur qui supprime la faction
	 */
	public void leave(Player p) {
		if(membres.size()==1) {
			removePlayer(p);
			listeFactions.remove(this);
			p.sendMessage("§4Vous avez quitté la faction, elle a été supprimée.");
		}
		else if(getChef().equals(p)) {
			p.sendMessage("§4Impossible, vous êtes le chef de votre faction. Utilisez /f leader pour changez de chef.");
		}
		else {
			removePlayer(p);
			p.sendMessage("§4Vous avez quitté la faction.");
			broadcastFaction("§4Le joueur " + p.getName() + " a quitté la faction.");

		}
	}
	
	public void disband() {
		broadcastFaction("§4La faction a été supprimé.");
		for(Player p : new ArrayList<Player>(membres)) {
			removePlayer(p);
		}
		listeFactions.remove(this);
	}
	
	/**
	 * Change le chef de la faction
	 * @param p joueur qui remplace le chef
	 */
	public void changerChef(Player p) {
		if(this.appartient(p)) {
			if(getChef().equals(p)) {
				p.sendMessage("§4Vous êtes déjà le chef.");
			}
			else {
				chef=p;
				p.sendMessage("§aVous êtes maintenant le chef de cette faction.");
				broadcastFaction(p.getName() + " est maintenant le chef de la faction.");
			}
		}

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
	public static boolean estDansMemeFaction(Player p1, Player p2) {
		if(joueurFactions.containsKey(p1) && joueurFactions.containsKey(p2)) {
			return getPlayerFaction(p1).equals(getPlayerFaction(p2));
		}
		return false;
	}
	
	public static boolean estDansFaction(Player p) {
		return joueurFactions.containsKey(p);
	}
	
	//--------------------------------------------------------------
	
}
