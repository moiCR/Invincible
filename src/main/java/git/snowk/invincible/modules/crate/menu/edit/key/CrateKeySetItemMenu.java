package git.snowk.invincible.modules.crate.menu.edit.key;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.utils.CompatibleSound;
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

public class CrateKeySetItemMenu extends Menu {

    private final Crate crate;

    public CrateKeySetItemMenu(Player player, Crate crate) {
        super(player, "Drag Item: " + crate.getCrateName(), 4, false);
        setDecoration(true);
        setDecorationType(DecorationType.FILL);
        setDecorationItem(ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName(" ").setDurability(15).build());
        this.crate = crate;

        setAllowPlayerMoveInventory(true);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(13, new KeyItemButton());
        buttons.put(31, new BackButton(new CrateKeyEditorMenu(getPlayer(), crate)));

        return buttons;
    }


    private class KeyItemButton implements Button{

        @Override
        public ItemStack icon() {
            return ItemMaker.of(crate.getKey().getItem())
                    .setDisplayName("&4&l&nDrag Item Here")
                    .addAllFlags()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            ItemStack cursor = event.getCursor();
            if (cursor == null) return;

            event.setCursor(null);
            crate.getKey().setItem(cursor);
            update();
            CompatibleSound.LEVEL_UP.play(getPlayer());
            crate.save();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }
}
