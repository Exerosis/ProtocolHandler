package me.exerosis.packet.display.event;

import me.exerosis.packet.display.Displayable;
import me.exerosis.packet.injection.PacketPlayer;

public class ShowDisplayEvent extends DisplayEvent {
    private PacketPlayer _player;

    public ShowDisplayEvent(PacketPlayer player, Displayable displayable) {
        super(displayable);
        _player = player;
    }

    public PacketPlayer getPlayer() {
        return _player;
    }
}