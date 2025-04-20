package git.snowk.invincible.modules.crate.reward;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
public class CrateReward {

    private ItemStack item;
    private double chance;
    private int slot;

    public CrateReward(Map<String, Object> map){
        this.item = ItemStack.deserialize((Map<String, Object>) map.get("item"));
        this.chance = Double.parseDouble(String.valueOf(map.get("chance")));
        this.slot = Integer.parseInt(String.valueOf(map.get("slot")));
    }

    public CrateReward(ItemStack item, double chance, int slot) {
        this.item = item;
        this.chance = chance;
        this.slot = slot;
    }

    public CrateReward(ItemStack item, int slot) {
        this(item, 50.0, slot);
    }


    public Map<String, Object> serialize(){
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("item", item.serialize());
        map.put("chance", String.valueOf(chance));
        map.put("slot", String.valueOf(slot));
        return map;
    }

    public ItemStack getItem() {
        return item.clone();
    }
}
