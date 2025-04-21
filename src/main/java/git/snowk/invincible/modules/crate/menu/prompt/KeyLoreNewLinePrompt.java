package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.key.CrateKeyEditLoreMenu;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.StringPrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class KeyLoreNewLinePrompt extends StringPrompt {

    private final Crate crate;

    @Override
    public void handleBegin(Player player) {
        sendMessage(player, "&eType the new line for the lore. Enter &6cancel &eto cancel the process.");
        player.closeInventory();
        CompatibleSound.NOTE_PIANO.play(player);
    }

    @Override
    public void handleCancel(Player player) {
        new CrateKeyEditLoreMenu(player, crate).open();
        CompatibleSound.VILLAGER_NO.play(player);
    }

    @Override
    public Prompt acceptInput(Player player, String value) {
        crate.getKey().getLore().add(value);
        new CrateKeyEditLoreMenu(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        crate.save();
        return null;
    }
}
