package me.exerosis.packet.injection.injector;

import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.packet.injection.handlers.PlayerHandler;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import net.minecraft.server.v1_8_R1.NetworkManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InterceptorManager {
    private static final AtomicInteger ID = new AtomicInteger(0);
    private final String handlerName;
    private final List<PlayerInterceptor> interceptors = new ArrayList<>();
    private final Map<String, Channel> channelLookup = new HashMap<>();
    private ReflectClass<Object> loginPacket = Reflect.Class("{nms}.PacketLoginInStart");

    public InterceptorManager() {
        handlerName = "P-3085_Pipeline_Accelerator-" + ID.incrementAndGet();
    }

    public void inject(Channel channel) {
        PlayerInterceptor interceptor = new PlayerInterceptor(channel, handlerName) {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (loginPacket.isInstance(msg)) {
                    GameProfile value = loginPacket.setInstance(msg).getField(GameProfile.class).getValue();
                    channelLookup.put(value.getName(), getChannel());
                }
                super.channelRead(ctx, msg);
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                super.write(ctx, msg, promise);
            }
        };
        interceptor.inject();
        interceptors.add(interceptor);
    }

    public void uninjectAll() {
        interceptors.forEach(PlayerInterceptor::uninject);

    }

    public Channel getChannel(Player player) {
        Channel channel = getChannel(player.getName());
        if (channel == null) {
            NetworkManager networkManager = PlayerHandler.getPlayer(player).getNetworkManager();
            channel = Reflect.Class(networkManager).getField(Channel.class).getValue();
            channelLookup.put(player.getName(), channel);
        }
        return channel;
    }

    public Channel getChannel(String player) {
        return channelLookup.get(player);
    }

    public Map<String, Channel> getChannelLookup() {
        return channelLookup;
    }

    public List<PlayerInterceptor> getInterceptors() {
        return interceptors;
    }
}
