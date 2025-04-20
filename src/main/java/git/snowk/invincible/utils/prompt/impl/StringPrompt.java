package git.snowk.invincible.utils.prompt.impl;

import git.snowk.invincible.utils.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class StringPrompt extends Prompt<String> {

    @Override
    public void handleFailed(Player player, String input) {
        player.sendMessage(ChatColor.RED + "The input '" + ChatColor.WHITE + input + ChatColor.RED + "' is invalid.");
    }

    @Override
    public String handleInput(Player player, String input) {
        return (input == null || input.isEmpty()) ? null : input;
    }
}
