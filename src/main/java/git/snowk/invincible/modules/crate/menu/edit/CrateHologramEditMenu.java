package git.snowk.invincible.modules.crate.menu.edit;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.prompt.HologramEditLinePrompt;
import git.snowk.invincible.modules.crate.menu.prompt.HologramNewLinePrompt;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.paginated.BackButton;
import git.snowk.invincible.utils.menu.paginated.MenuPaginated;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateHologramEditMenu extends MenuPaginated {

    private final Crate crate;

    public CrateHologramEditMenu(Player player, Crate crate) {
        super(player, "Manage Crate Hologram", 2, false);
        this.crate = crate;
        getNavigateBar().put(3, new BackButton(new CrateEditMenu(player, crate)));
        getNavigateBar().put(4, new UpdateHologramButton());
        getNavigateBar().put(5, new AddLineButton());
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        int index = 0;

        for (String holoLine : crate.getHologram().getLines()){
            buttons.put(buttons.size(), new LineButton(holoLine, index));
            index++;
        }

        return buttons;
    }

    @AllArgsConstructor
    private class LineButton implements Button{

        private String line;
        private int index;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.PAPER)
                    .setDisplayName(line.isEmpty() ? "&7Empty String" : line)
                    .setLore("",
                            "&7Left-Click to edit the line.",
                            "&7Right-Click to remove the line.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()){
                new HologramEditLinePrompt(crate, index).startPrompt(getPlayer());
                return;
            }

            if (event.isRightClick()){
                crate.getHologram().getLines().remove(index);
                update();
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class AddLineButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.NETHER_STAR)
                    .setDisplayName("&eAdd line")
                    .setLore(
                            "",
                            "&7Left-Click to add new line",
                            "&7Right-Click to add empty line"
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()){
                new HologramNewLinePrompt(crate).startPrompt(getPlayer());
                return;
            }

            if (event.isRightClick()){
                crate.getHologram().getLines().add("");
                update();
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class UpdateHologramButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.EMERALD)
                    .setDisplayName("&eUpdate Hologram")
                    .setLore(
                            "",
                            "&7Click to update the hologram."
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            crate.getHologram().updateHologram();
            update();
            CompatibleSound.LEVEL_UP.play(getPlayer());
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
