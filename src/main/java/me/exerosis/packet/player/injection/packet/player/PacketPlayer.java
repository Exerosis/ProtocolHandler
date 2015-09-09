package me.exerosis.packet.player.injection.packet.player;

import me.exerosis.packet.player.injection.packet.player.display.DisplayHolder;
import me.exerosis.packet.player.injection.packet.player.display.Displayable;
import me.exerosis.packet.player.injection.packet.player.display.displayables.ActionBar;
import me.exerosis.packet.player.injection.packet.player.display.displayables.Title;
import me.exerosis.packet.player.injection.packets.PreconPacket;
import me.exerosis.packet.utils.location.AdvancedLocation;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PacketPlayer {
    private Player player;
    private PlayerInjector playerInjector;
    private Map<Class<? extends Displayable>, DisplayHolder<Displayable>> displaySlots = new HashMap<>();

    /**
     * Creates a PacketPlayer and its respective PlayerInjector.
     *
     * @param player
     */
    public PacketPlayer(Player player) {
        this.player = player;
        playerInjector = new PlayerInjector(this);
        playerInjector.inject();
        registerDisplayableType(ActionBar.class);
        registerDisplayableType(Title.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends Displayable> void registerDisplayableType(Class<T> displayableClass) {
        displaySlots.put(displayableClass, (DisplayHolder<Displayable>) new DisplayHolder<T>(this));
    }

    /**
     * Shows a player a new displayable.
     *
     * @param displayable, shown
     */
    public void setDisplayed(Displayable displayable, boolean shown) {
        if (displayable == null)
            return;
        if (shown)
            displaySlots.get(displayable.getClass()).add(displayable);
        else
            displaySlots.get(displayable.getClass()).remove(displayable);
    }

    /**
     * s
     * Sends a packet from the server to the player.
     *
     * @param packet
     */
    public void sendPacket(Object packet) {
        if (packet instanceof PreconPacket)
            ((PreconPacket) packet).send(this);
        playerInjector.sendPacket(packet);
    }

    /**
     * Sends a packet from the player to the server.
     *
     * @param packet
     */
    public void receivePacket(Object packet) {
        if (packet instanceof PreconPacket)
            ((PreconPacket) packet).send(this);
        playerInjector.receivePacket(packet);
    }

    /**
     * Stop listening to packets from this player.
     */
    public void unInject() {
        playerInjector.unInject();
    }

    // Getters for the players values.

    /**
     * Gets the Bukkit Player.
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    /**
     * Gets the NMS player.
     *
     * @return craftPlayer
     */
    public EntityPlayer getCraftPlayer() {
        return ((CraftPlayer) player).getHandle();
    }

    /**
     * Gets the NMS NetworkManager.
     *
     * @return networkManager
     */
    public NetworkManager getNetworkManager() {
        return getCraftPlayer().playerConnection.networkManager;
    }

    /**
     * Gets the NMS PlayerConnection.
     *
     * @return playerConnection
     */
    public PlayerConnection getPlayerConnection() {
        return getCraftPlayer().playerConnection;
    }

    /**
     * Gets the a list containing all the display slots for this player.
     *
     * @return display handler
     */
    public Collection<DisplayHolder<Displayable>> getDisplayHolders() {
        return displaySlots.values();
    }

    /**
     * Get the {@linkplain DisplayHolder} for the specified {@linkplain Displayable} class
     *
     * @param displayableClass {@linkplain Displayable} - The Displayable the DisplayHolder holds.
     * @return {@linkplain DisplayHolder} - The DisplayHolder for the specified Displayable
     */
    @SuppressWarnings("unchecked")
    public <T extends Displayable> DisplayHolder<T> getDisplayHolder(Class<T> displayableClass) {
        return (DisplayHolder<T>) displaySlots.get(displayableClass);
    }

    /**
     * Returns true if the player injector is injected and open.
     *
     * @return
     */
    public boolean isInjected() {
        return playerInjector.isInjected() && playerInjector.isOpen();
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public AdvancedLocation getAdvancedLocation() {
        return new AdvancedLocation(getLocation());
    }
}