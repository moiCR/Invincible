package git.snowk.invincible.modules.crate.hologram.event;

import git.snowk.invincible.modules.crate.Crate;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class CrateHologramUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Crate crate;

    public CrateHologramUpdateEvent(Crate crate) {
        this.crate = crate;
        Bukkit.getPluginManager().callEvent(this);
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
