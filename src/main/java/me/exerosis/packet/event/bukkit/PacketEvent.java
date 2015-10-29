package me.exerosis.packet.event.bukkit;

import me.exerosis.event.Cancellable;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Object packet;
    private final PacketPlayer player;
    private boolean cancelled;

    public PacketEvent( Object packet, PacketPlayer player) {
        this.packet = packet;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public PacketPlayer getPlayer() {
        return player;
    }

    public Object getPacket() {
        return packet;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}