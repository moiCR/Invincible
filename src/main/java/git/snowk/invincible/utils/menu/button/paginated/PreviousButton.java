package git.snowk.invincible.utils.menu.button.paginated;


import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PreviousButton implements Button {

    private MenuPaginated paginated;

    public PreviousButton(MenuPaginated paginated) {
        this.paginated = paginated;
    }

    @Override
    public ItemStack icon() {
        ItemMaker maker = ItemMaker.of(Material.CARPET).setDurability((short) 7);
        maker.setDisplayName("&cPrevious Page");
        return maker.build();
    }

    @Override
    public void setAction(InventoryClickEvent event) {
        paginated.previousPage();
        paginated.update();
    }

    @Override
    public boolean isInteractable() {
        return false;
    }
}
