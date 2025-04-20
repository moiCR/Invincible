package git.snowk.invincible.modules.crate.hologram;

import com.google.common.collect.Lists;
import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CrateHologram {

    private List<String> lines;
    private Crate crate;

    public CrateHologram(Crate crate){
        this.crate = crate;
        this.lines = Lists.newArrayList(
                "&e&l<crate> Crate",
                "&7&o&nstore.example.com"
        );
    }

    public CrateHologram(Map<String, Object> map, Crate crate){
        this.crate = crate;
        this.lines = (List<String>) map.get("lines");
    }


    public Map<String, Object> serialize(){
        return Map.of("lines", lines);
    }

    public void updateHologram(){
        Invincible.getInstance().getHookManager().getHologram().updateHolograms(crate);
    }
}
