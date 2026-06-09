package ru.Natro.worldmanager;

import java.io.File;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.commands.AliasManager;
import ru.Natro.worldmanager.commands.CommandMapping;
import ru.Natro.worldmanager.listener.Addons;
import ru.Natro.worldmanager.listener.CropProtectionListener;
import ru.Natro.worldmanager.listener.Events;
import ru.Natro.worldmanager.listener.GameplayFeatureListener;
import ru.Natro.worldmanager.listener.GameplaySettingsListener;
import ru.Natro.worldmanager.listener.WorldManagerUI;
import ru.Natro.worldmanager.utils.Cache;
import ru.Natro.worldmanager.utils.CustomMetricsManager;
import ru.Natro.worldmanager.utils.LoadWorlds;
import ru.Natro.worldmanager.utils.Updater;


public class WorldManager extends PluginBase {

	protected static Plugin plugin;
	
	public static final String prefix = "§3WorldManager §8» §7";
	
	public void onEnable() {
		
		plugin = this;
		
		this.saveDefaultConfig();
		
		registerCommands();
		
		getServer().getPluginManager().registerEvents(new Events(), plugin);
		getServer().getPluginManager().registerEvents(new WorldManagerUI(), plugin);
		getServer().getPluginManager().registerEvents(new Addons(), plugin);
		getServer().getPluginManager().registerEvents(new Cache(), plugin);
		getServer().getPluginManager().registerEvents(new CropProtectionListener(), plugin);
		getServer().getPluginManager().registerEvents(new GameplaySettingsListener(), plugin);
		getServer().getPluginManager().registerEvents(new GameplayFeatureListener(), plugin);
		
		LoadWorlds.loadWorlds();
		AliasManager.registerAliases();
		Addons.initJson();
		
		CustomMetricsManager.loadMetrics();
		
		get().getLogger().info("§bWorldManager v" + plugin.getDescription().getVersion() + " loaded successfully.");
		
	}
	
	private void registerCommands() {
		
		CommandMapping command = new CommandMapping();
		command.register();
		
	}
	
	public static boolean isProtectedWorld(String worldName) {
		boolean listProtected = get().getConfig().getStringList("protected-worlds").contains(worldName);
		if (listProtected) return true;
		try {
			Level level = Server.getInstance().getLevelByName(worldName);
			if (level != null) {
				World world = Cache.getWorld(level);
				return world != null && world.isProtected();
			}
		} catch (Exception e) {
			if (get().getConfig().getBoolean("debug", false)) {
				get().getLogger().warning("isProtectedWorld check failed for " + worldName + ": " + e.getMessage());
			}
		}
		return false;
	}
	
	public static Plugin get() {
		return plugin;
	}
	
}
