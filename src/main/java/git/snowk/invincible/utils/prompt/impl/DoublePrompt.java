package git.snowk.invincible.utils.prompt.impl;

import git.snowk.invincible.utils.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class DoublePrompt extends Prompt<Double> {

    @Override
    public void handleFailed(Player player, String input) {
        player.sendMessage(ChatColor.RED + "Invalid double value: " + ChatColor.WHITE + input);
    }

    @Override
    public Double handleInput(Player player, String input) {
        return toDouble(input);
    }

    public double toDouble(String input){
        try{
            return Double.parseDouble(input);
        }catch (Exception e){
            return 0;
        }
    }
}
