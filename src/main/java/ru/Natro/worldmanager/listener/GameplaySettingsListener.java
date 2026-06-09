package ru.Natro.worldmanager.listener;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.CreatureSpawnEvent;
import cn.nukkit.level.Level;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.api.World;
import ru.Natro.worldmanager.utils.Cache;
import ru.Natro.worldmanager.utils.EntityTypeRegistry;

public class GameplaySettingsListener implements Listener {

    public GameplaySettingsListener() {
        scheduleTimeWeatherLock();
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        int networkId = e.getEntityNetworkId();
        String type = EntityTypeRegistry.getEntityType(networkId);
        Level level = e.getPosition().getLevel();
        if (level == null) return;
        World world = Cache.getWorld(level);
        if (world == null) return;

        if ("ANIMAL".equals(type) && !world.isAnimalSpawnEnabled()) {
            e.setCancelled(true);
        } else if ("MOB".equals(type) && !world.isMonsterSpawnEnabled()) {
            e.setCancelled(true);
        }
    }

    private void scheduleTimeWeatherLock() {
        Server.getInstance().getScheduler().scheduleRepeatingTask(WorldManager.get(), () -> {
            for (Level level : Server.getInstance().getLevels().values()) {
                World world = Cache.getWorld(level);
                if (world == null) continue;

                int timelock = world.getTimeLock();
                if (timelock >= 0) {
                    level.setTime(timelock);
                }

                String weather = world.getWeatherLock();
                if (weather == null || weather.isEmpty()) continue;
                if ("Sun".equalsIgnoreCase(weather)) {
                    level.setRaining(false);
                    level.setThundering(false);
                } else if ("Rain".equalsIgnoreCase(weather)) {
                    level.setRaining(true);
                    level.setThundering(false);
                } else if ("Storm".equalsIgnoreCase(weather)) {
                    level.setRaining(true);
                    level.setThundering(true);
                }
            }
        }, 100, false);
    }
}
