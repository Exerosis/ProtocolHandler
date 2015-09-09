package me.exerosis.packet.player.injection.events;

import org.bukkit.Bukkit;

import java.util.concurrent.Callable;

public class PacketEventFireTask implements Callable<PacketEvent> {
    private PacketEvent event;

    public PacketEventFireTask(PacketEvent event) {
        this.event = event;
    }

    public PacketEvent call() throws Exception {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }
}
