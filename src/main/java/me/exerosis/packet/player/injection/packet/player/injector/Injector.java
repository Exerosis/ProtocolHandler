package me.exerosis.packet.player.injection.packet.player.injector;

import com.google.common.collect.MapMaker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import me.exerosis.reflection.ReflectField;
import net.minecraft.server.v1_8_R1.ServerConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Injector {
    private Map<String, Channel> channelLookup = new MapMaker().weakValues().makeMap();
    private List<Object> networkManagers;

    public Injector() {

    }



    @SuppressWarnings("unchecked")
    private void registerChannelHandler() {
        ReflectField<Object> minecraftServer = Reflect.Class(Bukkit.getServer()).getField(Reflect.Class("{nms}.MinecraftServer"));
        ReflectClass<Object> serverConnection = Reflect.Class(minecraftServer.getReflectType().getField(Reflect.Class("{nms}.ServerConnection"), 0).getValue());

        boolean looking = true;

        // We need to synchronize against this list
        networkManagers = Reflect.Class("{nms}.ServerConnection").getMethodByReturn(List.class).call(serverConnection);
        createServerChannelHandler();

        // Find the correct list, or implicitly throw an exception
        for (int i = 0; looking; i++) {

            List<Object> list = Reflection.getField(serverConnection.getClass(), List.class, i).get(serverConnection);

            for (Object item : list) {
                if (!ChannelFuture.class.isInstance(item))
                    break;

                // Channel future that contains the server connection
                Channel serverChannel = ((ChannelFuture) item).channel();

                serverChannels.add(serverChannel);
                serverChannel.pipeline().addFirst(serverChannelHandler);
                looking = false;
            }
        }
    }



























    /**
     * Add a custom channel handler to the given player's channel pipeline, allowing us to intercept sent and received packets.
     * <p>
     * This will automatically be called when a player has logged in.
     *
     * @param player - the player to inject.
     */
    public void injectPlayer(Player player) {
        injectChannelInternal(getChannel(player)).player = player;
    }

    /**
     * Add a custom channel handler to the given channel.
     *
     * @param player - the channel to inject.
     * @return The intercepted channel, or NULL if it has already been injected.
     */
    public void injectChannel(Channel channel) {
        injectChannelInternal(channel);
    }

    /**
     * Retrieve the Netty channel associated with a player. This is cached.
     *
     * @param player - the player.
     * @return The Netty channel.
     */
    public Channel getChannel(Player player) {
        Channel channel = channelLookup.get(player.getName());

        // Lookup channel again
        if (channel != null)
            return channel;

        channel = Reflect.Field(PlayerHandler.getPlayer(player).getNetworkManager(), Channel.class).getValue();
        channelLookup.put(player.getName(), channel);
        return channel;
    }

    /**
     * Add a custom channel handler to the given channel.
     *
     * @param player - the channel to inject.
     * @return The packet interceptor.
     */
    private Interceptor injectChannelInternal(Channel channel) {
        try {
            Interceptor interceptor = (Interceptor) channel.pipeline().get(handlerName);

            // Inject our packet interceptor
            if (interceptor == null) {
                interceptor = new Interceptor();
                channel.pipeline().addBefore("packet_handler", handlerName, interceptor);
                uninjectedChannels.remove(channel);
            }

            return interceptor;
        } catch (IllegalArgumentException e) {
            // Try again
            return (Interceptor) channel.pipeline().get(handlerName);
        }
    }
}
