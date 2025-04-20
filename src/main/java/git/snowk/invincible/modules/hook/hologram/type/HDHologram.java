package git.snowk.invincible.modules.hook.hologram.type;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.hook.hologram.IHologram;
import git.snowk.invincible.utils.Colorizer;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HDHologram implements IHologram {

    private final Map<String, List<Hologram>> holograms;
    private final HolographicDisplaysAPI api;

    public HDHologram(Invincible plugin){
        this.api = HolographicDisplaysAPI.get(plugin);
        this.holograms = new HashMap<>();
    }


    @Override
    public void createHologram(Crate crate, boolean haveItem) {
        if (crate.getLocations().isEmpty()) return;

        for (Location oldLoc : crate.getLocations()){
            Location newLoc = oldLoc.clone().add(0.5, 1.5, 0.5);

            Hologram newHolo = api.createHologram(newLoc);

//            if (haveItem){
//
//            }

            for (String holoLine : crate.getHologram().getLines()){
                newHolo.getLines().appendText(Colorizer.colorize(holoLine).replace("<crate>", crate.getCrateName()));
            }

            newHolo.setPosition(newLoc);

            if (holograms.containsKey(crate.getCrateName())){
                List<Hologram> holoList = new ArrayList<>(holograms.get(crate.getCrateName()));
                holoList.add(newHolo);
                holograms.put(crate.getCrateName(), holoList);
            }else{
                holograms.put(crate.getCrateName(), new ArrayList<>());
                holograms.get(crate.getCrateName()).add(newHolo);
            }
        }
    }

    @Override
    public void removeHolograms(Crate crate) {
        if (holograms.containsKey(crate.getCrateName())){
            List<Hologram> holoList = holograms.get(crate.getCrateName());
            for (Hologram holo : holoList){
                holo.delete();
            }
            holograms.remove(crate.getCrateName());
        }
    }

    @Override
    public void updateHolograms(Crate crate) {
        removeHolograms(crate);
        createHologram(crate, false);
    }
}
