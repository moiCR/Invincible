package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.key.CrateKeyEditLoreMenu;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.StringPrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class KeyLoreEditLinePrompt extends StringPrompt {

    private final int index;
    private final Crate crate;


    @Override
    public void handleBegin(Player player) {
        sendMessage(player, "&eYou're editing the lore of the key. Or type cancel to &6cancel &ethis process");
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
        crate.getKey().getLore().set(index, value);
        new CrateKeyEditLoreMenu(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        crate.save();
        return null;
    }
}
