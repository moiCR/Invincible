package git.snowk.invincible.modules.crate.menu.edit.rewards;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateEditRewardsMenu extends Menu {

    private final Crate crate;

    public CrateEditRewardsMenu(Player player, Crate crate) {
        super(player, "Managing Rewards: " + crate.getCrateName(), crate.getRows(), false);
        this.crate = crate;

        setAllowAddItems(true);
        setAllowPlayerMoveInventory(true);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        for (CrateReward reward : crate.getRewards()){
            buttons.put(reward.getSlot(), new RewardButton(reward));
        }

        return buttons;
    }

    @AllArgsConstructor
    private static class RewardButton implements Button {

        private CrateReward reward;

        @Override
        public ItemStack icon() {
            return reward.getItem();
        }

        @Override
        public void setAction(InventoryClickEvent event) {
        }

        @Override
        public boolean isInteractable() {
            return false;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        ItemStack[] contents = getInventory().getContents();


        List<CrateReward> newRewards = new ArrayList<>();
        int i = 0;

        for (ItemStack item : contents) {
            if (item == null || item.getType() == Material.AIR) {
                i++;
                continue;
            }

            CrateReward existingReward = crate.getRewards().stream()
                    .filter(reward -> isSameItem(reward.getItem(), item))
                    .findFirst()
                    .orElse(null);

            double chance = (existingReward != null) ? existingReward.getChance() : 50.0;

            newRewards.add(new CrateReward(item, chance, i));
            i++;
        }

        crate.getRewards().clear();
        crate.getRewards().addAll(newRewards);
        CompatibleSound.NOTE_PLING.play(getPlayer());
        redirect(new CrateManageRewardsMenu(getPlayer(), crate));
    }

    private boolean isSameItem(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) return false;
        if (item1.getType() != item2.getType() || item1.getDurability() != item2.getDurability()) return false;

        if (!item1.hasItemMeta() || !item2.hasItemMeta()) return !item1.hasItemMeta() && !item2.hasItemMeta();

        return (item1.getItemMeta().hasDisplayName() == item2.getItemMeta().hasDisplayName() &&
                (!item1.getItemMeta().hasDisplayName() || item1.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName())) &&
                item1.getItemMeta().hasLore() == item2.getItemMeta().hasLore() &&
                (!item1.getItemMeta().hasLore() || item1.getItemMeta().getLore().equals(item2.getItemMeta().getLore())));
    }
}
