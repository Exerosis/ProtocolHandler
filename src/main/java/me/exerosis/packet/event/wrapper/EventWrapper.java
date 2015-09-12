package me.exerosis.packet.event.wrapper;

import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.event.PacketEvent;
import me.exerosis.packet.wrappers.PacketWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventWrapper<T extends PacketWrapper> implements Listener {
    private PacketListener<T> packetListener;
    private Class<?> wrapperClass;

    public EventWrapper(Class<T> wrapperClass, PacketListener<T> packetListener) {
        this(wrapperClass);
        this.packetListener = packetListener;
    }
    public EventWrapper(Class<T> wrapperClass) {
        this.wrapperClass = wrapperClass;
        Bukkit.getPluginManager().registerEvents(this, PacketAPI.getPlugin());
    }

    @EventHandler
    @SuppressWarnings("unchecked")
    public void onPacketReceive(PacketEvent event) {
        if (wrapperClass.isAssignableFrom(event.getWrapper().getClass()))
            packetListener.onPacket(event);
    }
}