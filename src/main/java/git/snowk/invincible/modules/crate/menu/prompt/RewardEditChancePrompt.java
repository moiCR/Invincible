package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.rewards.CrateChangeRewardChance;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.DoublePrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class RewardEditChancePrompt extends DoublePrompt {

    private final Crate crate;
    private final CrateReward reward;

    @Override
    public void handleBegin(Player player) {
        sendMessage(player, "&cEnter the new chance for the reward. Type &6cancel &cto cancel the process.");
        player.closeInventory();
        CompatibleSound.NOTE_PIANO.play(player);
    }

    @Override
    public void handleCancel(Player player) {
        new CrateChangeRewardChance(player, crate).open();
        CompatibleSound.VILLAGER_NO.play(player);
    }

    @Override
    public Prompt acceptInput(Player player, Double value) {

        if (value == null || value < 0.0 || value > 100.0) {
            sendMessage(player, "&cInvalid chance value!");
            handleCancel(player);
            return null;
        }

        reward.setChance(value);
        new CrateChangeRewardChance(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        crate.save();
        return null;
    }


}
