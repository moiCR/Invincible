package git.snowk.invincible.modules.crate.menu.edit.menu;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.modules.crate.menu.prompt.MenuEditTitlePrompt;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.BackButton;
import git.snowk.invincible.utils.menu.decoration.DecorationType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateMenuEditorMenu extends Menu {

    private final Crate crate;

    public CrateMenuEditorMenu(Player player, Crate crate) {
        super(player, "Editing: " + crate.getCrateName(), 4, false);
        this.crate = crate;

        setDecoration(true);
        setDecorationType(DecorationType.FILL);
        setDecorationItem(ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName(" ").setDurability(15).build());

        setSoundOnClick(true);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(12, new TitleButton());
        buttons.put(14, new RowsButton());

        buttons.put(31, new BackButton(new CrateEditMenu(getPlayer(), crate)));
        return buttons;
    }

    private class TitleButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.SIGN)
                    .setDisplayName("&aSet Title")
                    .setLore(
                            "",
                            "&c• &eCurrent Title: &f" + crate.getTitle(),
                            "",
                            "&7Click to edit the title.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new MenuEditTitlePrompt(crate).startPrompt(getPlayer());
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class RowsButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.ARMOR_STAND)
                    .setDisplayName("&aSet Rows")
                    .setLore(
                            "",
                            "&c• &eCurrent Rows: &f" + crate.getRows(),
                            "",
                            "&7Left-Click to decrease.",
                            "&7Right-Click to increase.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            if (event.isLeftClick()){
                if (crate.getRows() == 1){
                    return;
                }
                crate.setRows(crate.getRows() - 1);
                update();
                return;
            }

            if (event.isRightClick()){
                if (crate.getRows() == 6){
                    return;
                }
                crate.setRows(crate.getRows() + 1);
                update();
            }
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
