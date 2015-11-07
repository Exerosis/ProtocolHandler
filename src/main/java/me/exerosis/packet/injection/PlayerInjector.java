package me.exerosis.packet.injection;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.packet.event.PacketEventSystem;
import me.exerosis.packet.event.bukkit.PacketEvent;
import me.exerosis.packet.event.bukkit.PacketReceiveEvent;
import me.exerosis.packet.event.bukkit.PacketSentEvent;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class PlayerInjector extends ChannelDuplexHandler {
    private static Field CHANNEL_FIELD;

    //Player fields
    private PacketPlayer packetPlayer;
    private Player player;

    //Channel
    private Channel channel;

    //Status
    private boolean isInjected;
    private boolean isOpen;

    protected PlayerInjector(PacketPlayer packetPlayer) {
        Preconditions.checkNotNull(packetPlayer, "Player cannot be NULL!");
        this.packetPlayer = packetPlayer;
        player = packetPlayer.getPlayer();
    }

    //Private methods for handling in initialization and events firing.
    private static void initializeFields() {
        if (CHANNEL_FIELD != null)
            return;
        try {
            CHANNEL_FIELD = NetworkManager.class.getDeclaredField("i");
            CHANNEL_FIELD.setAccessible(true);
        } catch (Exception ignored) {
        }
        if (CHANNEL_FIELD == null)
            throw new IllegalStateException("Channel is NULL! \n Shutting down the server, is PlayerInjector up to date? (PlayerInjector version: 1.8.1)");
    }

    protected void unInject() {
        if (!isOpen || !isInjected)
            return;
        channel.eventLoop().execute(() -> {
            try {
                //channel.pipeline().remove(PlayerInjector.this);
                channel.pipeline().remove("custom_packet_handler");
            } catch (NoSuchElementException ignored) {
            }
        });

        isOpen = false;
        isInjected = false;
    }

    protected void inject() {
        initialize();

        synchronized (packetPlayer.getNetworkManager()) {
            if (isInjected || isOpen)
                return;
            if (channel == null)
                throw new IllegalStateException("Channel is NULL!");

            try {
                if (channel.pipeline().get("custom_packet_handler") != null)
                    channel.pipeline().remove("custom_packet_handler");
                channel.pipeline().addBefore("packet_handler", "custom_packet_handler", this);
            } catch (NoSuchElementException ignored) {
            } catch (IllegalArgumentException exception) {
                Bukkit.broadcastMessage(ChatColor.RED + "Duplicate handler name: custom_packet_handler");
            }

            isInjected = true;
            isOpen = true;
        }
    }

    protected void sendPacket(Object packet) {
        if (!isOpen)
            packetPlayer.getPlayerConnection().sendPacket((Packet) packet);
        channel.writeAndFlush(packet);
    }

    protected void receivePacket(Object packet) {
        if (!isOpen)
            throw new IllegalStateException("PlayerInjector is closed!");
        channel.pipeline().context("encoder").fireChannelRead(packet);
    }

    protected boolean isInjected() {
        return isInjected;
    }

    protected boolean isOpen() {
        return isOpen;
    }

    protected PacketPlayer getPlayer() {
        return packetPlayer;
    }

    private void initialize() {
        initializeFields();
        try {
            channel = (Channel) CHANNEL_FIELD.get(packetPlayer.getNetworkManager());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the channel for player: " + player.getName(), e);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        fire(ctx, packet, null);
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        fire(ctx, packet, promise);
        super.write(ctx, packet, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        unInject();
    }

    private void fire(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        me.exerosis.packet.event.PacketEvent listener = PacketEventSystem.fire(packet, packetPlayer);

        PacketEvent event = promise == null ? new PacketReceiveEvent(packet, packetPlayer) : new PacketSentEvent(packet, packetPlayer);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled())
            if (listener == null)
                if (promise != null)
                    super.write(ctx, packet, promise);
                else
                    super.channelRead(ctx, packet);
            else if (!listener.isCancelled())
                if (promise != null)
                    super.write(ctx, listener.getWrapper().getPacket(), promise);
                else
                    super.channelRead(ctx, listener.getWrapper().getPacket());
    }
}