package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.key.CrateKeyEditorMenu;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.StringPrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class KeyEditDisplayNamePrompt extends StringPrompt {

    private Crate crate;

    @Override
    public void handleBegin(Player player) {
        player.sendMessage(Colorizer.colorize("&eYou're editing the display name of the key. Or type cancel to &6cancel &ethis process"));
        player.closeInventory();
        CompatibleSound.NOTE_PIANO.play(player);
    }

    @Override
    public void handleCancel(Player player) {
        new CrateKeyEditorMenu(player, crate).open();
        CompatibleSound.VILLAGER_NO.play(player);
    }

    @Override
    public Prompt acceptInput(Player player, String value) {
        crate.getKey().setDisplayName(value);
        new CrateKeyEditorMenu(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        crate.save();
        return null;
    }
}
