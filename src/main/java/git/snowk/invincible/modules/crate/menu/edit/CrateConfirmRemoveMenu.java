package git.snowk.invincible.modules.crate.menu.edit;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateConfirmRemoveMenu extends Menu {

    private final Crate crate;

    public CrateConfirmRemoveMenu(Player player, Crate crate) {
        super(player, "Are you sure?", 3, false);
        this.crate = crate;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        int[] cofirmSlots = {0, 1, 2, 9, 10, 11, 18, 19, 20};
        int[] cancelSlots = {6, 7, 8, 15, 16, 17, 24, 25, 26};

        for (int slot : cofirmSlots) {
            buttons.put(slot, new ConfirmButton());
        }

        for (int slot : cancelSlots) {
            buttons.put(slot, new CancelButton());
        }

        return buttons;
    }

    private class ConfirmButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.WOOL)
                    .setDurability(5)
                    .setDisplayName("&a&lConfirm")
                    .setLore("&aClick here to remove the crate.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            crate.remove();
            sendMessage(getPlayer(), Invincible.getInstance().getLang().getString("CRATE.DELETE.SUCCESS").replace("%name%", crate.getCrateName()));
            CompatibleSound.NOTE_PLING.play(getPlayer());
            getPlayer().closeInventory();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class CancelButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.WOOL)
                    .setDurability(14)
                    .setDisplayName("&c&lCancel")
                    .setLore("&cClick here to cancel the crate removal.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            getPlayer().closeInventory();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
