package git.snowk.invincible.modules.crate.commands;

import git.snowk.invincible.Invincible;
import git.snowk.invincible.modules.crate.Crate;
import git.snowk.invincible.modules.crate.key.CrateKey;
import git.snowk.invincible.modules.crate.menu.ChooseCrateMenu;
import git.snowk.invincible.modules.crate.menu.edit.CrateEditMenu;
import git.snowk.invincible.utils.Colorizer;
import git.snowk.invincible.utils.CompatibleSound;
import git.snowk.invincible.utils.command.BaseCommand;
import git.snowk.invincible.utils.command.Command;
import git.snowk.invincible.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrateCommand extends BaseCommand {

    private final Invincible plugin = Invincible.getInstance();

    @Command(name = "crate", permission = "invincible.crate.admin", aliases = {"cr"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        String label = command.getLabel();

        if (args.length == 0) {
            sendUsage(player, label);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "create":
            case "new":
                handleCreate(player, args);
                break;
            case "delete":
            case "remove":
                handleDelete(player, args);
                break;
            case "edit":
            case "editor":
                handleEdit(player, args);
                break;
            case "setlocation":
            case "set":
                handleSetLocation(player, args);
                break;
            case "list":
            case "ls":
                handleList(player);
                break;
            case "key":
            case "givekey":
                handleGiveKey(player, args);
                break;
            default:
                sendUsage(player, label);
        }
    }

    private void handleCreate(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.CREATE.NO_NAME")));
            return;
        }

        String name = args[1];
        if (plugin.getCrateManager().exists(name)) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.CREATE.ALREADY_EXISTS").replace("%name%", name)));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        Crate crate = new Crate(name);
        plugin.getCrateManager().addCrate(crate);
        player.sendMessage(color(plugin.getLang().getString("CRATE.CREATE.SUCCESS").replace("%name%", name)));
        CompatibleSound.NOTE_PLING.play(player);
    }

    private void handleDelete(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.DELETE.NO_NAME")));
            return;
        }

        String name = args[1];
        if (!plugin.getCrateManager().exists(name)) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.DELETE.NOT_EXISTS").replace("%name%", name)));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        plugin.getCrateManager().removeCrate(plugin.getCrateManager().getByName(name));
        player.sendMessage(color(plugin.getLang().getString("CRATE.DELETE.SUCCESS").replace("%name%", name)));
        CompatibleSound.NOTE_PLING.play(player);
    }

    private void handleEdit(Player player, String[] args) {
        if (args.length < 2) {
            new ChooseCrateMenu(player).open();
            return;
        }

        Crate crate = plugin.getCrateManager().getByName(args[1]);
        if (crate == null) {
            new ChooseCrateMenu(player).open();
            return;
        }

        new CrateEditMenu(player, crate).open();
    }

    private void handleSetLocation(Player player, String[] args) {
        if (args.length < 2) {
            sendUsage(player, "crate");
            return;
        }

        Crate crate = plugin.getCrateManager().getByName(args[1]);
        if (crate == null) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.NOT_EXISTS").replace("%name%", args[1])));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        crate.addLocation(player);
        player.sendMessage(color(plugin.getLang().getString("CRATE.LOCATION_ADDED")));
        CompatibleSound.NOTE_PLING.play(player);
    }

    private void handleList(Player player) {
        player.sendMessage(Colorizer.LINE);
        player.sendMessage(color("&e&lInvincible Crates"));
        player.sendMessage("");
        for (Crate crate : plugin.getCrateManager().getCrates().values()) {
            player.sendMessage(color("&c• &e" + crate.getCrateName()));
        }
        player.sendMessage(Colorizer.LINE);
    }

    private void handleGiveKey(Player player, String[] args) {
        if (args.length < 3) {
            sendUsage(player, "crate");
            return;
        }

        String crateName = args[1];
        Crate crate = plugin.getCrateManager().getByName(crateName);

        if (crate == null) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.NOT_EXISTS").replace("%name%", crateName)));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        int amount = parseInt(args.length >= 4 ? args[3] : "1");
        ItemStack keyItem = crate.getKey().getKeyItem().clone();
        keyItem.setAmount(amount);

        if (args[2].equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().forEach(target -> {
                if (target.equals(player)) return;

                giveItemToPlayer(target, keyItem, crateName, amount);
            });

            player.sendMessage(color(plugin.getLang().getString("CRATE.KEY.GIVE.ALL.SUCCESS")));
            CompatibleSound.NOTE_PLING.play(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            player.sendMessage(color(plugin.getLang().getString("CRATE.KEY.GIVE.NOT_EXISTS").replace("%name%", args[2])));
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        giveItemToPlayer(target, keyItem, crateName, amount);
        player.sendMessage(color(plugin.getLang().getString("CRATE.KEY.GIVE.SUCCESS").replace("%name%", target.getName())));
        CompatibleSound.NOTE_PLING.play(player);
    }

    private void giveItemToPlayer(Player target, ItemStack item, String crateName, int amount) {
        if (target.getInventory().firstEmpty() == -1) {
            target.getWorld().dropItem(target.getLocation(), item);
        } else {
            target.getInventory().addItem(item);
        }

        target.sendMessage(color(plugin.getLang().getString("CRATE.KEY.GIVE.RECEIVED")
                .replace("%amount%", String.valueOf(amount))
                .replace("%crate%", crateName)));
        CompatibleSound.NOTE_PLING.play(target);
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private String color(String input) {
        return Colorizer.colorize(input);
    }

    public List<String> getUsage(String label) {
        return List.of(
                Colorizer.LINE,
                "&e&lInvincible Crates",
                "",
                "&7<> required",
                "&7[] optional",
                "&7| alternative",
                "",
                "&C• &b/" + label + " create | new <name>",
                "&C• &b/" + label + " delete | remove <crate>",
                "&C• &b/" + label + " edit | editor [crate]",
                "&C• &b/" + label + " setlocation | set <crate>",
                "&C• &b/" + label + " list | ls",
                "&C• &b/" + label + " givekey | key <crate> <player | all> [amount]",
                Colorizer.LINE
        );
    }

    public void sendUsage(Player player, String label) {
        getUsage(label).forEach(line -> player.sendMessage(color(line)));
    }
}
