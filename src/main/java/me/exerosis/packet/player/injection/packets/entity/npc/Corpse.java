package me.exerosis.packet.player.injection.packets.entity.npc;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.player.injection.packets.entity.PacketEntity;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Corpse extends PacketEntity {
    private PacketPlayOutBed bedPacket;
    private PacketPlayOutNamedEntitySpawn npcPacket;
    private PacketPlayOutEntityDestroy destroyPacket;
    private PacketPlayOutEntityTeleport teleportPacket;
    private int id = (int) (Math.random() * 10000);
    private Player deadPlayer;

    public Corpse(Player player) {
        this.deadPlayer = player;
        createNPC();
    }

    @Override
    public void createNPC() {
        PacketPlayer packetPlayer = PlayerHandler.getPlayer(deadPlayer);
        Location location = deadPlayer.getLocation();

        int x = (int) (location.getX() * 32.0D);
        int y = (int) ((deadPlayer.getWorld().getHighestBlockYAt(location) + 0.1) * 32.0D);
        int z = (int) (location.getZ() * 32.0D);

        npcPacket = new PacketPlayOutNamedEntitySpawn(packetPlayer.getCraftPlayer());
        bedPacket = new PacketPlayOutBed();
        teleportPacket = new PacketPlayOutEntityTeleport(id, x, y, z, (byte) 0, (byte) 0, false);
        destroyPacket = new PacketPlayOutEntityDestroy(id);


        DataWatcher watcher = emptyPlayerDataWatcher(deadPlayer);
        watcher.watch(10, packetPlayer.getCraftPlayer().getDataWatcher().getByte(10));
        watcher.watch(6, 20.0F);

        new ReflectFeild("a", bedPacket).setValue(id);
        new ReflectFeild("b", bedPacket).setValue(new BlockPosition(location.getBlockX(), 1, location.getBlockZ()));

        new ReflectFeild("a", npcPacket).setValue(id);
        new ReflectFeild("d", npcPacket).setValue(MathHelper.floor((y) * 32.0D));
        new ReflectFeild("h", npcPacket).setValue(0);
        new ReflectFeild("i", npcPacket).setValue(watcher);

        id++;
    }

    public Packet getBedPacket() {
        return bedPacket;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Packet getSpawnPacket() {
        for (PacketPlayer player : PlayerHandler.getOnlinePlayers()) {
            Location location = deadPlayer.getLocation();
            location.setY(1);
            player.getPlayer().sendBlockChange(location, Material.BED_BLOCK.getId(), (byte) 0);
        }
        return npcPacket;
    }


    public Packet getDestroyPacket() {
        return destroyPacket;
    }

    public Packet getTeleportPacket() {
        return teleportPacket;
    }

    public Packet getLookPacket(double pitch, double yaw) {
        return new PacketPlayOutEntityLook(id, (byte) ((int) yaw * 256.0F / 360.0F), (byte) ((int) pitch * 256.0F / 360.0F), false);
    }

    private DataWatcher emptyPlayerDataWatcher(Player player) {
        EntityHuman entityHuman = new EntityHuman(((CraftWorld) player.getWorld()).getHandle(), ((CraftPlayer) player).getProfile()) {
            public void sendMessage(IChatBaseComponent arg0) {
            }

            public boolean a(int arg0, String arg1) {
                return false;
            }

            public BlockPosition getChunkCoordinates() {
                return null;
            }

            public boolean v() {
                return false;
            }
        };

        entityHuman.d(id);
        return entityHuman.getDataWatcher();
    }
}


