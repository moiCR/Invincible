package dev.snowk.dante.util.menu.button.paginated;


import dev.snowk.dante.util.ItemMaker;
import dev.snowk.dante.util.menu.button.Button;
import dev.snowk.dante.util.menu.paginated.MenuPaginated;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NextButton implements Button {

    private MenuPaginated paginatedMenu;

    public NextButton(MenuPaginated paginatedMenu) {
        this.paginatedMenu = paginatedMenu;
    }

    @Override
    public ItemStack icon() {
        ItemMaker maker = ItemMaker.of(Material.CARPET).setDurability((short) 7);
        maker.setDisplayName("&cNext Page");
        return maker.build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {
        paginatedMenu.nextPage();
        paginatedMenu.update();
    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
