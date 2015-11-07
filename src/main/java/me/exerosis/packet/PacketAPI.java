package me.exerosis.packet;

import me.exerosis.packet.injection.handlers.PlayerHandler;
import me.exerosis.packet.injection.injector.Injector;
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
        new Injector();
        getServer().getPluginManager().registerEvents(this, this);
        plugin = this;
        PlayerHandler.getInstance();
    }
}