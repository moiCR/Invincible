package git.snowk.invincible.modules.hook.hologram;

import git.snowk.invincible.modules.crate.Crate;
import org.bukkit.Location;

import java.util.List;

public interface IHologram {

    void createHologram(Crate crate, boolean haveItem);
    void removeHolograms(Crate crate);
    void updateHolograms(Crate crate);
}
