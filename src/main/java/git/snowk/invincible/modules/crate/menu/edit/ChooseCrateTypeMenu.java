package git.snowk.invincible.modules.crate.menu.edit;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.type.CrateType;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.BackButton;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ChooseCrateTypeMenu extends MenuPaginated {

    private final Crate crate;

    public ChooseCrateTypeMenu(Player player, Crate crate) {
        super(player, "Choose a new Type", 2, false);
        this.crate = crate;
        getNavigateBar().put(4, new BackButton(new CrateEditMenu(player, crate)));
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        for (CrateType type : CrateType.values()) {
            buttons.put(buttons.size(), new CrateTypeButton(type, crate));
        }

        return buttons;
    }

    @AllArgsConstructor
    private class CrateTypeButton implements Button {

        private CrateType type;
        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.WOOL)
                    .setDurability(crate.getCrateType() == type ? 5 : 14)
                    .setDisplayName((crate.getCrateType() == type ? "&a" : "&c") + type.name())
                    .setLore()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            crate.setCrateType(type);
            update();
            CompatibleSound.NOTE_PIANO.play(getPlayer());
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
