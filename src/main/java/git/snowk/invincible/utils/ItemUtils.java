package git.snowk.invincible.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@UtilityClass
public class ItemUtils {

    public void decrementItem(Player player){
        if (player.getItemInHand().getAmount() > 1){
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }else{
            player.getInventory().setItemInHand(null);
        }

    }

    public void decrementItem(ItemStack item){
        if (item.getAmount() > 1){
            item.setAmount(item.getAmount() - 1);
        }else{
            item.setType(null);
        }
    }

    public static String itemToBase64(ItemStack item) throws IllegalStateException {
        if(item == null) {
            return "";
        }

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(item);

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save item.", e);
        }
    }

    public ItemStack itemFromBase64(String data) {
        if(data == null || data.isEmpty()) {
            return ItemMaker.of(Material.BARRIER).setAmount(1).setDisplayName("&cError to load Item").build();
        }

        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            dataInput.close();
            return (ItemStack) dataInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
