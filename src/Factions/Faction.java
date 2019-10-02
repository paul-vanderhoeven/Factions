package Factions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		if(!Faction.estDansFaction(p)) {
			try {
				String sql = "INSERT INTO Factions(nom, uuidChef) VALUES (?, ?)";
				PreparedStatement prep = connection.prepareStatement(sql);
				
				prep.setString(1, nom);
				prep.setString(2, chef.getUniqueId().toString());
				
				prep.execute();
				
				addPlayer(chef);
				p.sendMessage("La faction à bien été crée");
			} catch (SQLException e) {
				p.sendMessage("Ce nom de faction existe déjà ! Veuillez changez");
			}
		}
		else {
			p.sendMessage("Vous êtes déjà dans une faction");
		}

	}
	
	public String getNom() {
		return nom;
	}

	/**
	 * Ajout du joueurs p dans la faction
	 * @param p
	 */
	public void addPlayer(Player p) {
		try {
			String sql = "INSERT into Appartenir(nom, uuid) VALUES (?, ?)";
			
			PreparedStatement prep = connection.prepareStatement(sql);
			
			prep.setString(1, nom);
			prep.setString(2, p.getUniqueId().toString());
			
			prep.execute();
		} catch (SQLException e) {
			p.sendMessage("Erreur d'ajout dans la base de donnée du joueur" + p.getName());
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
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	public Player getChef() {
		return chef;
	}
	
	public ArrayList<Player> getMembres() {
		return membres;
	}

	/**
	 * Retourne vrai si le joueur p fait partie de la faction
	 * @param p joueur
	 * @return true si le joueur p fait partie de membres
	 */
	public boolean appartient(Player p) {
		return membres.contains(p);
	}
	
	/**
	 * Retourne vrai si le nom de joueur p fait partie de la faction
	 * @param p nom du joueur
	 * @return true si p fait partie de membres
	 */
	public boolean appartient(String p) {
		return appartient(Main.getMain().getServer().getPlayer(p));
	}

	/**
	 * @param p nom du joueur
	 * @return le joueur qui a le nom p dans la faction, sinon null
	 */
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
	 * Supprime un joueur de la faction
	 * @param p
	 */
	public void removePlayer(Player p) {
		//TO DO
		membres.remove(p);
	}
	/**
	 * Retire le joueur de la faction, l'enl�ve de membres.
	 * Si le joueur est le chef => impossible, si il est le dernier joueur => supprime la faction
	 * @param p joueur qui supprime la faction
	 */
	public void leave(Player p) {
		//TO DO
		if(membres.size()==1) {
			removePlayer(p);
			p.sendMessage("�4Vous avez quitt� la faction, elle a �t� supprim�e.");
		}
		else if(getChef().equals(p)) {
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
	public void disband() {
		//TO DO
		broadcastFaction("�4La faction a �t� supprim�.");
		for(Player p : new ArrayList<Player>(membres)) {
			removePlayer(p);
		}
	}
	
	/**
	 * Change le chef de la faction
	 * @param p joueur qui remplace le chef
	 */
	public void changerChef(Player p) {
		//TO DO
		if(this.appartient(p)) {
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
	
	public static boolean estDansMemeFaction(Player p1, Player p2) {
		//TO DO
		return false;
	}
	
	public static boolean estDansFaction(Player p) {
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

	public static Object estDansUneFaction(Player p) {
		// TODO 
		return null;
	}	
}
