package me.exerosis.packet.player.injection.packet.player.display.hologram;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packets.entities.Entity;
import org.bukkit.Location;

import java.util.List;

public class Hologram extends Entity {
    public Hologram(Location location, List<String> lines) {
        super("", location);
    }

    @Override
    public String getSpawnCommand(PacketPlayer player) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getEID() {
        // TODO Auto-generated method stub
        return null;
    }
}
