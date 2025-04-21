package git.snowk.invincible.utils.menu.listener;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Objects;
import java.util.Optional;


public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Optional<Menu> openedMenu = Invincible.getInstance().getMenuManager().getOpenedMenu(player);

        openedMenu.ifPresent(menu -> {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            if (!Objects.equals(event.getClickedInventory(), menu.getInventory())) {
                event.setCancelled(!menu.isAllowPlayerMoveInventory());
                return;
            }

            event.setCancelled(!menu.isAllowAddItems());

            menu.getDecorationButtons().forEach((key, button) -> {
                if (key == event.getSlot()) {
                    event.setCancelled(!button.isInteractable());
                    button.setAction(event);
                }
            });

            menu.getButtons().forEach((key, button) -> {
                if (key == event.getSlot()) {
                    event.setCancelled(!button.isInteractable());
                    button.setAction(event);
                    if (menu.isSoundOnClick()) {
                        CompatibleSound.CLICK.play(player);
                    }
                }
            });

        });
    }


    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Optional<Menu> openedMenu = Invincible.getInstance().getMenuManager().getOpenedMenu(player);

        openedMenu.ifPresent(menu -> {
            menu.onClose(event);
            menu.clean();
        });
    }
}
