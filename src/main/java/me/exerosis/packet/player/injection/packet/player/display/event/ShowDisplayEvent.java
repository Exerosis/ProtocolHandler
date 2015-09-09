package me.exerosis.packet.player.injection.packet.player.display.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.display.Displayable;

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