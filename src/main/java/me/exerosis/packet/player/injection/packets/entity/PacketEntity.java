package me.exerosis.packet.player.injection.packets.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public abstract class PacketEntity {
    private HashSet<PacketPlayer> players = new HashSet<PacketPlayer>();
    private boolean global;
    private boolean spawnOnAdd;
    private boolean saveToDatabase;

    public PacketEntity() {
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

    public static void refreshPlayer(final Player player) {
        Bukkit.getScheduler().runTaskLater(PacketAPI.getPlugin(), new Runnable() {
            int visibleDistance = Bukkit.getServer().getViewDistance() * 16;

            @Override
            public void run() {
                for (Entity entity : player.getNearbyEntities(visibleDistance, visibleDistance, visibleDistance)) {
                    if (!entity.getType().equals(EntityType.PLAYER))
                        continue;
                    final Player otherPlayer = (Player) entity;

                    if (!otherPlayer.canSee(player))
                        continue;

                    otherPlayer.hidePlayer(player);
                    Bukkit.getScheduler().runTaskLater(PacketAPI.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            if (otherPlayer == null || !otherPlayer.isValid() || player == null || !player.isValid())
                                return;
                            otherPlayer.showPlayer(player);
                        }
                    }, 1);
                }
            }
        }, 2);
    }

    public abstract void createNPC();

    public abstract Packet getSpawnPacket();

    public abstract Packet getDestroyPacket();

    public abstract void sendTo(PacketPlayer player);

    /**
     * Query the NPC with a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendCommand(String command) {
        return sendCommand(command, null, new Object[]{});
    }

    public boolean sendCommand(String command, Player player) {
        return sendCommand(command, player, new Object[]{});
    }

    public boolean sendCommand(String command, Object... objects) {
        return sendCommand(command, null, objects);
    }

    /**
     * Query the NPC with a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendCommand(String command, Player player, Object... objects) {
        outer:
        for (String cmd : command.split(", ")) {
            Class<?> clazz = this.getClass();
            for (Method method : clazz.getMethods()) {
                if (!method.getName().startsWith("get") || !method.getName().contains("Packet") || !method.getName().contains(cmd))
                    continue;
                try {
                    if (player != null)
                        PlayerHandler.getPlayer(player).sendPacket(method.invoke(this, objects));
                    else
                        for (PacketPlayer packetPlayer : players)
                            packetPlayer.sendPacket(method.invoke(this, objects));
                    continue outer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return false;
    }

    /**
     * Displays this NPC to all but a list of players.
     *
     * @param player
     */
    public void addAllBut(Player... players) {
        Collection<PacketPlayer> onlinePlayers = PlayerHandler.getOnlinePlayers();
        for (Player player : players)
            onlinePlayers.remove(PlayerHandler.getPlayer(player));
        this.players.addAll(onlinePlayers);
    }

    /**
     * Displays this NPC to all but a list of packet players.
     *
     * @param player
     */
    public void addAllBut(PacketPlayer... packetPlayers) {
        Collection<PacketPlayer> onlinePlayers = PlayerHandler.getOnlinePlayers();
        for (PacketPlayer player : packetPlayers)
            onlinePlayers.remove(player);
        players.addAll(onlinePlayers);
    }

    /**
     * Displays this NPC to a list of packet players.
     *
     * @param player
     */
    public void addPlayers(Collection<PacketPlayer> players) {
        for (PacketPlayer packetPlayer : players) {
            addPlayer(packetPlayer);
        }
    }

    /**
     * Displays this NPC to a list of players.
     *
     * @param player
     */
    public void addPlayers(Player... players) {
        for (Player player : players) {
            PacketPlayer packetPlayer = PlayerHandler.getPlayer(player);
            this.players.add(packetPlayer);
        }
    }

    /**
     * Displays this NPC to a list of packet players.
     *
     * @param player
     */
    public void addPlayers(PacketPlayer... players) {
        for (PacketPlayer player : players) {
            this.players.add(player);
        }
    }

    /**
     * Displays this NPC to a player.
     *
     * @param player
     */
    public void addPlayer(Player player) {
        addPlayer(PlayerHandler.getPlayer(player));
    }

    /**
     * Displays this NPC to a packet player.
     *
     * @param player
     */
    public void addPlayer(PacketPlayer player) {
        players.add(player);
    }

    /**
     * Stops displaying this NPC to a player.
     *
     * @param player
     */
    public void removePlayer(Player player) {
        removePlayer(PlayerHandler.getPlayer(player));
    }

    /**
     * Stops displaying this NPC to a packet player.
     *
     * @param player
     */
    public void removePlayer(PacketPlayer player) {
        player.sendPacket(getDestroyPacket());
        players.remove(player);
    }

    /**
     * Checks if this NPC contains a player.
     *
     * @param player
     * @return
     */
    public boolean contains(Player player) {
        return contains(PlayerHandler.getPlayer(player));
    }

    /**
     * Checks if this NPC contains a packet player.
     *
     * @param player
     * @return
     */
    public boolean contains(PacketPlayer player) {
        return players.contains(player);
    }

    /**
     * Returns the number of players in this NPC.
     *
     * @return
     */
    public int numberOfPlayers() {
        return players.size();
    }

    /**
     * Removes all players from this NPC.
     */
    public void clear() {
        for (PacketPlayer player : players) {
            player.sendPacket(getDestroyPacket());
        }
        players.clear();
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public HashSet<PacketPlayer> getPlayers() {
        return players;
    }
}
