package git.snowk.invincible.utils.menu.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Button {

    ItemStack icon();

    void setAction(InventoryClickEvent event);

    boolean isInteractable();
}
