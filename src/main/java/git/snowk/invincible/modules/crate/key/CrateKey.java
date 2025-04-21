package git.snowk.invincible.modules.crate.key;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.ItemUtils;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Data
public class CrateKey {

    private String displayName;
    private List<String> lore;
    private ItemStack item;

    public CrateKey(Crate crate){
        this.displayName = crate.getCrateName() + " Key";
        this.lore = new ArrayList<>();
        this.item = ItemMaker.of(Material.TRIPWIRE_HOOK).build();
    }

    public CrateKey(Map<String, Object> map){
        this.displayName = (String) map.get("displayName");
        this.lore = (List<String>) map.get("lore");
        this.item = ItemUtils.itemFromBase64((String) map.get("item"));
    }

    public ItemStack getKeyItem(){
        return ItemMaker.of(this.item.clone()).setDisplayName(this.displayName).setLore(this.lore).addAllFlags().build();
    }

    public ItemStack getItem(){
        return this.item.clone();
    }

    public Map<String, Object> serialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("displayName", this.displayName);
        map.put("lore", this.lore);
        map.put("item", ItemUtils.itemToBase64(this.item));
        return map;
    }



}
