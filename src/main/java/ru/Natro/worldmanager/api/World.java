package ru.Natro.worldmanager.api;

import java.io.File;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;

public class World {
	private String level;
	private Config config;
	private boolean loadonstart;
	private int gamemode;
	private String respawnworld;
	private String thumbnail;
	private boolean fly;
	private boolean protect;
	private String note;
	private int timelock;
	private String weather;
	private boolean animals;
	private boolean monsters;
	private boolean pvp;
	private boolean instantRespawn;
	private boolean keepInventory;
	private boolean keepExperience;
	private boolean fallDamage;
	private boolean fireDamage;
	private boolean lavaDamage;
	private boolean drowning;
	private boolean starvation;
	private boolean explosionDamage;
	private boolean explosionBlockDamage;
	private boolean fireSpread;
	private boolean mobGriefing;
	private boolean hunger;
	private boolean naturalRegen;
	private int playerLimit;
	private int difficulty;

	public World(Level level) {
		this.level = level.getName();
		refreshData();
	}

	public Level getAsLevel() {
		return Server.getInstance().getLevelByName(this.level);
	}

	public Config getWorldConfiguration() {
		return this.config;
	}

	public Config getConfig() {
		return this.config;
	}

	public boolean doesLoadOnStart() {
		return this.loadonstart;
	}

	public int getOwnGamemode() {
		return this.gamemode;
	}

	public String getRespawnWorld() {
		return this.respawnworld;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public boolean isFlyAllowed() {
		return this.fly;
	}

	public boolean isProtected() {
		return this.protect;
	}

	public String getNote() {
		return this.note;
	}

	public void setLoadOnStart(boolean loadOnStart) {
		this.loadonstart = loadOnStart;
		this.config.set("LoadOnStart", loadOnStart);
		this.config.save();
	}

	public void enableLoadOnStart() {
		this.loadonstart = true;
		this.config.set("LoadOnStart", true);
		this.config.save();
	}

	public void disableLoadOnStart() {
		this.loadonstart = false;
		this.config.set("LoadOnStart", false);
		this.config.save();
	}

	public void enableOwnGamemode() {
		setGamemode(4);
	}

	public boolean isUsingOwnGamemode() {
		return (this.gamemode == 4);
	}

	public void setGamemode(int gamemode) {
		if(gamemode < 0 || gamemode > 4) throw new IndexOutOfBoundsException("Unknown Gamemode");
		this.gamemode = gamemode;
		this.config.set("Gamemode", gamemode);
		this.config.save();
	}

	public void setFly(boolean fly) {
		this.fly = fly;
		this.config.set("fly", fly);
		this.config.save();
	}

	public void enableFly() {
		this.fly = true;
		this.config.set("fly", true);
		this.config.save();
	}

	public void disableFly() {
		this.fly = false;
		this.config.set("fly", false);
		this.config.save();
	}

	public void setProtected(boolean protect) {
		this.protect = protect;
		this.config.set("protected", protect);
		this.config.save();
	}

	public void enableProtection() {
		this.protect = true;
		this.config.set("protected", true);
		this.config.save();
	}

	public void disableProtection() {
		this.protect = false;
		this.config.set("protected", false);
		this.config.save();
	}

	public void setRespawnWorld(String respawnworld) {
		this.respawnworld = respawnworld;
		this.config.set("respawnworld", respawnworld);
		this.config.save();
	}

	public void setRespawnWorld(Level respawnworld) {
		this.respawnworld = respawnworld.getName();
		this.config.set("respawnworld", respawnworld.getName());
		this.config.save();
	}

	public void setRespawnWorld(World respawnworld) {
		this.respawnworld = respawnworld.getAsLevel().getName();
		this.config.set("respawnworld", respawnworld.getAsLevel().getName());
		this.config.save();
	}

	public void setThumbnail(String thumbnail) {
		this.respawnworld = thumbnail;
		this.config.set("thumbnail", thumbnail);
		this.config.save();
	}

	public void setPathThumbnail(String path) {
		setThumbnail("path::" + path);
	}

	public void setUrlThumbnail(String url) {
		setThumbnail("url::" + url);
	}

	public void setNote(String note) {
		this.note = note;
		this.config.set("note", note);
		this.config.save();
	}

	public int getTimeLock() {
		return this.timelock;
	}

	public String getWeatherLock() {
		return this.weather;
	}

	public boolean isAnimalSpawnEnabled() {
		return this.animals;
	}

	public boolean isMonsterSpawnEnabled() {
		return this.monsters;
	}

	public void setTimeLock(int timelock) {
		this.timelock = timelock;
		this.config.set("timelock", timelock);
		this.config.save();
	}

	public void setWeatherLock(String weather) {
		this.weather = weather;
		this.config.set("weather", weather);
		this.config.save();
	}

	public void setAnimalSpawn(boolean animals) {
		this.animals = animals;
		this.config.set("animals", animals);
		this.config.save();
	}

	public void setMonsterSpawn(boolean monsters) {
		this.monsters = monsters;
		this.config.set("monsters", monsters);
		this.config.save();
	}

	public boolean isPvpEnabled() {
		return this.pvp;
	}

	public boolean isInstantRespawn() {
		return this.instantRespawn;
	}

	public boolean isKeepInventory() {
		return this.keepInventory;
	}

	public boolean isKeepExperience() {
		return this.keepExperience;
	}

	public boolean hasFallDamage() {
		return this.fallDamage;
	}

	public boolean hasFireDamage() {
		return this.fireDamage;
	}

	public boolean hasLavaDamage() {
		return this.lavaDamage;
	}

	public boolean hasDrowning() {
		return this.drowning;
	}

	public boolean hasStarvation() {
		return this.starvation;
	}

	public boolean hasExplosionDamage() {
		return this.explosionDamage;
	}

	public boolean hasExplosionBlockDamage() {
		return this.explosionBlockDamage;
	}

	public boolean hasFireSpread() {
		return this.fireSpread;
	}

	public boolean hasMobGriefing() {
		return this.mobGriefing;
	}

	public boolean hasHunger() {
		return this.hunger;
	}

	public boolean hasNaturalRegen() {
		return this.naturalRegen;
	}

	public int getPlayerLimit() {
		return this.playerLimit;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setPvp(boolean pvp) {
		this.pvp = pvp;
		this.config.set("pvp", pvp);
		this.config.save();
	}

	public void setInstantRespawn(boolean instantRespawn) {
		this.instantRespawn = instantRespawn;
		this.config.set("instant-respawn", instantRespawn);
		this.config.save();
	}

	public void setKeepInventory(boolean keepInventory) {
		this.keepInventory = keepInventory;
		this.config.set("keep-inventory", keepInventory);
		this.config.save();
	}

	public void setKeepExperience(boolean keepExperience) {
		this.keepExperience = keepExperience;
		this.config.set("keep-experience", keepExperience);
		this.config.save();
	}

	public void setFallDamage(boolean fallDamage) {
		this.fallDamage = fallDamage;
		this.config.set("fall-damage", fallDamage);
		this.config.save();
	}

	public void setFireDamage(boolean fireDamage) {
		this.fireDamage = fireDamage;
		this.config.set("fire-damage", fireDamage);
		this.config.save();
	}

	public void setLavaDamage(boolean lavaDamage) {
		this.lavaDamage = lavaDamage;
		this.config.set("lava-damage", lavaDamage);
		this.config.save();
	}

	public void setDrowning(boolean drowning) {
		this.drowning = drowning;
		this.config.set("drowning", drowning);
		this.config.save();
	}

	public void setStarvation(boolean starvation) {
		this.starvation = starvation;
		this.config.set("starvation", starvation);
		this.config.save();
	}

	public void setExplosionDamage(boolean explosionDamage) {
		this.explosionDamage = explosionDamage;
		this.config.set("explosion-damage", explosionDamage);
		this.config.save();
	}

	public void setExplosionBlockDamage(boolean explosionBlockDamage) {
		this.explosionBlockDamage = explosionBlockDamage;
		this.config.set("explosion-block-damage", explosionBlockDamage);
		this.config.save();
	}

	public void setFireSpread(boolean fireSpread) {
		this.fireSpread = fireSpread;
		this.config.set("fire-spread", fireSpread);
		this.config.save();
	}

	public void setMobGriefing(boolean mobGriefing) {
		this.mobGriefing = mobGriefing;
		this.config.set("mob-griefing", mobGriefing);
		this.config.save();
	}

	public void setHunger(boolean hunger) {
		this.hunger = hunger;
		this.config.set("hunger", hunger);
		this.config.save();
	}

	public void setNaturalRegen(boolean naturalRegen) {
		this.naturalRegen = naturalRegen;
		this.config.set("natural-regen", naturalRegen);
		this.config.save();
	}

	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
		this.config.set("player-limit", playerLimit);
		this.config.save();
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.config.set("difficulty", difficulty);
		this.config.save();
	}

	public void refreshData() {
		this.config = new Config(new File(Server.getInstance().getDataPath() + "/worlds/" + this.getAsLevel().getFolderName(), "config.yml"));
		this.loadonstart = this.config.getBoolean("LoadOnStart");
		this.gamemode = this.config.getInt("Gamemode");
		this.protect = this.config.getBoolean("protected");
		this.fly = this.config.getBoolean("fly");
		this.respawnworld = this.config.getString("respawnworld");
		try {
			this.thumbnail = this.config.getString("thumbnail");
		} catch (Exception e) {
			this.thumbnail = "path::textures/blocks/grass_side_carried.png";
		}
		this.note = this.config.getString("note");
		this.timelock = this.config.getInt("timelock", -1);
		this.weather = this.config.getString("weather", "");
		this.animals = this.config.getBoolean("animals", true);
		this.monsters = this.config.getBoolean("monsters", true);
		this.pvp = this.config.getBoolean("pvp", true);
		this.instantRespawn = this.config.getBoolean("instant-respawn", false);
		this.keepInventory = this.config.getBoolean("keep-inventory", false);
		this.keepExperience = this.config.getBoolean("keep-experience", false);
		this.fallDamage = this.config.getBoolean("fall-damage", true);
		this.fireDamage = this.config.getBoolean("fire-damage", true);
		this.lavaDamage = this.config.getBoolean("lava-damage", true);
		this.drowning = this.config.getBoolean("drowning", true);
		this.starvation = this.config.getBoolean("starvation", true);
		this.explosionDamage = this.config.getBoolean("explosion-damage", true);
		this.explosionBlockDamage = this.config.getBoolean("explosion-block-damage", false);
		this.fireSpread = this.config.getBoolean("fire-spread", true);
		this.mobGriefing = this.config.getBoolean("mob-griefing", true);
		this.hunger = this.config.getBoolean("hunger", true);
		this.naturalRegen = this.config.getBoolean("natural-regen", true);
		this.playerLimit = this.config.getInt("player-limit", -1);
		this.difficulty = this.config.getInt("difficulty", -1);
	}
}
