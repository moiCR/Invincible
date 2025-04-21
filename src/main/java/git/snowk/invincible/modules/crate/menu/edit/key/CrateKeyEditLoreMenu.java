package git.snowk.invincible.modules.crate.menu.edit.key;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.prompt.KeyLoreEditLinePrompt;
import git.snowk.invincible.modules.crate.menu.prompt.KeyLoreNewLinePrompt;
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

public class CrateKeyEditLoreMenu extends MenuPaginated {

    private final Crate crate;

    public CrateKeyEditLoreMenu(Player player, Crate crate) {
        super(player, "Editing Key Lore: " + crate.getCrateName(), 1, false);
        this.crate = crate;
        getNavigateBar().put(3, new BackButton(new CrateKeyEditorMenu(player, crate)));
        getNavigateBar().put(5, new NewLineButton());
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        int index = 0;
        Map<Integer, Button> buttons = new HashMap<>();

        for (String line : crate.getKey().getLore()){
            buttons.put(index, new LineButton(line, index));
            index++;
        }

        return buttons;
    }

    @AllArgsConstructor
    private class LineButton implements Button{

        private final String line;
        private final int index;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.PAPER)
                    .setDisplayName((line.isEmpty() ? "&cEmpty Line" : line))
                    .setLore("",
                            "&7Left Click to edit the line.",
                            "&7Right Click to remove the line.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()){
                new KeyLoreEditLinePrompt(index, crate).startPrompt(getPlayer());
                return;
            }

            if (event.isRightClick()){
                crate.getKey().getLore().remove(index);
                update();
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class NewLineButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.NETHER_STAR)
                    .setDisplayName("&aNew Line")
                    .setLore("",
                            "&7Left-Click to add a new line.",
                            "&7Right-Click to add empty line.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()){
                new KeyLoreNewLinePrompt(crate).startPrompt(getPlayer());
                return;
            }

            if (event.isRightClick()){
                crate.getKey().getLore().add("");
                update();
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
