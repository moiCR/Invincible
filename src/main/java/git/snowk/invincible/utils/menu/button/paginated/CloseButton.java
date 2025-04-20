package git.snowk.invincible.utils.menu.button.paginated;


import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CloseButton implements Button {

    private MenuPaginated paginatedMenu;

    public CloseButton(MenuPaginated paginatedMenu) {
        this.paginatedMenu = paginatedMenu;
    }

    @Override
    public ItemStack icon() {
        ItemMaker maker = ItemMaker.of(Material.BARRIER);
        maker.setDisplayName("&cClose");
        return maker.build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {
        paginatedMenu.getPlayer().closeInventory();
    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
