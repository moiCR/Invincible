package git.snowk.invincible.modules.crate.menu.prompt;

import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.hologram.event.CrateHologramUpdateEvent;
import git.snowk.invincible.modules.crate.menu.edit.hologram.CrateHologramEditMenu;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.prompt.Prompt;
import git.snowk.invincible.utils.prompt.impl.StringPrompt;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class HologramEditLinePrompt extends StringPrompt {

    private Crate crate;
    private int index;

    @Override
    public void handleBegin(Player player) {
        player.sendMessage(Colorizer.colorize("&eYou're editing the line " + index + " from the lore. Or type cancel to &6cancel &ethis process"));
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
        crate.getHologram().getLines().set(index, value);
        new CrateHologramEditMenu(player, crate).open();
        CompatibleSound.LEVEL_UP.play(player);
        new CrateHologramUpdateEvent(crate);
        crate.save();
        return null;
    }
}
