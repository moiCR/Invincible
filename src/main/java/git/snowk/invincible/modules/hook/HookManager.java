package git.snowk.invincible.modules.hook;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.hook.hologram.IHologram;
import git.snowk.invincible.modules.hook.hologram.type.NoneHologram;
import git.snowk.invincible.modules.hook.hologram.type.HDHologram;
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
            hologram = new HDHologram(Invincible.getInstance());
            hologramType = "HolographicDisplays";
        }else{
            hologram = new NoneHologram();
            hologramType = "None";
        }
    }

}