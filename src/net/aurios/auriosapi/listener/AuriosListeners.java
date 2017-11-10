package net.aurios.auriosapi.listener;

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
		p.sendMessage(group + "!");
		String prefix = ChatColor.translateAlternateColorCodes('&', core.getGroupsAPI().getPrefix(group));
		p.setDisplayName(prefix + " §7§o" + p.getName());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setFormat(p.getDisplayName() + "§f§o: " + e.getMessage());
	}

}
