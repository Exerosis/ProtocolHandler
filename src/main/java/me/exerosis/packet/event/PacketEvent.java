package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketEvent <T extends PacketWrapper> extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final T wrapper;
    private final PacketPlayer player;
    private boolean cancelled;

    public PacketEvent(T wrapper, PacketPlayer player) {
        this.wrapper = wrapper;
        this.player = player;
    }

    public PacketPlayer getPlayer() {
        return player;
    }

    public T getWrapper() {
        return wrapper;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
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