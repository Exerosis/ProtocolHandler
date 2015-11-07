package me.exerosis.packet.display.hologram;

import me.exerosis.packet.entities.Entity;
import me.exerosis.packet.injection.PacketPlayer;
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
