package ru.Natro.worldmanager.listener;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockBeetroot;
import cn.nukkit.block.BlockCarrot;
import cn.nukkit.block.BlockFarmland;
import cn.nukkit.block.BlockPotato;
import cn.nukkit.block.BlockStemMelon;
import cn.nukkit.block.BlockStemPumpkin;
import cn.nukkit.block.BlockWheat;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityBlockChangeEvent;
import cn.nukkit.event.entity.EntityInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import ru.Natro.worldmanager.WorldManager;
import ru.Natro.worldmanager.utils.Cache;

public class CropProtectionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!isProtected(event.getBlock().getLevel().getName())) return;
        if (isCropBlock(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!isProtected(event.getBlock().getLevel().getName())) return;
        Block target = event.getBlockAgainst();
        if (isCropBlock(target) || target instanceof BlockFarmland) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!isProtected(event.getBlock().getLevel().getName())) return;
        Block block = event.getBlock();
        if (event.getAction() == Action.PHYSICAL && block instanceof BlockFarmland) {
            event.setCancelled(true);
            return;
        }
        if (isCropBlock(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (!isProtected(event.getBlock().getLevel().getName())) return;
        if (event.getBlock() instanceof BlockFarmland) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityBlockChangeEvent event) {
        if (!isProtected(event.getFrom().getLevel().getName())) return;
        if (event.getFrom() instanceof BlockFarmland) {
            event.setCancelled(true);
        }
    }

    private boolean isProtected(String worldName) {
        if (WorldManager.isProtectedWorld(worldName)) return true;
        try {
            cn.nukkit.level.Level level = cn.nukkit.Server.getInstance().getLevelByName(worldName);
            if (level != null) {
                ru.Natro.worldmanager.api.World world = Cache.getWorld(level);
                return world != null && world.isProtected();
            }
        } catch (Exception e) {
            WorldManager.get().getLogger().warning("CropProtection check error for " + worldName + ": " + e.getMessage());
        }
        return false;
    }

    private boolean isCropBlock(Block block) {
        return block instanceof BlockWheat
                || block instanceof BlockCarrot
                || block instanceof BlockPotato
                || block instanceof BlockBeetroot
                || block instanceof BlockStemMelon
                || block instanceof BlockStemPumpkin;
    }
}
