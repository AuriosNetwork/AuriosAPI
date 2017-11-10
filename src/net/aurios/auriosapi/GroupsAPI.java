package net.aurios.auriosapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class GroupsAPI {
	
	public HashMap<String, List<String>> permissions = new HashMap<String, List<String>>(); //Group name, list of permissions
	
	AuriosAPI core;
	public GroupsAPI(AuriosAPI core) {
		this.core = core;
	}
	
	public HashMap<String, List<String>> initializeGroupPermissions() {
		HashMap<String, List<String>> ip = new HashMap<String, List<String>>(); //Initialized permissions -> ip
		ResultSet rs = core.getMySQL().getResult("SELECT * FROM groupdata");
		try {
			while(rs.next()) {
				ip.put(rs.getString("Name"), new ArrayList<String>());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(String s : ip.keySet()) {
			List<String> gp = ip.get(s);//gp -> Group permissions
			String perms = (String) core.getMySQL().get("Permissions", "groupdata", "Name", s);
			if(perms != null && !perms.isEmpty()) {
				if(perms.contains(";")) {
					String[] sperms = perms.split(";"); //sperms -> split permissions
					for(int i = 0; i < sperms.length; i++) {
						gp.add(sperms[i]);
					}
				}else{
					gp.add(perms);
				}
			}
			ip.put(s, gp);
		}
		return ip;
	}
	
	public boolean groupExists(String group) {
		String gn = (String)core.getMySQL().get("Name", "groupdata", "Name", group);
		return gn != null && !gn.isEmpty();
	}
	
	private String getRawPlayerGroups(String uuid) {
		return (String) core.getMySQL().get("Groups", "playerData", "UUID", uuid);
	}
	
	public List<String> getPlayerGroups(String uuid) {
		List<String> groups = new ArrayList<String>();
		String pg = getRawPlayerGroups(uuid);
		if(pg != null && !pg.isEmpty()) {
			if(pg.contains(";")) {
				String[] g = getRawPlayerGroups(uuid).split(";");
				for(int i = 0; i < g.length; i++) {
					groups.add(g[i]);
				}
			}else{
				groups.add(pg);
			}
		}
		return groups;
	}
	
	private boolean groupContainsPermission(String group, String permission) {
		if(groupExists(group)) {
			group = group.substring(0, 1).toUpperCase() + group.substring(1).toLowerCase();
			List<String> prs = permissions.get(group);
			return prs.contains(permission);
		}
		return false;
	}
	
	public boolean hasPermission(CommandSender cs, String permission) {
		if(cs instanceof ConsoleCommandSender) return true;
		else if(cs instanceof Player) {
			int x = 0;
			Player pp = (Player) cs;
			String uuid = pp.getUniqueId().toString();
			List<String> groups = getPlayerGroups(uuid);
			for(String g : groups) {
				if(groupContainsPermission(g, permission)) x+=1;
			}
			return x>0;
		}
		return false;
	}
	
	public String getPrefix(String group) {
		return (String) core.getMySQL().get("Prefix", "groupdata", "Name", group);
	}
	
	public String getChatColor(String group) {
		return (String) core.getMySQL().get("ChatColor", "groupdata", "Name", group);
	}
	
	public String getTheMostValuablePlayerGroup(List<String> groups) {
		String toReturn = "";
		int value = 0;
		for(String g : groups) {
			if(groupExists(g)) {
				int gv = (int)core.getMySQL().get("Value", "groupdata", "Name", g); //Group value
				if(gv > value) {
					toReturn = g;
					value = gv;
				}
			}
		}
		return toReturn;
	}

}
