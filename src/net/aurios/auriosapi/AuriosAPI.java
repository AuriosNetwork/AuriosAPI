package net.aurios.auriosapi;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.aurios.auriosapi.listener.AuriosListeners;

public class AuriosAPI extends JavaPlugin {
	
	private AuriosAPI auriosapi;
	private FileManager fileManager;
	private MySQL mysql;
	private PlayerAccount playerAccount;
	private Packets packets;
	private Title title;
	private GroupsAPI groupsAPI;
	
	//Rank value -> Default rank value is 1. Higher ranks have higher values
	//The most valuable rank will always show up.
	
	@Override
	public void onEnable() {
		auriosapi = this;
		fileManager = new FileManager(auriosapi);
		mysql = new MySQL(auriosapi);
		playerAccount = new PlayerAccount(auriosapi);
		packets = new Packets();
		title = new Title(auriosapi);
		groupsAPI = new GroupsAPI(auriosapi);
		mysql.createMySQLFile("plugins//AuriosAPI//");
		mysql.connect();
		registerListeners();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void registerListeners() {
		PluginManager pm = auriosapi.getServer().getPluginManager();
		pm.registerEvents(new AuriosListeners(auriosapi), auriosapi);
	}
	
	public AuriosAPI getAuriosAPI() {
		return auriosapi;
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
	public MySQL getMySQL() {
		return mysql;
	}
	
	public PlayerAccount getPlayerAccount() {
		return playerAccount;
	}
	
	public Packets getPacketsHelper() {
		return packets;
	}
	
	public Title getTitleHelper() {
		return title;
	}
	
	public GroupsAPI getGroupsAPI() {
		return groupsAPI;
	}
	
	public String getPrefix() {
		return "§7[§a§lAUN§7] ";
	}
	

}
