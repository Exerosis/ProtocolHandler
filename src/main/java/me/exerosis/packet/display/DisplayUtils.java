package me.exerosis.packet.display;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import org.bukkit.ChatColor;

public class DisplayUtils {
    private DisplayUtils() {
    }

    public static IChatBaseComponent serializeText(String text, ChatColor color) {
        if (text != null && !text.equals(""))
            text = ChatColor.translateAlternateColorCodes('&', text);
        return ChatSerializer.a("{text:\"" + text + "\",color:" + color.name().toLowerCase() + "}");
    }
}
