package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

public class ListenerPseudoInstance<T extends PacketWrapper> {
    private T wrapper;
    private boolean canceled;
    private PacketPlayer player;

    public PacketPlayer getPlayer() {
        return player;
    }

    public void setPlayer(PacketPlayer player) {
        this.player = player;
    }

    public T getWrapper() {
        return wrapper;
    }

    public void setWrapper(T wrapper) {
        this.wrapper = wrapper;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }
}