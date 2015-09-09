package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PlayParticle implements PreconPacket {
    private int id;
    private Location location;
    private Vector velocity;
    private float speed;
    private int particlesCount;
    private PacketPlayOutWorldParticles packet;

    public PlayParticle(int id, Location location, Vector velocity, float speed, int particlesCount) {
        this.id = id;
        this.location = location;
        this.velocity = velocity;
        this.speed = speed;
        this.particlesCount = particlesCount;

        packet = new PacketPlayOutWorldParticles(
                EnumParticle.a(id),
                true,
                (float) location.getX(),
                (float) location.getY(),
                (float) location.getZ(),
                (float) velocity.getX(),
                (float) velocity.getY(),
                (float) velocity.getZ(),
                speed,
                particlesCount
        );
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public int getParticlesCount() {
        return particlesCount;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public float getSpeed() {
        return speed;
    }
}