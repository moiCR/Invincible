package git.snowk.invincible.utils.menu.button.impl;


import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NextButton implements Button {

    private final MenuPaginated paginatedMenu;

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
