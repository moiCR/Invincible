package git.snowk.invincible.modules.crate.type;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.hologram.event.CrateHologramUpdateEvent;
import git.snowk.invincible.modules.crate.menu.CratePreviewMenu;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.ItemUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

    public void handle(PlayerInteractEvent event, Crate crate, Location location) {
        if (handleRemoveCrate(crate, location, event)) return;

        if (handleEdit(crate, location, event)) return;

        switch (crate.getCrateType()) {
            case VIRTUAL:
                handleVirtual(crate, event, location);
                break;
            case NORMAL:
                handleNormal(crate, event, location);
                break;
        }
    }

    public boolean handleRemoveCrate(Crate crate, Location location, PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return false;
        if (!player.isSneaking()) return false;
        if (block == null) return false;

        if (!location.equals(block.getLocation())) return false;
        event.setCancelled(true);

        if (!player.hasPermission("invincible.crate.admin")) {
            return false;
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            return false;
        }

        crate.removeLocation(location);
        CompatibleSound.ANVIL_BREAK.play(player);
        crate.save();
        new CrateHologramUpdateEvent(crate);
        return true;
    }

    public boolean handleEdit(Crate crate, Location location, PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (block == null) return false;
        if (!location.equals(block.getLocation())) return false;
        event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking() && player.hasPermission("invincible.crate.edit") && player.getGameMode() == GameMode.CREATIVE) {
            new CrateEditMenu(player, crate).open();
            return true;
        }

        return false;
    }

    public void handleVirtual(Crate crate, PlayerInteractEvent event, Location location) {
        Player player = event.getPlayer();
        if (!crate.isKey(player.getItemInHand())) {
            if (event.getClickedBlock().getLocation().equals(location)) {
                event.setCancelled(true);
                new CratePreviewMenu(player, crate).open();
            }
            return;
        }

        event.setCancelled(true);
        if (event.getAction().name().startsWith("LEFT")) {
            new CratePreviewMenu(player, crate).open();
            return;
        }

        giveReward(crate, player);
    }

    public void handleNormal(Crate crate, PlayerInteractEvent event, Location location) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block == null || !block.getLocation().equals(location)) return;

        event.setCancelled(true);
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (!crate.isKey(event.getItem())) {
                player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("NO_KEY.TEXT")));
                CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_KEY.SOUND")).play(player);
                return;
            }

            giveReward(crate, player);
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            new CratePreviewMenu(player, crate).open();
        }
    }

    public void giveReward(Crate crate, Player player) {

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("REWARD.NO_EMPTY_INVENTORY")));
            CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_REWARD.SOUND")).play(player);
            return;
        }

        CrateReward reward = crate.getRandomReward();

        if (reward == null) {
            player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("NO_REWARD.TEXT")));
            CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("NO_REWARD.SOUND")).play(player);
            return;
        }

        for (ItemStack contentItem : player.getInventory().getContents()) {
            if (contentItem == null) continue;
            if (crate.isKey(contentItem)) {
                ItemUtils.decrementItem(player, contentItem);
                player.updateInventory();
                break;
            }
        }

        player.getInventory().addItem(reward.getItem().clone());
        CompatibleSound.valueOf(Invincible.getInstance().getLang().getString("REWARD.SUCCESS.SOUND")).play(player);
    }

}
