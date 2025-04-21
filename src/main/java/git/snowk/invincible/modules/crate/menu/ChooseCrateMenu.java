package git.snowk.invincible.modules.crate.menu;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.CloseButton;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class ChooseCrateMenu extends MenuPaginated {

    public ChooseCrateMenu(Player player) {
        super(player, "Choose Crate to edit", 6, false);
        setSoundOnClick(true);
        getNavigateBar().put(4, new CloseButton(this));
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        for (Crate crate : Invincible.getInstance().getCrateManager().getCrates().values()) {
            buttons.put(buttons.size(), new CrateButton(crate));
        }
        return buttons;
    }

    private class CrateButton implements Button {

        private final Crate crate;

        public CrateButton(Crate crate) {
            this.crate = crate;
        }

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.CHEST)
                    .setDisplayName("&c" + crate.getCrateName())
                    .setLore("",
                            "&aClick here to edit the crate.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateEditMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

}
