package Factions.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		e.getPlayer().sendMessage("§aBienvenue sur le serveur !");
	}
}
