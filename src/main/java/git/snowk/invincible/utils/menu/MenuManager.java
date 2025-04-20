package git.snowk.invincible.utils.menu;


import git.snowk.invincible.Invincible;
import git.snowk.invincible.utils.menu.listener.MenuListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class MenuManager {

    private final Map<UUID, Menu> menuMap;

    public MenuManager() {
        this.menuMap = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new MenuListener(), Invincible.getInstance());
    }

    public Optional<Menu> getOpenedMenu(Player player) {
        return Optional.ofNullable(getMenuMap().getOrDefault(player.getUniqueId(), null));
    }

    public void addMenu(Player player, Menu menu) {
        this.getMenuMap().put(player.getUniqueId(), menu);
    }

    public void removeMenu(Player player) {
        this.getMenuMap().remove(player.getUniqueId());
    }

}
