package git.snowk.invincible.modules.crate.listener;

import git.snowk.invincible.Invincible;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.hologram.event.CrateHologramUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrateListener implements Listener {

    public CrateListener() {
        Bukkit.getPluginManager().registerEvents(this, Invincible.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Block block = event.getClickedBlock();

        if (block == null) return;
        if (block.getType() == Material.AIR) return;

        Crate crate = Invincible.getInstance().getCrateManager().getCrate(block.getLocation());

        if (crate == null) return;

        crate.handle(crate, event, block.getLocation());
    }

    @EventHandler
    public void onCrateHologramUpdate(CrateHologramUpdateEvent event){
        // run in the main server thread due to asynchronous problems with HolographicDisplays
        Bukkit.getScheduler().runTask(Invincible.getInstance(), () -> {
            event.getCrate().getHologram().updateHologram();
        });
    }

}
