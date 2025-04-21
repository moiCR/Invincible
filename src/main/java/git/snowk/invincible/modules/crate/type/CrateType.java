package git.snowk.invincible.modules.crate.type;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.menu.CratePreviewMenu;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public enum CrateType {

    NORMAL,
    VIRTUAL,
    BROADCAST,
    ROULETTE;

    public void handleVirtual(Crate crate, PlayerInteractEvent event, Location location){
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        Block block = event.getClickedBlock();

        if (!crate.isKey(item)){
            if (block == null) return;
            if (!location.equals(block.getLocation())) return;
            new CratePreviewMenu(player, crate).open();
            return;
        }

        switch (event.getAction()){
            case LEFT_CLICK_BLOCK: {
                new CratePreviewMenu(player, crate).open();
                break;
            }
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:{
                giveReward(crate, player);
                break;
            }

            default:
                break;
        }
    }


    public void handleNormal(Crate crate, PlayerInteractEvent event, Location location){
        ItemStack item = event.getPlayer().getItemInHand();
        Player player = event.getPlayer();

        Block block = event.getClickedBlock();
        if (block == null) return;

        if (crate.getLocations().isEmpty()) return;

        if (!location.equals(block.getLocation())) return;

        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            new CratePreviewMenu(player, crate).open();
            return;
        }

        if (!crate.isKey(item)){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
                player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("NO_KEY.TEXT")));
                CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_KEY.SOUND")).play(player);
                return;
            }
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            giveReward(crate, player);
        }
    }

    public void giveReward(Crate crate, Player player) {
        CrateReward reward = crate.getRandomReward();
        if (reward == null){
            player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("NO_REWARD.TEXT")));
            CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_REWARD.SOUND")).play(player);
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("REWARD.NO_EMPTY_INVENTORY")));
            CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_REWARD.SOUND")).play(player);
            return;
        }

        player.getInventory().addItem(reward.getItem().clone());
        for (ItemStack contentItem : player.getInventory().getContents()){
            if (contentItem == null) continue;
            if (contentItem.getType() == Material.AIR) continue;

            if (crate.isKey(contentItem)){
                ItemUtils.decrementItem(contentItem);
                player.updateInventory();
                break;
            }
        }
        CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("REWARD.SUCCESS.SOUND")).play(player);
    }

}
