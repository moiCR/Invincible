package git.snowk.invincible.utils.menu.button.impl;

import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PanelButton implements Button {

    @Override
    public ItemStack icon() {
        return ItemMaker.of(Material.STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .setDurability(15)
                .build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {

    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
