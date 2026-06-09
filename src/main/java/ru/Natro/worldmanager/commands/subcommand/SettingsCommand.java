package ru.Natro.worldmanager.commands.subcommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.api.WorldManagerOption;
import ru.Natro.worldmanager.utils.Cache;

public class SettingsCommand extends SubCommand{

	public static final String[] TIME_OPTIONS = {"Default (No Lock)", "Dawn (0)", "Day (1000)", "Noon (6000)", "Sunset (12000)", "Night (13000)", "Midnight (18000)"};
	public static final int[] TIME_VALUES = {-1, 0, 1000, 6000, 12000, 13000, 18000};
	public static final String[] WEATHER_OPTIONS = {"None", "Sun", "Rain", "Storm"};
	public static final String[] DIFFICULTY_OPTIONS = {"None", "Peaceful", "Easy", "Normal", "Hard"};

	public static final int IDX_LOAD_ON_START = 0;
	public static final int IDX_GAMEMODE = 1;
	public static final int IDX_DIFFICULTY = 2;
	public static final int IDX_PLAYER_LIMIT = 3;
	public static final int IDX_RESPAWN_WORLD = 4;
	public static final int IDX_INSTANT_RESPAWN = 5;
	public static final int IDX_KEEP_INVENTORY = 6;
	public static final int IDX_KEEP_EXPERIENCE = 7;
	public static final int IDX_FLY = 8;
	public static final int IDX_HUNGER = 9;
	public static final int IDX_PVP = 10;
	public static final int IDX_FALL_DAMAGE = 11;
	public static final int IDX_FIRE_DAMAGE = 12;
	public static final int IDX_LAVA_DAMAGE = 13;
	public static final int IDX_DROWNING = 14;
	public static final int IDX_STARVATION = 15;
	public static final int IDX_EXPLOSION_DAMAGE = 16;
	public static final int IDX_TIME_LOCK = 17;
	public static final int IDX_WEATHER_LOCK = 18;
	public static final int IDX_FIRE_SPREAD = 19;
	public static final int IDX_EXPLOSION_BLOCK_DAMAGE = 20;
	public static final int IDX_MOB_GRIEFING = 21;
	public static final int IDX_ANIMALS = 22;
	public static final int IDX_MONSTERS = 23;
	public static final int IDX_PROTECTED = 24;
	public static final int IDX_NOTE = 25;
	public static final int IDX_CUSTOM_START = 26;

	public SettingsCommand() {
		super("settings");
		this.setAliases(new String[] {
				"settings",
				"edit"
		});
	}

	@Override
	public CommandParameter[] getParameters() {

		LinkedList<CommandParameter> parameters = new LinkedList<>();
		parameters.add(CommandParameter.newEnum(this.getName(), this.getAliases()));
		parameters.add(CommandParameter.newType("world", true, CommandParamType.STRING));
		return parameters.toArray(new CommandParameter[parameters.size()]);

	}

	@Override
	public boolean execute(CommandSender sender, String arg1, String[] args) {

		if (sender instanceof ConsoleCommandSender) {
			 sender.sendMessage(WorldManager.prefix + "§cThis can only be done ingame.");
			 return false;
		  }
		  if (!sender.hasPermission("worldmanager.admin") && !sender.hasPermission("worldmanager.settings")) {

			 sender.sendMessage(WorldManager.prefix + "§cYou are lacking the permission §e'worldmanager.settings'.");
			 return false;

		  } else {

			 if (args.length == 1 || args.length == 2) {

				Level l = ((Player) sender).getLevel();
				if (args.length == 2) {
					if (Server.getInstance().getLevelByName(args[1]) != null) {
					   l = Server.getInstance().getLevelByName(args[1]);
					} else {
					   sender.sendMessage(WorldManager.prefix + "§cThis world does not exist.");
					   return false;
				    }
				}
				World w = Cache.getWorld(l);
				List < String > worlds = new ArrayList < > ();
				for (Level level : Server.getInstance().getLevels().values()) worlds.add(level.getName());
				FormWindowCustom fw = new FormWindowCustom("§3WorldSettings - " + l.getFolderName());

				fw.addElement(new ElementToggle("Load On Start", w.doesLoadOnStart()));
				fw.addElement(new ElementDropdown("Gamemode", Arrays.asList("Survival", "Creative", "Adventure", "Spectator", "None"), w.getOwnGamemode()));
				Difficulty diff = Difficulty.fromId(w.getDifficulty());
				fw.addElement(new ElementDropdown("Difficulty", Arrays.asList(DIFFICULTY_OPTIONS), diff != null ? diff.ordinal() : 0));
				fw.addElement(new ElementInput("Player Limit", "-1 for unlimited", String.valueOf(w.getPlayerLimit())));
				fw.addElement(new ElementDropdown("Respawn World", worlds, (worlds.contains(w.getRespawnWorld()) ? worlds.indexOf(w.getRespawnWorld()) : worlds.indexOf(l.getName()))));
				fw.addElement(new ElementToggle("Instant Respawn", w.isInstantRespawn()));
				fw.addElement(new ElementToggle("Keep Inventory", w.isKeepInventory()));
				fw.addElement(new ElementToggle("Keep Experience", w.isKeepExperience()));
				fw.addElement(new ElementToggle("Fly", w.isFlyAllowed()));
				fw.addElement(new ElementToggle("Hunger", w.hasHunger()));
				fw.addElement(new ElementToggle("PvP", w.isPvpEnabled()));
				fw.addElement(new ElementToggle("Fall Damage", w.hasFallDamage()));
				fw.addElement(new ElementToggle("Fire Damage", w.hasFireDamage()));
				fw.addElement(new ElementToggle("Lava Damage", w.hasLavaDamage()));
				fw.addElement(new ElementToggle("Drowning", w.hasDrowning()));
				fw.addElement(new ElementToggle("Starvation", w.hasStarvation()));
				fw.addElement(new ElementToggle("Explosion Damage", w.hasExplosionDamage()));
				fw.addElement(new ElementDropdown("Time Lock", Arrays.asList(TIME_OPTIONS), timePresetIndex(w.getTimeLock())));
				fw.addElement(new ElementDropdown("Weather Lock", Arrays.asList(WEATHER_OPTIONS), weatherPresetIndex(w.getWeatherLock())));
				fw.addElement(new ElementToggle("Fire Spread", w.hasFireSpread()));
				fw.addElement(new ElementToggle("Explosion Block Damage", w.hasExplosionBlockDamage()));
				fw.addElement(new ElementToggle("Mob Griefing", w.hasMobGriefing()));
				fw.addElement(new ElementToggle("Animals", w.isAnimalSpawnEnabled()));
				fw.addElement(new ElementToggle("Monsters", w.isMonsterSpawnEnabled()));
				fw.addElement(new ElementToggle("Protected", w.isProtected()));
				fw.addElement(new ElementInput("Notepad", "Sth. to remember like coords.", w.getNote()));

				Config c = w.getConfig();
				for (WorldManagerOption o : WorldManagerOption.getCustomOptions()) {
				    if (o.getValue() instanceof Boolean) {
					   fw.addElement(new ElementToggle(o.getDisplay(), c.getBoolean(o.getKey())));
				    } else if (o.getValue() instanceof String) {
					   fw.addElement(new ElementInput(o.getDisplay(), o.getDescription(), c.getString(o.getKey())));
				    } else if (o.getValue() instanceof Integer) {
					   fw.addElement(new ElementSlider(o.getDisplay(), 0, o.maxvalue, 1, c.getInt(o.getKey())));
				    }
				}

				((Player) sender).showFormWindow(fw);
			 } else sender.sendMessage(WorldManager.prefix + "§cDo /worldmanager settings [World].");

		  }

		return false;
	}

	static int timePresetIndex(int time) {
		for (int i = 0; i < TIME_VALUES.length; i++) {
			if (TIME_VALUES[i] == time) return i;
		}
		return 0;
	}

	static int weatherPresetIndex(String weather) {
		if (weather == null || weather.isEmpty()) return 0;
		for (int i = 1; i < WEATHER_OPTIONS.length; i++) {
			if (WEATHER_OPTIONS[i].equalsIgnoreCase(weather)) return i;
		}
		return 0;
	}

	enum Difficulty {
		NONE, PEACEFUL, EASY, NORMAL, HARD;

		static Difficulty fromId(int id) {
			for (Difficulty d : values()) {
				if (d.ordinal() == id) return d;
			}
			return null;
		}
	}
}
