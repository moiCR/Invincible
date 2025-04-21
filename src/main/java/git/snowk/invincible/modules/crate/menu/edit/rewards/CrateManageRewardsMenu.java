package git.snowk.invincible.modules.crate.menu.edit.rewards;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
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

public class CrateManageRewardsMenu extends Menu {

    private final Crate crate;

    public CrateManageRewardsMenu(Player player, Crate crate) {
        super(player, "Edititng Rewards: ", 4, false);
        this.crate = crate;
        setDecoration(true);
        setDecorationType(DecorationType.FILL);
        setDecorationItem(ItemMaker.of(Material.STAINED_GLASS_PANE).setDisplayName(" ").setDurability(15).build());
        setSoundOnClick(true);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(12, new ManageRewardsButton());
        buttons.put(14, new EditRewardsChanceButton());

        buttons.put(31, new BackButton(new CrateEditMenu(getPlayer(), crate)));
        return buttons;
    }

    private class ManageRewardsButton implements Button {

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.CHEST)
                    .setDisplayName("&aManage Rewards")
                    .setLore(
                            "",
                            "&c• &eCurrent Rewards: &f" + crate.getRewards().size(),
                            "",
                            "&7Click to manage the rewards.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateEditRewardsMenu(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    private class EditRewardsChanceButton implements Button {

        @Override
        public ItemStack icon() {
            return ItemMaker.of(Material.ENCHANTED_BOOK).addAllFlags()
                    .setDisplayName("&aEdit Rewards Chance")
                    .setLore(
                            "",
                            "&7Click to edit the rewards chance.")
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new CrateChangeRewardChance(getPlayer(), crate).open();
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }


}
