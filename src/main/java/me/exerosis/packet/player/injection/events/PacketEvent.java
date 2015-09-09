package me.exerosis.packet.player.injection.events;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class PacketEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Packet packet;
    private boolean cancelled;
    private PacketPlayer player;

    protected PacketEvent(Object packet, PacketPlayer player) {
        this.packet = (Packet) packet;
        this.player = player;
    }

    public PacketEvent(Object packet, PacketPlayer player, boolean async) {
        super(async);
        this.packet = (Packet) packet;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PacketPlayer getPlayer() {
        return player;
    }

    public Packet getPacket() {
        return packet;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
