package me.exerosis.packet.player.injection.packets.entity.npc;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.player.injection.packets.entity.PacketEntity;
import me.exerosis.reflection.ReflectFeild;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Disguise extends PacketEntity {
    private Player player;
    private String name;
    private Packet disguisePacket;
    private PacketPlayOutEntityDestroy destroyPacket;
    private PacketPlayOutPlayerInfo addTabPacket;
    private PacketPlayOutPlayerInfo removeTabPacket;
    private EntityType type;

    public Disguise(Player player, String name, EntityType type) {
        this.player = player;
        this.name = name;
        this.type = type;
        createNPC();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void createNPC() {
        PacketPlayer packetPlayer = PlayerHandler.getPlayer(player);
        EntityPlayer craftPlayer = packetPlayer.getCraftPlayer();

        destroyPacket = new PacketPlayOutEntityDestroy(craftPlayer.getId());
        removeTabPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer);

        //new ReflectFeild("name", craftPlayer.getProfile()).setValue(name);

        addTabPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, craftPlayer);

        if (type.equals(EntityType.PLAYER))
            disguisePacket = new PacketPlayOutNamedEntitySpawn(craftPlayer);
        else {
            disguisePacket = new PacketPlayOutSpawnEntityLiving();
            new ReflectFeild("a", disguisePacket).setValue(craftPlayer.getId());
            new ReflectFeild("b", disguisePacket).setValue(type.getTypeId());
            new ReflectFeild("c", disguisePacket).setValue(MathHelper.floor(craftPlayer.locX * 32.0D));
            new ReflectFeild("d", disguisePacket).setValue(MathHelper.floor(craftPlayer.locY * 32.0D));
            new ReflectFeild("e", disguisePacket).setValue(MathHelper.floor(craftPlayer.locZ * 32.0D));
            new ReflectFeild("f", disguisePacket).setValue(0);
            new ReflectFeild("g", disguisePacket).setValue(0);
            new ReflectFeild("h", disguisePacket).setValue(0);
            new ReflectFeild("i", disguisePacket).setValue((byte) (int) (craftPlayer.yaw * 256.0F / 360.0F));
            new ReflectFeild("j", disguisePacket).setValue((byte) (int) (craftPlayer.pitch * 256.0F / 360.0F));
            new ReflectFeild("k", disguisePacket).setValue((byte) 0);


            DataWatcher watcher = new DataWatcher(null);
            watcher.a(0, (byte) 0);
            watcher.a(5, "LOL");
            watcher.a(6, (float) 1);
            new ReflectFeild("l", disguisePacket).setValue(watcher);
        }
        player.setDisplayName(name);
    }

    @Override
    public Packet getSpawnPacket() {
        return disguisePacket;
    }

    @Override
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
