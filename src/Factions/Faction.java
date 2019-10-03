package Factions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringJoiner;

import org.bukkit.entity.Player;

public class Faction {

	private String nom;
	private Player chef;
	private ArrayList<Player> membres;
	private Connection connection;
	
	public Faction(String nom, Player chef) {
		this.nom = nom;
		this.chef = chef;
		connection = Main.getConnection();
		
		membres = new ArrayList<Player>();
		membres.add(chef);
	}
	
	public void save(Player p) {
		if(estDansUneFaction(p)) {
			p.sendMessage("Vous êtes déjà dans une faction");
		}else if(existe(nom)) {
			p.sendMessage("Ce nom de faction existe déjà");
		} else {
			saveAux(p);
		}
	}
	
	private void saveAux(Player p) {
		
		try {
			String sql = "INSERT INTO Factions(nom, uuidChef) VALUES (?, ?)";
			PreparedStatement prep = connection.prepareStatement(sql);
			
			prep.setString(1, nom);
			prep.setString(2, chef.getUniqueId().toString());
				
			prep.execute();
				
			addPlayer(chef);
			p.sendMessage("La faction à bien été crée");
		} catch (SQLException e) {
			p.sendMessage("Erreur lors de la sauvegarde de la faction dans la base de donnée");
			e.printStackTrace();
		}
	}
	
	public String getNom() {
		return nom;
	}

	public void addPlayer(Player p) {
		if(estMembre(p)) {
			p.sendMessage("Vous faites déjà partie de cette faction");
		} else if (estDansUneFaction(p)) {
			p.sendMessage("Vous faites partie d'une autre faction");
		}
		else {
			addPlayerAux(p);
		}
	}
	
	private void addPlayerAux(Player p) {
		try {
			String sql = "INSERT into Appartenir(nom, uuid) VALUES (?, ?)";
			
			PreparedStatement prep = connection.prepareStatement(sql);
			
			prep.setString(1, nom);
			prep.setString(2, p.getUniqueId().toString());
			
			prep.execute();
		} catch (SQLException e) {
			p.sendMessage("Erreur lors de l'ajout dans la base de donnée du joueur" + p.getName());
		}
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
		} else if (!nom.equals(other.nom)) {
			return false;

		}
		return true;
	}
	public Player getChef() {
		return chef;
	}
	public boolean estChef(Player p) {
		return getChef().equals(p);
	}
	
	public ArrayList<Player> getMembres() {
		return membres;
	}

	/**
	 * Retourne vrai si le joueur p fait partie de la faction
	 * @param p joueur
	 * @return true si le joueur p fait partie de membres
	 */
	public boolean estMembre(Player p) {
		return membres.contains(p);
	}
	
	/**
	 * Retourne vrai si le nom de joueur p fait partie de la faction
	 * @param p nom du joueur
	 * @return true si p fait partie de membres
	 */
	public boolean estMembre(String p) {
		return estMembre(Main.getMain().getServer().getPlayer(p));
	}

	/**
	 * @param p nom du joueur
	 * @return le joueur qui a le nom p dans la faction, sinon null
	 */
	public Player getPlayer(String p) {
		if(estMembre(p)) {
			for(Player membre : membres) {
				if(membre.getName().equals(p)) {
					return membre;
				}
			}
		}
		return null;
	}
	
	/**
	 * Envoie un message à tout les membres de la faction
	 * @param message
	 */
	public void broadcastFaction(String message) {
		for(Player p : membres) {
			p.sendMessage(message);
		}
	}
	
	
	/**
	 * Pré-requis: on suppose que la joueur p fait partie de la faction
	 * Supprime un joueur de la faction sans controler si le joueur est chef ou ne fait pas partie de la faction
	 * @param p joueur à supprimer de la faction
	 * 
	 */
	private void removePlayer(Player p) {
		try {
			String sql = "DELETE FROM Appartenir WHERE nom='" + p.getName() + "'";
			Statement sta = connection.createStatement();
			sta.execute(sql);
			membres.remove(p);
		} catch (SQLException e) {
			p.sendMessage("Erreur lors de la suppression du joueur " + p.getName());
			e.printStackTrace();
		}
	}
	
	public void kick(Player p, Player sender) {
		if(!estMembre(p)) {
			sender.sendMessage("Ce joueur n'est pas membre de la faction");
		} else if(estChef(p)) {
			sender.sendMessage("Ce joueur est chef, il ne peut pas être kick de la faction");
		} else {
			removePlayer(p);
			broadcastFaction("Le joueur " + p.getName() + " a été kick de la faction");
			p.sendMessage("Vous avez été kick de la faction");
		}
	}

	/**
	 * Retire le joueur de la faction, l'enl�ve de membres.
	 * Si le joueur est le chef => impossible, si il est le dernier joueur => supprime la faction
	 * @param p joueur qui supprime la faction
	 */
	public void leave(Player p) {
		if(membres.size()==1) {
			p.sendMessage("Vous êtes le dernier membre de la faction, si vous la supprimez utilisez: /f disband");
		}
		else if(estChef(p)) {
			p.sendMessage("�4Impossible, vous �tes le chef de votre faction. Utilisez /f leader pour changez de chef.");
		}
		else {
			removePlayer(p);
			p.sendMessage("�4Vous avez quitt� la faction.");
			broadcastFaction("�4Le joueur " + p.getName() + " a quitt� la faction.");

		}
	}
	
	/**
	 * Supprime la faction
	 */
	private void delete(Player p) {
		try {
			String sql = "DELETE FROM Factions WHERE nom='" + nom + "'";
			Statement sta = connection.createStatement();
			sta.execute(sql);
		} catch (SQLException e) {
			p.sendMessage("Erreur lors de la suppression de la faction");
			e.printStackTrace();
		}
	}
	
	public void disband(Player p) {
		broadcastFaction("La faction à été supprimée");
		membres.clear();
		delete(p);
	}
	/**
	 * Change le chef de la faction
	 * @param p joueur qui remplace le chef
	 */
	public void changerChef(Player p) {
		//TO DO
		if(estMembre(p)) {
			if(getChef().equals(p)) {
				p.sendMessage("�4Vous �tes d�j� le chef.");
			}
			else {
				chef=p;
				p.sendMessage("�aVous �tes maintenant le chef de cette faction.");
				broadcastFaction(p.getName() + " est maintenant le chef de la faction.");
			}
		}

	}
	
	/**
	 * Renvoie la faction qui a le nom nom
	 * @param nom, nom de la faction
	 * @return retourne la faction si une faction avec le nom nom existe, null sinon
	 */
	public static Faction getFaction(String nom) {
		//TO DO
		return null;
	}
	public static boolean existe(String nom) {
		
		return getFaction(nom)!=null;
	}
	
	public static boolean estDansMemeFaction(Player p1, Player p2) {
		//TO DO
		return false;
	}

	public static ArrayList<Faction> getListFaction() {
		//TO DO
		return null;
	}

	public static Faction getPlayerFaction(Player p) {
		// TODO
		return null;
	}

	public static boolean estDansUneFaction(Player p) {
		// TODO 
		return false;
	}	
}
