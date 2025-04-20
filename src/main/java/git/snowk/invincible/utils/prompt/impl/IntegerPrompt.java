package git.snowk.invincible.utils.prompt.impl;

import git.snowk.invincible.utils.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

;

public abstract class IntegerPrompt extends Prompt<Integer> {


    @Override
    public void handleFailed(Player player, String input) {
        player.sendMessage(ChatColor.RED + "Invalid integer value: " + ChatColor.WHITE + input);
    }

    @Override
    public Integer handleInput(Player player, String input) {
        return toInt(input);
    }

    public int toInt(String input){
        try{
            return Integer.parseInt(input);
        }catch (Exception e){
            return 0;
        }
    }
}
