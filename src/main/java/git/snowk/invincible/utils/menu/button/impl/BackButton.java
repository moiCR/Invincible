package git.snowk.invincible.utils.menu.button.impl;

import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BackButton implements Button {

    @Getter @Setter
    private Menu previousMenu;

    public BackButton(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    @Override
    public ItemStack icon() {
        ItemMaker maker = ItemMaker.of(Material.REDSTONE);
        maker.setDisplayName("&cBack");
        maker.setLore("&7Click to go back.");
        return maker.build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {
        if (previousMenu != null) {
            previousMenu.open();
        }
    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
