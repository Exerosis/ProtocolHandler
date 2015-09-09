package me.exerosis.packet.player.injection.packets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;

import java.util.UUID;

public class SpawnNamedEntity implements PreconPacket {
    private PacketPlayOutNamedEntitySpawn spawnPacket;
    private PacketPlayOutPlayerInfo tabPacket;

    public SpawnNamedEntity(Location location, String name, UUID skinUUID, UUID npcID) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, makeProfile("NPC", skinUUID, npcID), new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        spawnPacket = new PacketPlayOutNamedEntitySpawn(npc);
        tabPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc);
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

    public void send(PacketPlayer player) {
        player.sendPacket(tabPacket);
        player.sendPacket(spawnPacket);
    }

}
