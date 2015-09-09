package me.exerosis.packet.player.injection.packets.entity.npc;

import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.events.in.PacketEventInUseEntity;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packets.Animation;
import net.minecraft.server.v1_8_R1.EnumEntityUseAction;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.UUID;

public class NPC implements Listener {
    private NPCEntity npcEntity;

    private HashSet<PacketPlayer> players = new HashSet<>();

    private Runnable interactAction;

    public NPC(String name, UUID skinUUID, Location location, int health, Runnable interactAction) {
        this.interactAction = interactAction;
        npcEntity = new NPCEntity(name, skinUUID, location, health);
        PacketAPI.getPlugin().getServer().getPluginManager().registerEvents(this, PacketAPI.getPlugin());
    }


    @EventHandler
    public void onNPCInteract(PacketEventInUseEntity event) {
        if (event.getTargetId() != npcEntity.getId())
            return;
        if (event.getAction().equals(EnumEntityUseAction.INTERACT))
            interactAction.run();
        if (event.getAction().equals(EnumEntityUseAction.ATTACK))
            new Animation(npcEntity.getId(), 1).send(event.getPlayer());
    }


    public void setHealth(int health) {
        Packet packet = npcEntity.getHealthPacket(health);
        for (PacketPlayer player : players) {
            player.sendPacket(packet);
        }
    }

    public void setName(String name) {
        Packet packet = npcEntity.getNamePacket(name);
        for (PacketPlayer player : players) {
            player.sendPacket(packet);
        }
    }

    public void setLocation(Location location) {
        Packet packet = npcEntity.getTeleportPacket(location);
        for (PacketPlayer player : players) {
            player.sendPacket(packet);
        }
    }

    public void addPlayer(PacketPlayer player) {
        player.sendPacket(npcEntity.getSpawnPacket());
        players.add(player);
    }

    public void removePlayer(PacketPlayer player) {
        player.sendPacket(npcEntity.getDestroyPacket());
        players.remove(player);
    }

    public void clear() {
        for (PacketPlayer player : players) {
            player.sendPacket(npcEntity.getDestroyPacket());
        }
        players.clear();
    }

}
