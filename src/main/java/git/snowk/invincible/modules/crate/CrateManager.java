package git.snowk.invincible.modules.crate;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.commands.CrateCommand;
import git.snowk.invincible.modules.crate.listener.CrateListener;
import lombok.Getter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CrateManager {

    private final Map<String, Crate> crates;

    public CrateManager(){
        this.crates = new HashMap<>();
        Invincible.getInstance().getCommandManager().registerCommands(new CrateCommand());
        new CrateListener();
    }

    public void addCrate(Crate crate){
        this.crates.put(crate.getCrateName(), crate);
    }

    public boolean exists(String crateName){
        return this.crates.containsKey(crateName);
    }

    public void removeCrate(Crate crate){
        this.crates.remove(crate.getCrateName());
        Invincible.getInstance().getStorageManager().getStorage().removeCrate(crate);
    }

    public Crate getByName(String name){
        return this.crates.get(name);
    }

    public void onEnable(){
        for (Crate crate : this.crates.values()){
            Invincible.getInstance().getHookManager().getHologram().createHologram(crate, false);
        }
    }

    public void onDisable(){
        for (Crate crate : this.crates.values()){
            Invincible.getInstance().getHookManager().getHologram().removeHolograms(crate);
        }
    }

    public Crate getByLocation(Location location){
        for (Crate crate : this.crates.values()){
            if (crate.getLocations().stream().anyMatch(loc -> loc.equals(location))){
                return crate;
            }
        }
        return null;
    }
}
