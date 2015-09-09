package me.exerosis.packet.player.injection.packets.entity.npc;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.player.injection.packets.entity.PacketEntity;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.entity.Player;

public class PlayerName extends PacketEntity {
    private Player player;
    private String name;
    private PacketPlayOutNamedEntitySpawn npcPacket;
    private PacketPlayOutEntityDestroy destroyPacket;
    private PacketPlayOutPlayerInfo addTabPacket;
    private PacketPlayOutPlayerInfo removeTabPacket;

    public PlayerName(Player player, String name) {
        this.player = player;
        this.name = name;
        createNPC();
    }

    @Override
    public void createNPC() {
        PacketPlayer packetPlayer = PlayerHandler.getPlayer(player);
        EntityPlayer craftPlayer = packetPlayer.getCraftPlayer();

        destroyPacket = new PacketPlayOutEntityDestroy(craftPlayer.getId());
        removeTabPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer);

        new ReflectFeild("name", craftPlayer.getProfile()).setValue(name);

        addTabPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, craftPlayer);
        npcPacket = new PacketPlayOutNamedEntitySpawn(craftPlayer);

        player.setDisplayName(name);
    }

    public Packet getSpawnPacket() {
        return npcPacket;
    }

    public Packet getDestroyPacket() {
        return destroyPacket;
    }

    public Packet getAddTabPacket() {
        return addTabPacket;
    }

    public Packet getRemoveTabPacket() {
        return removeTabPacket;
    }


}
