package git.snowk.invincible.utils.prompt;

import com.google.common.collect.Maps;
import git.snowk.invincible.Invincible;
import git.snowk.invincible.utils.Colorizer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
@Getter
public class PromptManager {


    private final Map<UUID, PromptData> promptMap;

    public PromptManager() {
        this.promptMap = Maps.newHashMap();
        Bukkit.getServer().getPluginManager().registerEvents(new PromptListener(this), Invincible.getInstance());
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(Invincible.getInstance(), new PromptRunnable(this), 20L, 20L);
    }

    public void startPrompt(Player player, Prompt<?> prompt, long timeout) {
        if (this.promptMap.containsKey(player.getUniqueId())) {
            player.sendMessage(Colorizer.colorize("&cYou are already in a prompt."));
            return;
        }

        this.promptMap.put(player.getUniqueId(), new PromptData(prompt, System.currentTimeMillis(), timeout));
        prompt.handleBegin(player);
    }

    public void endPrompt(Player player) {
        if (!this.promptMap.containsKey(player.getUniqueId())) {
            player.sendMessage(Colorizer.colorize("&cYou are not in a prompt."));
            return;
        }

        PromptData promptData = this.promptMap.remove(player.getUniqueId());

        promptData.getPrompt().handleCancel(player);
    }

    public PromptData getPromptByPlayer(Player player) {
        if (this.promptMap.containsKey(player.getUniqueId())) {
            return this.promptMap.get(player.getUniqueId());
        }

        return null;
    }

    public boolean onPrompt(Player player) {
        return getPromptByPlayer(player) != null;
    }

    protected void handleChat(AsyncPlayerChatEvent event, Prompt prompt) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("leave") || message.equalsIgnoreCase("quit")) {
            endPrompt(player);
            return;
        }

        Object value = prompt.handleInput(player, message);
        if (value == null) {
            prompt.handleFailed(player, message);
            return;
        }

        Prompt<?> nextPrompt = prompt.acceptInput(player, value);
        if (nextPrompt == null) {
            this.promptMap.remove(player.getUniqueId());
        } else {
            this.promptMap.remove(player.getUniqueId());
            this.promptMap.put(player.getUniqueId(), new PromptData(nextPrompt, System.currentTimeMillis(), TimeUnit.SECONDS.toMillis(20L)));
            nextPrompt.handleBegin(player);
        }
    }
}
