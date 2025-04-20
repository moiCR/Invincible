package git.snowk.invincible.utils.menu;



import git.snowk.invincible.Invincible;
import git.snowk.invincible.utils.*;
import git.snowk.invincible.utils.menu.button.Button;
import git.snowk.invincible.utils.menu.decoration.DecorationType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public abstract class Menu {

    private int rows;
    private String title;
    private Inventory inventory;
    private boolean decoration;
    private ItemStack decorationItem;
    private Player player;
    private boolean update;
    private DecorationType decorationType;
    private Map<Integer, Button> decorationButtons;
    private boolean allowPlayerMoveInventory;
    private boolean allowAddItems;
    private BukkitTask updateTask;
    private boolean soundOnClick;


    public Menu(Player player, String title, int rows, boolean update) {
        this.player = player;
        this.rows = rows;
        this.title = title;
        this.decoration = false;
        this.update = update;
        this.decorationButtons = new HashMap<>();
        this.allowPlayerMoveInventory = false;
        this.allowAddItems = false;
        this.updateTask = update ? Bukkit.getScheduler().runTaskTimerAsynchronously(Invincible.getInstance(), this::update, 0L, 10L) : null;
        this.soundOnClick = false;
    }


    public abstract Map<Integer, Button> getButtons();



    public void redirect(Menu menu){
        new BukkitRunnable() {
            @Override
            public void run() {
                menu.open();
            }
        }.runTaskLater(Invincible.getInstance(), (long)0.5*20L);
    }

    public void open(){

        if (!player.isOnline()){
            clean();
            return;
        }

        if (rows > 6 || rows < 1){
            rows = 6;
        }

        this.inventory = Bukkit.createInventory(null, getRows()*9, ChatColor.translateAlternateColorCodes('&', getTitle()));

        update();


        player.openInventory(this.inventory);
        Invincible.getInstance().getMenuManager().addMenu(player, this);
    }

    public void update(){
        Bukkit.getScheduler().runTaskAsynchronously(Invincible.getInstance(), () -> {
            this.getInventory().clear();

            if (isDecoration()){
                decorationType.decorate(this);
            }

            getButtons().forEach((key, button) -> {
                this.inventory.setItem(key, button.icon());
            });

            player.updateInventory();
        });
    }

    public void clean(){
        getDecorationButtons().clear();
        getButtons().clear();
        Invincible.getInstance().getMenuManager().removeMenu(player);

        if (updateTask != null){
            updateTask.cancel();
        }
    }



    public void onClose(InventoryCloseEvent event){
    }


    public int getSize(){
        return getRows()*9;
    }

    public void sendMessage(Player player, String text){
        player.sendMessage(Colorizer.colorize(text));
    }

    public void sendMessage(Player player, List<String> texts){
        for (String text : texts){
            sendMessage(player, text);
        }
    }

    public void sendMessage(Player player, String... texts){
        for (String text : texts){
            sendMessage(player, text);
        }
    }

}
