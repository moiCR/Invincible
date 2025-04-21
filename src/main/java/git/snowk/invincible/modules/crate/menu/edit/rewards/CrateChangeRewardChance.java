package git.snowk.invincible.modules.crate.menu.edit.rewards;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.prompt.RewardEditChancePrompt;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.ItemMaker;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateChangeRewardChance extends Menu {

    private final Crate crate;

    public CrateChangeRewardChance(Player player, Crate crate) {
        super(player, "Changing Rewards Chance: " + crate.getCrateName(), crate.getRows(), false);
        this.crate = crate;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        for (CrateReward reward : crate.getRewards()) {
            buttons.put(reward.getSlot(), new RewardButton(reward));
        }
        return buttons;
    }

    @AllArgsConstructor
    private class RewardButton implements Button {

        private CrateReward reward;

        @Override
        public ItemStack icon() {
            return ItemMaker.of(reward.getItem())
                    .setLore(
                            "",
                            "&c• &eCurrent Chance: &f" + reward.getChance(),
                            "",
                            "&7Click to edit the chance.")
                    .addAllFlags()
                    .build();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
            new RewardEditChancePrompt(crate, reward).startPrompt(getPlayer());
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        redirect(new CrateManageRewardsMenu(getPlayer(), crate));
    }
}
