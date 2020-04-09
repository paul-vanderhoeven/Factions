package Factions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Faction {

	private String nom;
	private Player chef;
	private ArrayList<Player> membres;
	private static ArrayList<Invitation> invitations = new ArrayList<>();
	private static Connection connection = SqlConnection.getConnection();
	
	public Faction(String nom, Player chef) {
		this.nom = nom;
		this.chef = chef;
		
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
				
			addPlayerAux(chef);
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
			
			System.out.println(sql);
			PreparedStatement prep = connection.prepareStatement(sql);
			
			prep.setString(1, nom);
			prep.setString(2, p.getUniqueId().toString());
			
			prep.execute();
		} catch (SQLException e) {
			p.sendMessage("Erreur lors de l'ajout dans la base de donnée du joueur " + p.getName());
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
	private void setMembres(ArrayList<Player> membres) {
		this.membres = membres;
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
			String sql = "DELETE FROM Appartenir WHERE uuid='" + p.getUniqueId() + "'";
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
			delete(p);
			p.sendMessage("La faction a été supprimé");
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
			removePlayer(p);
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
	
	public void invite(Player player) {
		invitations.add(new Invitation(nom, player));
	}
	
	public void join(Player player) {
		Invitation inv = null;
		System.out.println(invitations.size());
		
		for(Invitation i : invitations) {
			if(i.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				inv = i;
				break;
			}
		}
		
		if(inv == null) {
			player.sendMessage("Vous n'avez pas été invité dans cette faction");
		} else {
			inv.accept();
			invitations.remove(inv);
		}
	}
	
	/**
	 * Renvoie la faction qui a le nom nom
	 * @param nom, nom de la faction
	 * @return retourne la faction si une faction avec le nom nom existe, null sinon
	 */
	public static Faction getFaction(String nom) {
		String sql = "SELECT * FROM Factions WHERE nom=?";
		Faction f = null;
		try {
			PreparedStatement requete = connection.prepareStatement(sql);
			requete.setString(1, nom);
			ResultSet rs = requete.executeQuery();
			
			if(rs.isClosed()) {
				return null;
			}
			
			UUID uuid = UUID.fromString(rs.getString("uuidChef"));
			
			f = new Faction(nom, Main.getMain().getServer().getPlayer(uuid));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		f.setMembres(Faction.getMembres(nom));
		return f;
	}
	
	private static ArrayList<Player> getMembres(String nom) {
		ArrayList<Player> membres = new ArrayList<>();
		String sql = "SELECT uuid FROM Appartenir WHERE nom=?";
		try {
			PreparedStatement requete = connection.prepareStatement(sql);
			requete.setString(1, nom);
			ResultSet rs = requete.executeQuery();
			
			while(rs.next()) {
				Player p = Main.getMain().getServer().getPlayer(UUID.fromString(rs.getString("uuid")));
				membres.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return membres;
	}
	public static boolean existe(String nom) {
		
		return getFaction(nom)!=null;
	}
	
	public static boolean estDansMemeFaction(Player p1, Player p2) {
		
		if(Faction.estDansUneFaction(p1) && Faction.estDansUneFaction(p2) && Faction.getPlayerFaction(p1).equals(Faction.getPlayerFaction(p2))) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<Faction> getListFaction() {
		String sql = "SELECT * FROM Factions;";
		ArrayList<Faction> list = new ArrayList<>();
		
		try {
			PreparedStatement requete = connection.prepareStatement(sql);
			ResultSet rs = requete.executeQuery();
			
			while(rs.next()) {
				Faction f = new Faction(rs.getString("nom"), Main.getMain().getServer().getPlayer(UUID.fromString(rs.getString("uuidChef"))));
				f.setMembres(Faction.getMembres(rs.getString("nom")));
				list.add(f);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Faction getPlayerFaction(Player p) {
		String sql = "SELECT nom FROM Appartenir WHERE uuid=?";
		String fnom = null;
		
		try {
			PreparedStatement requete = connection.prepareStatement(sql);
			requete.setString(1, p.getUniqueId().toString());
			ResultSet rs = requete.executeQuery();
			
			fnom = rs.getString("nom");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Faction.getFaction(fnom);
	}

	public static boolean estDansUneFaction(Player p) {
		
		String sql = "SELECT count(nom) as nb FROM Appartenir WHERE uuid=?";
		int nb = 0;
		
		try {
			PreparedStatement requete = connection.prepareStatement(sql);
			requete.setString(1, p.getUniqueId().toString());
			ResultSet rs = requete.executeQuery();
			
			nb = rs.getInt("nb");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(nb == 1) {
			return true;
		} else {
			return false;
		}
	}


}
