package git.snowk.invincible.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Colorizer {

    public String LINE = colorize("&7&m--------------------------");

    public String colorize(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> colorizeList(List<String> text){
        return text.stream().map(Colorizer::colorize).collect(Collectors.toList());
    }

    public List<String> colorizeList(String... text){
        return colorizeList(Arrays.asList(text));
    }

}
