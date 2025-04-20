package git.snowk.invincible.modules.hook.hologram.type;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHolograms;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.hook.hologram.IHologram;
import git.snowk.invincible.utils.Colorizer;
import org.bukkit.Location;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DHHologramType implements IHologram {

    private final Map<String, List<Hologram>> holograms;

    public DHHologramType(){
        this.holograms = new HashMap<>();
    }

    @Override
    public void createHologram(Crate crate, boolean haveItem) {
        if (crate.getLocations().isEmpty()) return;

        for (Location oldLoc : crate.getLocations()){
            int linesCount = crate.getHologram().getLines().size();
            List<String> lines = new ArrayList<>();

            for (String line : crate.getHologram().getLines()) {
                lines.add(Colorizer.colorize(line.replace("<crate>", crate.getCrateName())));
            }

            Location newLoc = oldLoc.clone().add(0.5, 1 + (0.25 * linesCount), 0.5);
            String hologramId = crate.getCrateName() + "_" + newLoc.getBlockX() + "_" + newLoc.getBlockY() + "_" + newLoc.getBlockZ();
            Hologram newHolo = DHAPI.createHologram(hologramId, newLoc, lines);
            holograms.computeIfAbsent(crate.getCrateName(), k -> new ArrayList<>()).add(newHolo);
        }
    }

    @Override
    public void removeHolograms(Crate crate) {
        if (holograms.containsKey(crate.getCrateName())){
            List<Hologram> holoList = holograms.get(crate.getCrateName());
            for (Hologram holo : holoList){
                DHAPI.removeHologram(holo.getId());
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
