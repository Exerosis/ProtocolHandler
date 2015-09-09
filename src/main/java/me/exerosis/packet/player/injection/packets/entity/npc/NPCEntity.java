package me.exerosis.packet.player.injection.packets.entity.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;

import java.util.UUID;

public class NPCEntity {
    private String name;
    private UUID skinUUID;
    private Location location;
    private EntityPlayer npc;
    private int id;
    private int health;

    public NPCEntity(String name, UUID skinUUID, Location location, int health) {
        this.name = name;
        this.skinUUID = skinUUID;
        this.location = location;

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        npc = new EntityPlayer(nmsServer, nmsWorld, makeProfile("NPC", skinUUID, UUID.randomUUID()), new PlayerInteractManager(nmsWorld));

        id = npc.getId();
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public static GameProfile makeProfile(String name, UUID skinId, UUID npcID) {
        GameProfile profile = new GameProfile(npcID, name);
        if (skinId != null) {
            MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
            GameProfile skin = new GameProfile(skinId, null);
            skin = nmsServer.aB().fillProfileProperties(skin, true); //Srg = func_147130_as
            if (skin.getProperties().get("textures") == null || !skin.getProperties().get("textures").isEmpty()) {
                Property textures = skin.getProperties().get("textures").iterator().next();
                profile.getProperties().put("textures", textures);
            }
        }
        return profile;
    }

    public Packet getSpawnPacket() {
        return new PacketPlayOutNamedEntitySpawn(npc);
    }

    public Packet getTabAddPacket() {
        return new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc);
    }

    public Packet getTabRemovePacket() {
        return new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc);
    }

    public Packet getDestroyPacket() {
        return new PacketPlayOutEntityDestroy(id);
    }

    public Packet getTeleportPacket(Location location) {
        this.location = location;
        return new PacketPlayOutEntityTeleport(id, location.getBlockX() * 32, location.getBlockY() * 32, location.getBlockZ() * 32, (byte) 0, (byte) 0, false);
    }

    private Packet getMetaPacket() {
        DataWatcher watcher = new DataWatcher(null);
        watcher.a(6, health);
        watcher.a(2, name);
        watcher.a(0, (byte) 32);
        watcher.a(20, 881);
        return new PacketPlayOutEntityMetadata(id, watcher, true);
    }

    public Packet getNamePacket(String name) {
        this.name = name;
        return getMetaPacket();
    }

    public Packet getHealthPacket(int health) {
        this.health = health;
        return getMetaPacket();
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public UUID getSkinUUID() {
        return skinUUID;
    }

    public int getId() {
        return id;
    }
}
