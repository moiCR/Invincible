package git.snowk.invincible.modules.crate.menu.edit.key;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.modules.crate.menu.prompt.KeyEditDisplayNamePrompt;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.BackButton;
import git.snowk.invincible.utils.menu.decoration.DecorationType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateKeyEditorMenu extends Menu {

    private final Crate crate;

    public CrateKeyEditorMenu(Player player, Crate crate) {
        super(player, "Key Editor: " + crate.getCrateName(), 4, false);

        setDecoration(true);
        setDecorationType(DecorationType.FILL);
        setDecorationItem(ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName(" ").setDurability(15).build());
        setSoundOnClick(true);
        this.crate = crate;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(11, new ItemButton());
        buttons.put(13, new DisplayNameButton());
        buttons.put(15, new KeyLoreButton());

        buttons.put(31, new BackButton(new CrateEditMenu(getPlayer(), crate)));

        return buttons;
    }

    private class ItemButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(crate.getKey().getItem())
                    .setDisplayName("&aClick here to edit the item key.")
                    .addAllFlags()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateKeySetItemMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class DisplayNameButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.NAME_TAG)
                    .setDisplayName("&aSet DisplayName")
                    .setLore(
                            "",
                            "&c• &eCurrent DisplayName: &f" + crate.getKey().getDisplayName(),
                            "",
                            "&7Click to edit the display name.")
                    .addAllFlags()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new KeyEditDisplayNamePrompt(crate).startPrompt(getPlayer());
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class KeyLoreButton implements Button{

        @Override
        public ItemStack icon() {
            List<String> lore = new ArrayList<>();

            lore.add("");
            lore.add("&c• &eCurrent Lore: ");

            for (String line : crate.getKey().getLore()){
                lore.add("  " + line);
            }
            lore.add("");
            lore.add("&7Click to edit the lore.");

            return ItemMaker.of(Material.PAPER)
                    .setDisplayName("&aManage Lore")
                    .setLore(lore)
                    .addAllFlags()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateKeyEditLoreMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
