package net.aurios.auriosapi.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.aurios.auriosapi.AuriosAPI;

public class AuriosListeners implements Listener {
	
	AuriosAPI core;
	public AuriosListeners(AuriosAPI core) {
		this.core = core;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String group = core.getGroupsAPI().getTheMostValuablePlayerGroup(core.getGroupsAPI().getPlayerGroups(p.getUniqueId().toString()));
		String prefix = ChatColor.translateAlternateColorCodes('&', core.getGroupsAPI().getPrefix(group));
		p.setDisplayName(prefix + " §7§o" + p.getName());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		int lastPunishment = (int) core.getMySQL().get("LastPunishment", "playerdata", "Username", p.getName());
		if(lastPunishment != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sd = (String) core.getMySQL().get("ExpirationDate", "punishmentdata", "ID", lastPunishment);
			Date d = null;
			try {
				d = sdf.parse(sd);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if(d != null) {
				Date now = new Date();
				if(now.before(d)) {
					e.setCancelled(true);
				}else e.setCancelled(false);
			}else e.setCancelled(false);
		}else e.setCancelled(false);
		
		if(!e.isCancelled()) e.setFormat(p.getDisplayName() + "§f§o: " + e.getMessage());
		else p.sendMessage(core.getPrefix() + "§cYou can not talk right now, because you are muted.");
	}

}
