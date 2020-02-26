package Factions.Events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Factions.SqlConnection;

public class onPlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		addPlayer(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName());
	}
	
	private void addPlayer(String uuid, String name) {
		Connection connection = SqlConnection.getConnection();
		try {
			String sql = "INSERT INTO Joueurs(uuid, nom) VALUES (?, ?)";
			PreparedStatement prep = connection.prepareStatement(sql);
		
			prep.setString(1, uuid);
			prep.setString(2, name);
				
			prep.execute();
				
		} catch (SQLException e) {
			System.out.println("Le joueur est déjà dans la base de donnée.");
		}
	}
}
