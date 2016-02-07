package me.exerosis.packet.injection;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings({"static-method", "SynchronizeOnNonFinalField"})
public class LightPlayerInjector extends ChannelDuplexHandler {
    private static final String BUKKIT_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PREFIX = BUKKIT_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static Field CHANNEL_FIELD;
    //Player fields
    private Player player;
    private Object connection;
    private Object networkManager;
    //Channel
    private Channel channel;

    //Status
    private boolean isInjected;
    private boolean isOpen;

    public LightPlayerInjector(Player player) {
        Preconditions.checkNotNull(player, "Player cannot be NULL!");
        this.player = player;

        getPlayerFields(player);
        inject();
    }

    //Private methods for handling in initialization and event firing.
    private static void initializeFields() {
        if (CHANNEL_FIELD != null)
            return;
        try {
            Class<?> networkManagerClass = Class.forName(NMS_PREFIX + ".NetworkManager");
            for (Field field : networkManagerClass.getDeclaredFields())
                if (field.getType().equals(Channel.class))
                    CHANNEL_FIELD = field;
            CHANNEL_FIELD.setAccessible(true);
        } catch (Exception ignored) {
        }
        if (CHANNEL_FIELD == null)
            throw new IllegalStateException("Channel is NULL! \n Shutting down the server, is PlayerInjector up to date? (PlayerInjector version: 1.8.1)");
    }

    private void getPlayerFields(Player player) {
        try {
            Class<?> craftPlayerClass = Class.forName(BUKKIT_PREFIX + ".entity.CraftPlayer");
            Class<?> entityPLayerClass = Class.forName(NMS_PREFIX + ".EntityPlayer");
            Object handle = craftPlayerClass.getMethod("getHandle").invoke(player);

            connection = entityPLayerClass.getField("playerConnection").get(handle);
            networkManager = connection.getClass().getField("networkManager").get(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void unInject() {
        if (!isOpen || !isInjected)
            return;
        channel.eventLoop().execute(() -> {
            try {
                channel.pipeline().remove("custom_packet_handler");
            } catch (NoSuchElementException ignored) {
            }
        });

        isOpen = false;
        isInjected = false;
    }

    protected void inject() {
        initialize();

        synchronized (networkManager) {
            if (isInjected || isOpen)
                return;
            if (channel == null)
                throw new IllegalStateException("Channel is NULL!");

            try {
                System.out.println("Created");
                if (channel.pipeline().get("custom_packet_handler_light") != null)
                    channel.pipeline().remove("custom_packet_handler_light");

                channel.pipeline().addBefore("packet_handler", "custom_packet_handler_light", this);
            } catch (NoSuchElementException ignored) {
            } catch (IllegalArgumentException exception) {
                Bukkit.broadcastMessage(ChatColor.RED + "Duplicate handler name: custom_packet_handler_light");
            }

            isInjected = true;
            isOpen = true;
        }
    }

    protected void sendPacket(Object packet) {
        if (!isOpen)
            try {
                connection.getClass().getMethod("sendPacket").invoke(connection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    protected Player getPlayer() {
        return player;
    }

    private void initialize() {
        initializeFields();
        try {
            channel = (Channel) CHANNEL_FIELD.get(networkManager);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the channel for player: " + player.getName(), e);
        }
    }

    public Object incoming(Object packet) {
        return packet;
    }

    public Object outgoing(Object packet) {
        return packet;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        LightPlayerInjector d = new LightPlayerInjector(player) {
            @Override
            public Object outgoing(Object packet) {
                for (Field field : packet.getClass().getFields()) {
                    if (!field.getType().equals(List.class))
                        continue;
                    field.setAccessible(true);
                    try {
                        List list = (List) field.get(packet);
                        for (Field playerDataField : list.get(0).getClass().getFields()) {
                            if (!field.getType().equals(GameProfile.class))
                                continue;
                            GameProfile profile = (GameProfile) playerDataField.get(list.get(0));

                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                return packet;
            }
        };
        super.channelRead(ctx, incoming(packet));
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        super.write(ctx, outgoing(packet), promise);
    }
}