package net.aurios.auriosapi;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	AuriosAPI core;
	public FileManager(AuriosAPI core) {
		this.core = core;
	}
	
    public File createNewFile(String fileName, String path) {
        File filePath = new File(path);
        if(!filePath.exists()) filePath.mkdirs();
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("�c" + e.getMessage());
                e.printStackTrace();
            }
        }
        return file;
    }

    public File getFile(String fileName, String path) {
        File file = new File(path, fileName);
        return file;
    }

    public void deleteFile(String filename, String path) {
        File file = new File(path, filename);
        file.delete();
    }

    public FileConfiguration getConfiguration(String fileName, String path) {
        return YamlConfiguration.loadConfiguration(getFile(fileName, path));
    }


}
