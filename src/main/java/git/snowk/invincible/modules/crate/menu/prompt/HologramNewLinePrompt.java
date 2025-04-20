package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.edit.CrateHologramEditMenu;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.StringPrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class HologramNewLinePrompt extends StringPrompt {

    private final Crate crate;

    @Override
    public void handleBegin(Player player) {
        player.sendMessage(Colorizer.colorize("&eType the new line for the hologram. Enter &6cancel &eto cancel the process."));
        player.closeInventory();
        CompatibleSound.NOTE_PIANO.play(player);
    }

    @Override
    public void handleCancel(Player player) {
        new CrateHologramEditMenu(player, crate).open();
        CompatibleSound.VILLAGER_NO.play(player);
    }

    @Override
    public Prompt acceptInput(Player player, String value) {
        crate.getHologram().getLines().add(value);
        new CrateHologramEditMenu(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        return null;
    }
}
