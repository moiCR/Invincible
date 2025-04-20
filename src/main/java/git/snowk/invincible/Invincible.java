package git.snowk.invincible;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import git.snowk.invincible.modules.crate.CrateManager;
import git.snowk.invincible.modules.hook.HookManager;
import git.snowk.invincible.modules.storage.StorageManager;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.FileConfig;
import git.snowk.invincible.utils.command.CommandManager;
import git.snowk.invincible.utils.menu.MenuManager;
import git.snowk.invincible.utils.prompt.PromptManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Invincible extends JavaPlugin {

    private Gson gson;
    private MenuManager menuManager;
    private StorageManager storageManager;
    private CrateManager crateManager;
    private CommandManager commandManager;
    private HookManager hookManager;
    private PromptManager promptManager;
    private FileConfig config, lang;

    @Override
    public void onEnable() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        this.config = new FileConfig("config.yml");
        this.lang = new FileConfig("lang.yml");

        this.commandManager = new CommandManager(this);
        this.crateManager = new CrateManager();
        this.menuManager = new MenuManager();
        this.storageManager = new StorageManager();
        this.promptManager = new PromptManager();
        this.hookManager = new HookManager();

        this.storageManager.onLoad();
        this.crateManager.onEnable();

        log("");
        log("&e&lInvincible");
        log("");
        log("&7» &bVersion: &f" + this.getDescription().getVersion());
        log("&7» &bAuthor: &f" + this.getDescription().getAuthors().get(0));
        log("&7» &bHolograms: &f" + this.hookManager.getHologramType());
        log("&7» &bSupport: &fhttps://github.com/moiCR/Invincible/issues");
        log("");
    }

    @Override
    public void onDisable() {
        this.storageManager.onDisable();
        this.crateManager.onDisable();
    }


    public void log(String text){
        Bukkit.getConsoleSender().sendMessage(Colorizer.colorize(text));
    }

    public static Invincible getInstance(){
        return JavaPlugin.getPlugin(Invincible.class);
    }

}
