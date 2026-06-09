package ru.Natro.worldmanager.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockIgniteEvent;
import cn.nukkit.event.entity.EntityBlockChangeEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Level;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.utils.Cache;

public class GameplayFeatureListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        World world = Cache.getWorld(player.getLevel());
        if (world == null) return;

        if (world.isKeepInventory()) {
            e.setKeepInventory(true);
        }
        if (world.isKeepExperience()) {
            e.setKeepExperience(true);
        }
        if (world.isInstantRespawn()) {
            Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
                if (player.isOnline() && player.getHealth() <= 0) {
                    player.teleport(player.getSpawn());
                    player.setHealth(player.getMaxHealth());
                }
            }, 1);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        Level level = e.getEntity().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;

        switch (e.getCause()) {
            case FALL:
                if (!world.hasFallDamage()) e.setCancelled(true);
                return;
            case FIRE:
            case FIRE_TICK:
                if (!world.hasFireDamage()) e.setCancelled(true);
                return;
            case LAVA:
                if (!world.hasLavaDamage()) e.setCancelled(true);
                return;
            case DROWNING:
                if (!world.hasDrowning()) e.setCancelled(true);
                return;
            case HUNGER:
                if (!world.hasStarvation()) e.setCancelled(true);
                return;
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
                if (!world.hasExplosionDamage()) e.setCancelled(true);
                return;
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;
        Level level = e.getEntity().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;
        if (!world.isPvpEnabled()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        Level level = e.getBlock().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;
        if (!world.hasFireSpread()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        Level level = e.getEntity().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;
        if (!world.hasExplosionBlockDamage()) {
            e.getBlockList().clear();
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityBlockChangeEvent e) {
        Level level = e.getFrom().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;
        if (!world.hasMobGriefing()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(PlayerFoodLevelChangeEvent e) {
        Level level = e.getPlayer().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;
        if (!world.hasHunger()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Level to = e.getTo().getLevel();
        if (to == null) return;
        World world = Cache.getWorld(to);
        if (world == null) return;
        int limit = world.getPlayerLimit();
        if (limit < 0) return;
        if (e.getPlayer().hasPermission("worldmanager.bypass.playerlimit")) return;
        int count = 0;
        for (Player p : to.getPlayers().values()) {
            if (!p.hasPermission("worldmanager.bypass.playerlimit")) {
                count++;
            }
        }
        if (count >= limit) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§3WorldManager §8» §cThis world has reached its player limit.");
        }
    }
}
