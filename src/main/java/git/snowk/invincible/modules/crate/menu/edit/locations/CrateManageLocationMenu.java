package git.snowk.invincible.modules.crate.menu.edit.locations;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.BackButton;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CrateManageLocationMenu extends MenuPaginated {

    private final Crate crate;

    public CrateManageLocationMenu(Player player, Crate crate) {
        super(player, "Managing Location: " + crate.getCrateName(), 3, false);
        this.crate = crate;
        getNavigateBar().put(4, new BackButton(new CrateEditMenu(player, crate)));
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        return Map.of();
    }

    @AllArgsConstructor
    private class LocationButton implements Button {

        private final Location location;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.COMPASS)
                    .setDisplayName(location.getWorld().getName() + " - " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ())
                    .setLore(
                            "",
                            "&7Left Click to teleport to this location.",
                            "&7Right Click to remove this location."
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTask(Invincible.getInstance(), () -> getPlayer().teleport(location));
                update();
                return;
            }

            if (event.isRightClick()) {
                crate.removeLocation(location);
                update();
                CompatibleSound.NOTE_PLING.play(getPlayer());
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
