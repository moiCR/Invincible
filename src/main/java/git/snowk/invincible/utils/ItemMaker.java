package git.snowk.invincible.utils;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemMaker {

    private final ItemStack item;
    private final ItemMeta meta;

    private ItemMaker(ItemStack item){
        this.item = item.clone();
        this.meta = item.getItemMeta();
    }

    private ItemMaker(Material material){
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    private ItemMaker(String material){
        this.item = new ItemStack(Material.getMaterial(material));
        this.meta = item.getItemMeta();
    }

    public static ItemMaker of(ItemStack item){
        return new ItemMaker(item);
    }

    public static ItemMaker of(Material material){
        return new ItemMaker(material);
    }

    public static ItemMaker of(String material){
        return new ItemMaker(material);
    }

    public ItemMaker setDisplayName(String displayName){
        meta.setDisplayName(Colorizer.colorize(displayName));
        return this;
    }

    public ItemMaker setLore(List<String> lore){
        meta.setLore(Colorizer.colorizeList(lore));
        return this;
    }

    public ItemMaker setLore(String... lore){
        meta.setLore(Colorizer.colorizeList(lore));
        return this;
    }

    public ItemMaker addLore(String... lore){
        if (!meta.hasLore()) meta.setLore(new ArrayList<>());
        if (meta.getLore() == null) meta.setLore(new ArrayList<>());

        List<String> loreList = new ArrayList<>(meta.getLore());
        loreList.addAll(Arrays.asList(lore));
        meta.setLore(Colorizer.colorizeList(loreList));
        return this;
    }

    public ItemMaker setDurability(int durability){
        item.setDurability((short) durability);
        return this;
    }

    public ItemMaker setAmount(int amount){
        item.setAmount(amount);
        return this;
    }

    public ItemMaker addAllFlags(){
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemMaker addFlag(ItemFlag flag){
        meta.addItemFlags(flag);
        return this;
    }

    public ItemMaker removeFlag(ItemFlag flag){
        meta.removeItemFlags(flag);
        return this;
    }

    public ItemMaker removeAllFlags(){
        meta.removeItemFlags(ItemFlag.values());
        return this;
    }

    public ItemMaker setArmorColor(Color color){
        if (!item.getType().name().startsWith("LEATHER_")){
            return this;
        }
        LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;
        leatherMeta.setColor(color);
        return this;
    }

    public ItemMaker addEnchant(Enchantment enchantment, int level){
        item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemStack build(){
        item.setItemMeta(meta);
        return item;
    }

}
