package git.snowk.invincible.modules.hook;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.hook.hologram.IHologram;
import git.snowk.invincible.modules.hook.hologram.type.DHHologramType;
import git.snowk.invincible.modules.hook.hologram.type.NoneHologram;
import git.snowk.invincible.modules.hook.hologram.type.HDHologramType;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class HookManager {

    private String hologramType;
    private IHologram hologram;

    public HookManager(){
        hookHologram();
    }

    public void hookHologram(){
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null){
            hologram = new HDHologramType(Invincible.getInstance());
            hologramType = "HolographicDisplays";
        }else if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null){
            hologram = new DHHologramType();
            hologramType = "DecentHolograms";
        }
        else{
            hologram = new NoneHologram();
            hologramType = "None";
        }
    }

}