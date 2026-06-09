package ru.Natro.worldmanager.listener;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerGameModeChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.GameRules;
import cn.nukkit.level.GameRules.Type;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.api.WorldManagerOption;
import ru.Natro.worldmanager.commands.subcommand.SettingsCommand;
import ru.Natro.worldmanager.utils.Cache;

public class Events implements Listener {

    @EventHandler
    public void onLevelLoad(LevelLoadEvent e) {

        File file = new File(Server.getInstance().getDataPath() + "worlds/" + e.getLevel().getFolderName(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        Config c = new Config(file);

        if (!c.exists("version")) c.set("version", 0);
        if (!c.exists("Gamemode")) c.set("Gamemode", 4);
        if (!c.exists("fly")) c.set("fly", false);
        if (!c.exists("respawnworld")) c.set("respawnworld", e.getLevel().getName());
        if (!c.exists("thumbnail")) c.set("thumbnail", "path::" + ((e.getLevel().getDimension() == 0) ? "textures/blocks/grass_side_carried.png" : (e.getLevel().getDimension() == 1) ? "textures/blocks/netherrack.png" : "textures/blocks/end_stone.png"));
        if (!c.exists("protected")) c.set("protected", false);
        if (!c.exists("note")) c.set("note", "");
        if (!c.exists("timelock")) c.set("timelock", -1);
        if (!c.exists("weather")) c.set("weather", "");
        if (!c.exists("animals")) c.set("animals", true);
        if (!c.exists("monsters")) c.set("monsters", true);
        if (!c.exists("pvp")) c.set("pvp", true);
        if (!c.exists("instant-respawn")) c.set("instant-respawn", false);
        if (!c.exists("keep-inventory")) c.set("keep-inventory", false);
        if (!c.exists("keep-experience")) c.set("keep-experience", false);
        if (!c.exists("fall-damage")) c.set("fall-damage", true);
        if (!c.exists("fire-damage")) c.set("fire-damage", true);
        if (!c.exists("lava-damage")) c.set("lava-damage", true);
        if (!c.exists("drowning")) c.set("drowning", true);
        if (!c.exists("starvation")) c.set("starvation", true);
        if (!c.exists("explosion-damage")) c.set("explosion-damage", true);
        if (!c.exists("explosion-block-damage")) c.set("explosion-block-damage", false);
        if (!c.exists("fire-spread")) c.set("fire-spread", true);
        if (!c.exists("mob-griefing")) c.set("mob-griefing", true);
        if (!c.exists("hunger")) c.set("hunger", true);
        if (!c.exists("player-limit")) c.set("player-limit", -1);
        if (!c.exists("difficulty")) c.set("difficulty", -1);
        for (WorldManagerOption option: WorldManagerOption.getCustomOptions()) {
            if (!c.exists(option.getKey())) {
                c.set(option.getKey(), option.getValue());
            }
        }

        c.save();

        try {
        	Cache.initWorld(e.getLevel());
		} catch (Exception e2) {
			WorldManager.get().getLogger().warning("Failed to load all worlds in cache!");
		}

    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {

    	Player p = e.getPlayer();

    	if(e.getTo().getLevel() == null || e.getFrom().getLevel() == null) return;

        World world = Cache.getWorld(e.getTo().getLevel());
        if (p.hasPermission("worldmanager.deny." + e.getTo().getLevel()) && !e.getPlayer().hasPermission("*")) {
            e.setCancelled(true);
            return;
        }

        if (!e.getTo().getLevel().equals(e.getFrom().getLevel())) {

            if (world.isUsingOwnGamemode()) {
                Cache.gamemodes.put(p.getName(), (byte) p.getGamemode());
                p.setGamemode(world.getOwnGamemode());

            } else if (Cache.gamemodes.containsKey(p.getName())) {
                p.setGamemode(Cache.gamemodes.get(p.getName()));
                Cache.gamemodes.remove(p.getName());
            }

            if (p.getGamemode() != 1) p.setAllowFlight(world.isFlyAllowed());

        }

    }

    HashMap < String, String > respawnworld = new HashMap < > ();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        respawnworld.put(e.getEntity().getName(), Cache.getWorld(e.getEntity().getLevel()).getRespawnWorld());

    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        World world = Cache.getWorld(e.getRespawnPosition().getLevel());
        if (e.isFirstSpawn() && e.getPlayer().getGamemode() != 1) e.getPlayer().setAllowFlight(world.isFlyAllowed());

        if (respawnworld.containsKey(e.getPlayer().getName())) {
            Level l = Server.getInstance().getLevelByName(respawnworld.get(e.getPlayer().getName()));

            if (l != null) {
                if (e.getPlayer().getSpawn().getLevel().getId() == l.getId()) {
                    e.setRespawnPosition(e.getPlayer().getSpawn());
                } else e.setRespawnPosition(l.getSafeSpawn());
            }
            respawnworld.remove(e.getPlayer().getName());
        }

        if (world.isUsingOwnGamemode()) {
            Cache.gamemodes.put(e.getPlayer().getName(), (byte) e.getPlayer().getGamemode());
            e.getPlayer().setGamemode(world.getOwnGamemode());

        } else if (Cache.gamemodes.containsKey(e.getPlayer().getName())) {
            e.getPlayer().setGamemode(Cache.gamemodes.get(e.getPlayer().getName()));
            Cache.gamemodes.remove(e.getPlayer().getName());
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onForm(PlayerFormRespondedEvent e) {

        if (e.getWindow() instanceof FormWindowCustom) {
            FormWindowCustom fw = (FormWindowCustom) e.getWindow();

            if (fw.getTitle().startsWith("§3WorldSettings")) {
                String level = fw.getTitle().replace("§3WorldSettings - ", "");
                if (fw.getResponse() == null) {
                    e.getPlayer().sendMessage(WorldManager.prefix + "§7Didn't save settings for §8" + level);
                    return;
                }

                Config c = new Config(new File(Server.getInstance().getDataPath() + "/worlds/" + level, "config.yml"));
                c.set("LoadOnStart", fw.getResponse().getResponse(SettingsCommand.IDX_LOAD_ON_START));
                c.set("Gamemode", fw.getResponse().getDropdownResponse(SettingsCommand.IDX_GAMEMODE).getElementID());
                c.set("difficulty", fw.getResponse().getDropdownResponse(SettingsCommand.IDX_DIFFICULTY).getElementID() - 1);
                String playerLimitStr = fw.getResponse().getInputResponse(SettingsCommand.IDX_PLAYER_LIMIT);
                try {
                    c.set("player-limit", Integer.parseInt(playerLimitStr));
                } catch (Exception ex) {
                    c.set("player-limit", -1);
                }
                c.set("respawnworld", fw.getResponse().getResponse(SettingsCommand.IDX_RESPAWN_WORLD));
                c.set("instant-respawn", fw.getResponse().getResponse(SettingsCommand.IDX_INSTANT_RESPAWN));
                c.set("keep-inventory", fw.getResponse().getResponse(SettingsCommand.IDX_KEEP_INVENTORY));
                c.set("keep-experience", fw.getResponse().getResponse(SettingsCommand.IDX_KEEP_EXPERIENCE));
                c.set("fly", fw.getResponse().getResponse(SettingsCommand.IDX_FLY));
                c.set("hunger", fw.getResponse().getResponse(SettingsCommand.IDX_HUNGER));
                c.set("pvp", fw.getResponse().getResponse(SettingsCommand.IDX_PVP));
                c.set("fall-damage", fw.getResponse().getResponse(SettingsCommand.IDX_FALL_DAMAGE));
                c.set("fire-damage", fw.getResponse().getResponse(SettingsCommand.IDX_FIRE_DAMAGE));
                c.set("lava-damage", fw.getResponse().getResponse(SettingsCommand.IDX_LAVA_DAMAGE));
                c.set("drowning", fw.getResponse().getResponse(SettingsCommand.IDX_DROWNING));
                c.set("starvation", fw.getResponse().getResponse(SettingsCommand.IDX_STARVATION));
                c.set("explosion-damage", fw.getResponse().getResponse(SettingsCommand.IDX_EXPLOSION_DAMAGE));
                int timeIdx = fw.getResponse().getDropdownResponse(SettingsCommand.IDX_TIME_LOCK).getElementID();
                c.set("timelock", timeIdx >= 0 && timeIdx < SettingsCommand.TIME_VALUES.length ? SettingsCommand.TIME_VALUES[timeIdx] : -1);
                int weatherIdx = fw.getResponse().getDropdownResponse(SettingsCommand.IDX_WEATHER_LOCK).getElementID();
                c.set("weather", weatherIdx >= 1 && weatherIdx < SettingsCommand.WEATHER_OPTIONS.length ? SettingsCommand.WEATHER_OPTIONS[weatherIdx] : "");
                c.set("fire-spread", fw.getResponse().getResponse(SettingsCommand.IDX_FIRE_SPREAD));
                c.set("explosion-block-damage", fw.getResponse().getResponse(SettingsCommand.IDX_EXPLOSION_BLOCK_DAMAGE));
                c.set("mob-griefing", fw.getResponse().getResponse(SettingsCommand.IDX_MOB_GRIEFING));
                c.set("animals", fw.getResponse().getResponse(SettingsCommand.IDX_ANIMALS));
                c.set("monsters", fw.getResponse().getResponse(SettingsCommand.IDX_MONSTERS));
                c.set("protected", fw.getResponse().getResponse(SettingsCommand.IDX_PROTECTED));
                c.set("note", fw.getResponse().getResponse(SettingsCommand.IDX_NOTE));
                int index = SettingsCommand.IDX_CUSTOM_START;
                for (WorldManagerOption o : WorldManagerOption.getCustomOptions()) {
                    c.set(o.getKey(), fw.getResponse().getResponse(index));
                    index++;
                }
                c.save();

                for (Player p: Server.getInstance().getLevelByName(level).getPlayers().values()) {
                    if (p.getGamemode() != 1) {
                        p.setAllowFlight(c.getBoolean("fly"));
                    }
                }

                if (c.getInt("Gamemode") != 4)
                    for (Player p: Server.getInstance().getLevelByName(level).getPlayers().values())
                        if (p.getGamemode() != c.getInt("Gamemode")) {
                            Cache.gamemodes.put(p.getName(), (byte) p.getGamemode());
                            p.setGamemode(c.getInt("Gamemode"));
                        }

                e.getPlayer().sendMessage(WorldManager.prefix + "§7Saved settings for §8" + level);
                Cache.initWorld(level);

            } else if (fw.getTitle().startsWith("§3WorldGamerules")) {
                Level level = Server.getInstance().getLevelByName(fw.getTitle().replace("§3WorldGamerules - ", ""));
                GameRules gamerules = level.gameRules;
                int i = 0;
                if (fw.getResponse() == null) {
                    e.getPlayer().sendMessage(WorldManager.prefix + "§7Didn't save gamerules for §8" + level.getName());
                    return;
                }
                for (GameRule r: GameRule.values()) {
                    if (fw.getResponse().getResponses().get(i) instanceof Boolean) {
                        gamerules.setGameRule(r, fw.getResponse().getToggleResponse(i));
                    } else if (level.getGameRules().getGameRuleType(r) == Type.INTEGER) {
                        try {
                            gamerules.setGameRule(r, Integer.valueOf(fw.getResponse().getInputResponse(i)));
                        } catch (Exception e2) {
                            e.getPlayer().sendMessage(WorldManager.prefix + "§cError with the gamerule §8" + r.getName() + "§c. It will remain on §8" + level.getGameRules().getInteger(r));
                        }
                    }
                    i++;
                }
                level.gameRules = gamerules;
                e.getPlayer().sendMessage(WorldManager.prefix + "§7Saved gamerules for §8" + level.getName());
            } else if (fw.getTitle().startsWith("§3WorldSync")) {

                if (fw.getResponse() == null) {
                    e.getPlayer().sendMessage(WorldManager.prefix + "§7Didn't synced settings and gamerules for your selection.");
                    return;
                }

                try {

                    Level level = Server.getInstance().getLevelByName(fw.getTitle().split(" - ")[1]);
                    Config c = Cache.getWorld(level).getConfig();
                    int i = 1;
                    for (Level l: Server.getInstance().getLevels().values()) {
                        if (l == level) continue;
                        if (fw.getResponse().getToggleResponse(i)) {

                            Config c2 = Cache.getWorld(l).getConfig();
                            c2.setAll((LinkedHashMap < String, Object > ) c.getAll());
                            c2.save();
                            l.gameRules = level.gameRules;
                            Cache.initWorld(l);
                        }
                        i++;
                    }
                    e.getPlayer().sendMessage(WorldManager.prefix + "§7Synced all selected worlds with §8" + level.getName() + ".");

                } catch (Exception e2) {
                    e.getPlayer().sendMessage(WorldManager.prefix + "§cSomething went wrong while syncing your worlds.");
                    return;
                }

            }
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        World world = Cache.getWorld(e.getEntity().getLevel());
        try {
            if (!(e.getEntity() instanceof Player) && e.getDamager() instanceof Player && world.isProtected()) {
                e.setCancelled(true);
            }
        } catch (Exception e2) {}

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e == null) return;
        try {
            if (Cache.getWorld(e.getBlock().getLevel()).isProtected()) e.setCancelled(true);
        } catch (Exception e2) {

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e == null) return;
        try {
            if (Cache.getWorld(e.getBlock().getLevel()).isProtected()) e.setCancelled(true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (Cache.getWorld(e.getBlock().getLevel()).isProtected()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
                e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void on(PlayerGameModeChangeEvent e) {
        if (e.getNewGamemode() == 0 || e.getNewGamemode() == 2) {
            e.getPlayer().setAllowFlight(Cache.getWorld(e.getPlayer().getLevel()).isFlyAllowed());
        }
    }

}
