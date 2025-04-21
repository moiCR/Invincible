package git.snowk.invincible.modules.crate;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.hologram.CrateHologram;
import git.snowk.invincible.modules.crate.key.CrateKey;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.modules.crate.reward.CrateReward;
import git.snowk.invincible.modules.crate.type.CrateType;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.Serializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Crate extends CrateManager {

    private UUID id;
    private String crateName;
    private CrateType crateType;
    private int rows;
    private String title;
    private List<Location> locations;
    private List<CrateReward> rewards;
    private CrateKey key;
    private CrateHologram hologram;

    // Deserialize
    public Crate(Map<String, Object> map){
        this.id = UUID.fromString((String) map.get("id"));
        this.crateName = (String) map.get("crateName");
        this.crateType = CrateType.valueOf((String) map.get("crateType"));
        this.rows = Integer.parseInt((String) map.get("rows"));
        this.title = (String) map.get("title");

        List<String> serializedLocations = (List<String>) map.get("locations");
        this.locations = serializedLocations.stream()
                .map(Serializer::deserializeLocation)
                .collect(Collectors.toList());

        List<Map<String, Object>> serializedRewards = (List<Map<String, Object>>) map.get("rewards");
        this.rewards = serializedRewards.stream()
                .map(CrateReward::new)
                .collect(Collectors.toList());

        this.key = new CrateKey((Map<String, Object>) map.get("key"));

        this.hologram = new CrateHologram((Map<String, Object>) map.get("hologram"), this);
    }

    public Crate(String name){
        this.id = UUID.randomUUID();
        this.crateName = name;
        this.crateType = CrateType.NORMAL;
        this.rows = 3;
        this.title = "&c<crate> Rewards";
        this.locations = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.key = new CrateKey(this);
        this.hologram = new CrateHologram(this);
    }
    
    // Serialize
    public Map<String, Object> serialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("crateName", crateName);
        map.put("crateType", crateType.name());
        map.put("rows", String.valueOf(rows));
        map.put("title", title);
        map.put("locations", locations.stream().map(Serializer::serializeLocation).collect(Collectors.toList()));
        map.put("rewards", rewards.stream().map(CrateReward::serialize).collect(Collectors.toList()));
        map.put("key", key.serialize());
        map.put("hologram", hologram.serialize());
        return map;
    }

    public boolean handleRemoveCrate(PlayerInteractEvent event, Location location){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return false;
        if (!player.isSneaking()) return false;
        if (block == null) return false;

        if (!location.equals(block.getLocation())) return false;
        event.setCancelled(true);

        if (!player.hasPermission("invincible.crate.admin")){
            return false;
        }

        if (player.getGameMode() != GameMode.CREATIVE){
            return false;
        }

        removeLocation(location);
        CompatibleSound.ANVIL_BREAK.play(player);
        save();
        getHologram().updateHologram();
        return true;
    }

    public void removeLocation(Location location){
        locations.remove(location);
    }

    public boolean handleEdit(Crate crate, Location location, PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (block == null) return false;
        if (!location.equals(block.getLocation())) return false;
        event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking() && player.hasPermission("invincible.crate.edit") && player.getGameMode() == GameMode.CREATIVE){
            new CrateEditMenu(player, crate).open();
            return true;
        }

        return false;
    }

    public void addLocation(Player player){
        Block block = player.getTargetBlock((HashSet<Byte>) null, 5);

        if (block.isLiquid() || block.getType() == Material.AIR) return;

        if (locations.stream().anyMatch(location -> block.getLocation().equals(location))){
            player.sendMessage(Invincible.getInstance().getLang().getString("CRATE.LOCATION_EXISTS"));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        locations.add(block.getLocation());
        save();
        getHologram().updateHologram();
        player.sendMessage(Colorizer.colorize(Invincible.getInstance().getLang().getString("CRATE.LOCATION_ADDED")));
        CompatibleSound.NOTE_PLING.play(player);
    }

    public void handle(Crate crate, PlayerInteractEvent event, Location location){

        if (handleRemoveCrate(event, location)){
            return;
        }

        if (handleEdit(crate, location, event)){
            return;
        }

        switch (crateType){
            case NORMAL:
                getCrateType().handleNormal(crate, event, location);
                break;
            case VIRTUAL:
                getCrateType().handleVirtual(crate, event, location);
                break;
        }
    }

    public boolean isKey(ItemStack item){
        ItemStack keyItem = key.getKeyItem();
        return item != null && item.hasItemMeta() && keyItem.hasItemMeta() &&
                Objects.equals(item.getItemMeta().getDisplayName(), keyItem.getItemMeta().getDisplayName()) &&
                Objects.equals(item.getItemMeta().getLore(), keyItem.getItemMeta().getLore());
    }

    public CrateReward getRandomReward() {
        if (rewards.isEmpty()) {
            return null;
        }

        double totalChance = rewards.stream()
                .filter(reward -> !reward.getItem().getType().name().endsWith("GLASS_PANE"))
                .mapToDouble(CrateReward::getChance)
                .sum();

        if (totalChance <= 0) {
            return null;
        }

        double randomValue = Math.random() * totalChance;
        double currentSum = 0;

        for (CrateReward reward : rewards) {
            if (reward.getItem().getType().name().endsWith("GLASS_PANE")) continue;
            currentSum += reward.getChance();
            if (randomValue <= currentSum) {
                return reward;
            }
        }
        return null;
    }

    public void save(){
        Invincible.getInstance().getStorageManager().getStorage().saveCrate(this);
    }

    public void remove(){
        removeCrate(this);
    }

}
