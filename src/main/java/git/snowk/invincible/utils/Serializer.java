package git.snowk.invincible.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;


@UtilityClass
public class Serializer {

    public String serializeLocation(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        String world = location.getWorld().getName();

        return world + "," + x + "," + y + "," + z;
    }

    public Location deserializeLocation(String location) {
        String[] split = location.split(",");

        String world = split[0];
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);

        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
