package me.exerosis.packet;

import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.player.injection.packet.player.network.ServerNetworkManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketAPI extends JavaPlugin implements Listener {
    public static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        plugin = this;
        new ServerNetworkManager();
        PlayerHandler.getInstance();
    }
}