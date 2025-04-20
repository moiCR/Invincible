package dev.snowk.dante.util.menu.button.paginated;

import dev.snowk.dante.util.ItemMaker;
import dev.snowk.dante.util.menu.Menu;
import dev.snowk.dante.util.menu.button.Button;
import dev.snowk.dante.util.menu.paginated.MenuPaginated;
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
        ItemMaker maker = ItemMaker.of(Material.ARROW);
        maker.setDisplayName("&cBack");
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
