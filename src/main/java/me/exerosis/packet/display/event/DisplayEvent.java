package me.exerosis.packet.display.event;

import me.exerosis.packet.display.Displayable;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DisplayEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean _cancelled;
    private Displayable _displayable;

    public DisplayEvent(Displayable displayable) {
        _displayable = displayable;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Displayable getDisplayable() {
        return _displayable;
    }

    @Override
    public boolean isCancelled() {
        return _cancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        _cancelled = arg0;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}