package me.exerosis.packet.player.injection.packets.entities;

import me.exerosis.io.util.StreamUtil;
import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.utils.PlayerUtil;
import me.exerosis.packet.wrappers.entity.in.PacketEventInUseEntity;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class Entity implements Listener, Serializable {
    private static final long serialVersionUID = 8606871941638844968L;

    private String name;
    private Location location;
    private byte debugLevel;

    public Entity(String name, Location location) {
        this.name = name;
        this.location = location;
        Bukkit.getPluginManager().registerEvents(this, PacketAPI.getPlugin());
    }

    /**
     * Makes sure a player can see all surrounding players
     *
     * @param player
     */
    public static void refreshPlayer(final Player player) {
        final int visibleDistance = Bukkit.getServer().getViewDistance() * 16;
        Bukkit.getScheduler().runTaskLater(PacketAPI.getPlugin(), () -> {
            final Set<Player> players = new HashSet<>();
            for (Player otherPlayer : PlayerUtil.getOnlinePlayers()) {
                if (otherPlayer.getLocation().distance(player.getLocation()) > visibleDistance)
                    continue;
                if (!otherPlayer.canSee(player))
                    continue;
                players.add(otherPlayer);
                otherPlayer.hidePlayer(player);
            }

            Bukkit.getScheduler().runTaskLater(PacketAPI.getPlugin(), () -> {
                for (Player otherPlayer : players) {
                    if (otherPlayer == null || !otherPlayer.isValid() || player == null || !player.isValid())
                        return;
                    otherPlayer.showPlayer(player);
                }
            }, 1);
        }, 2);
    }

    /**
     * Gets the command used to create and show the NPC to a player, allows for player modifications as well.
     */
    public abstract String getSpawnCommand(PacketPlayer player);

    /**
     * Gets the NPCs entity ID.
     */
    public abstract int[] getEID();

    /**
     * Called when this NPC is clicked on.
     */
    @EventHandler
    public void onClick(PacketEventInUseEntity event) {
    }

    public String getSpawnCommand(Player player) {
        return getSpawnCommand(PlayerHandler.getPlayer(player));
    }

    /**
     * Returns the packet that destroys the entity.
     *
     * @return
     */
    public Packet getRemovePacket() {
        return new PacketPlayOutEntityDestroy(getEID());
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendCommand(String command, PacketPlayer player, Object... objects) {
        outer:
        for (String cmd : command.split(", ")) {
            Class<?> clazz = this.getClass();
            for (Method method : clazz.getMethods()) {
                if (!method.getName().startsWith("get") || !method.getName().contains("Packet") || !method.getName().contains(cmd))
                    continue;
                try {
                    Packet packet = (Packet) method.invoke(this, objects);
                    if (player != null)
                        player.sendPacket(packet);
                    for (PacketPlayer onlinePlayer : PlayerHandler.getOnlinePlayers())
                        onlinePlayer.sendPacket(packet);
                    continue outer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Saves the NPC data to a .dat.
     */
    public void save() {
        File file = new File("NPC Data");

        if (file.mkdirs())
            print("Created 'NPC Data' folder.");

        file = new File("NPC Data/" + name + ".dat");

        try {
            if (file.createNewFile())
                print("[NPCs] Created '" + name + "' npc data file.");
        } catch (IOException e) {
            print("[NPCs] Failed to create a .dat file!", true);
        }

        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream = new BufferedOutputStream(stream);
            stream = new ObjectOutputStream(stream);

            ((ObjectOutputStream) stream).writeObject(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(stream);
        }
    }


    //Events
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        sendModCommand(getSpawnCommand(event.getPlayer()), event.getPlayer());
    }

    @EventHandler
    private void onClickEvent(PacketEventInUseEntity event) {
        if (event.getTargetId() == getEID()[0])
            onClick(event);
    }


    //Getters.

    /**
     * Returns the NPC's location.
     *
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the NPC's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command, Object... objects) {
        return sendCommand(command, null, objects);
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command) {
        return sendCommand(command, null);
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command, Player player, Object... objects) {
        return sendCommand(command, PlayerHandler.getPlayer(player), objects);
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command, PacketPlayer player, Object... objects) {
        return sendCommand(command, player, objects);
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command, Player player) {
        return sendCommand(command, PlayerHandler.getPlayer(player));
    }

    /**
     * Send a modification to the NPC that only one player can see and will not be saved by the server.
     * Input a unique string part of a "getPacket" method.
     *
     * @param command
     * @param objects
     */
    public boolean sendModCommand(String command, PacketPlayer player) {
        return sendCommand(command, player);
    }

    public byte getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(byte debugLevel) {
        this.debugLevel = debugLevel > 3 ? 3 : debugLevel;
    }

    protected void print(CharSequence sequence) {
        print(sequence, false);
    }

    protected void print(CharSequence sequence, boolean error) {
        if (debugLevel != 0)
            ((debugLevel == 2 ? error : debugLevel == 3) ? System.err : System.out).println("[NPCs]" + sequence);
    }
}