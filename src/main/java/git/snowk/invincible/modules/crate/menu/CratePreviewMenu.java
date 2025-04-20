package git.snowk.invincible.modules.crate.menu;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.menu.Menu;
import git.snowk.invincible.utils.menu.button.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CratePreviewMenu extends Menu {

    private final Crate crate;

    public CratePreviewMenu(Player player, Crate crate) {
        super(player, crate.getTitle()
                .replace("<crate>", crate.getCrateName()), crate.getRows(), false);

        this.crate = crate;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        for (CrateReward reward : this.crate.getRewards()){
            buttons.put(reward.getSlot(), new RewardButton(reward));
        }

        return buttons;
    }

    private record RewardButton(CrateReward reward) implements Button {

        @Override
            public ItemStack icon() {
                return reward.getItem().clone();
            }

            @Override
            public void setAction(InventoryClickEvent event) {

            }

            @Override
            public boolean isInteractable() {
                return false;
            }
    }
}
