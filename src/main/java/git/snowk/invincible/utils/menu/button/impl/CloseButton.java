package git.snowk.invincible.utils.menu.button.impl;


import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CloseButton implements Button {

    private final Menu menu;

    public CloseButton(Menu menu) {
        this.menu = menu;
    }

    @Override
    public ItemStack icon() {
        ItemMaker maker = ItemMaker.of(Material.BARRIER);
        maker.setDisplayName("&cClose");
        return maker.build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {
        menu.getPlayer().closeInventory();
    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
