package me.exerosis.packet.player.injection.packet.player.display;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;

import javax.annotation.Nonnull;

public abstract class Displayable implements Comparable<Displayable> {
    private int _priority;

    public Displayable(int priority) {
        _priority = priority;
    }

    public void show(PacketPlayer player) {
    }

    public void hide(PacketPlayer player) {
    }

    @Override
    public int compareTo(@Nonnull Displayable o) {
        return Integer.compare(o.getPriority(), _priority);
    }

    public int getPriority() {
        return _priority;
    }
}