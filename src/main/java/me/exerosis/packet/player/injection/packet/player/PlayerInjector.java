package me.exerosis.packet.player.injection.packet.player;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.event.PacketEvent;
import me.exerosis.packet.event.PacketEventFireTask;
import me.exerosis.packet.event.wrapper.PacketLookup;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

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

    //Private methods for handling in initialization and event firing.
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
        firePacketEvent(ctx, new PacketEvent(PacketLookup.getWrapper(packet), packetPlayer));
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        firePacketEvent(ctx, new PacketEvent(PacketLookup.getWrapper(packet), packetPlayer), promise);
        super.write(ctx, packet, promise);
    }

    private void firePacketEvent(ChannelHandlerContext ctx, PacketEvent event) {
        firePacketEvent(ctx, event, null);
    }

    private void firePacketEvent(ChannelHandlerContext ctx, PacketEvent event, ChannelPromise promise) {
        Future<PacketEvent> futureTask = Bukkit.getScheduler().callSyncMethod(PacketAPI.getPlugin(), new PacketEventFireTask(event));

        try {
            event = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (event.isCancelled())
            return;
        try {
            if (promise == null) super.channelRead(ctx, event.getWrapper().getPacket());
            else super.write(ctx, event.getWrapper().getPacket(), promise);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}