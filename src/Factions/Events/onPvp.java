package Factions.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import Factions.Faction;

public class onPvp implements Listener {

	@EventHandler
	public void onPvpFaction(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if(Faction.estDansMemeFaction( (Player) e.getDamager(), (Player) e.getEntity())) {
				e.getDamager().sendMessage("§4Vous ne pouvez pas taper un membre de votre faction");
				e.setCancelled(true);
			}
		}

	}
}
