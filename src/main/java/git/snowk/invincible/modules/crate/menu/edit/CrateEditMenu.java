package git.snowk.invincible.modules.crate.menu.edit;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.ChooseCrateMenu;
import git.snowk.invincible.modules.crate.menu.edit.hologram.CrateHologramEditMenu;
import git.snowk.invincible.modules.crate.menu.edit.key.CrateKeyEditorMenu;
import git.snowk.invincible.modules.crate.menu.edit.locations.CrateManageLocationMenu;
import git.snowk.invincible.modules.crate.menu.edit.menu.CrateMenuEditorMenu;
import git.snowk.invincible.modules.crate.menu.edit.rewards.CrateManageRewardsMenu;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.button.impl.BackButton;
import git.snowk.invincible.utils.menu.decoration.DecorationType;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CrateEditMenu extends Menu {

    private final Crate crate;

    public CrateEditMenu(Player player, Crate crate) {
        super(player, "Editing: " + crate.getCrateName(), 6, false);
        this.crate = crate;
        setSoundOnClick(true);
        setDecoration(true);
        setDecorationType(DecorationType.FILL);
        setDecorationItem(ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName(" ").setDurability(15).build());
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(12, new TypeButton(crate));
        buttons.put(13, new HologramButton(crate));
        buttons.put(14, new MenuButton(crate));
        buttons.put(21, new LocationsButton(crate));
        buttons.put(22, new LootButton(crate));
        buttons.put(23, new KeyButton(crate));
        buttons.put(39, new BackButton(new ChooseCrateMenu(getPlayer())));
        buttons.put(41, new RemoveButton());
        return buttons;
    }

    @AllArgsConstructor
    private class TypeButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.NAME_TAG)
                    .setDisplayName("&aSet Type")
                    .setLore(
                            "",
                            "&c• &eCurrent Type: &f" + crate.getCrateType().name(),
                            "",
                            "&7Click to set the type.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new ChooseCrateTypeMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @AllArgsConstructor
    private class MenuButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.SIGN)
                    .setDisplayName("&aMenu Editor")
                    .setLore(
                            "",
                            "&c• &eTitle: &f" + crate.getTitle(),
                            "&c• &eRows: &f" + crate.getRows(),
                            "",
                            "&7Click to edit the menu."
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateMenuEditorMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @AllArgsConstructor
    private class LocationsButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.COMPASS)
                    .setDisplayName("&aManage Locations")
                    .setLore(
                            "",
                            "&c• &eCurrent Locations: &f" + crate.getLocations().size(),
                            "",
                            "&7Click to manage the locations."
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateManageLocationMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @AllArgsConstructor
    private class LootButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of("CHEST")
                    .setDisplayName("&aLoot Editor")
                    .setLore(
                            "",
                            "&c• &eCurrent Rewards: &f" + crate.getRewards().size(),
                            "",
                            "&7Click to edit the loot."
                    )
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateManageRewardsMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @AllArgsConstructor
    private class KeyButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            List<String> lore = new ArrayList<>();

            lore.add("");
            lore.add("&c• &eItem: &f" + crate.getKey().getItem().getType().name() + ":" + crate.getKey().getItem().getDurability());
            lore.add("&c• &eDisplayName: &f" + crate.getKey().getDisplayName());
            lore.add("&c• &eLore:");
            for (String line : crate.getKey().getLore()) {
                lore.add("  " + line);
            }
            lore.add("");
            lore.add("&7Click to edit the key.");

            return ItemMaker.of(crate.getKey().getItem())
                    .setDisplayName("&aKey Editor")
                    .setLore(lore)
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateKeyEditorMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class RemoveButton implements Button {

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.BARRIER)
                    .setDisplayName("&cRemove")
                    .setLore("&7Click to remove this crate.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateConfirmRemoveMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @AllArgsConstructor
    private class HologramButton implements Button {

        private Crate crate;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.PAPER)
                    .setDisplayName("&aHologram Editor")
                    .setLore("",
                            "&7Click to manage the hologram.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateHologramEditMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

}
