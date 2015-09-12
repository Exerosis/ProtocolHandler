package me.exerosis.packet.player.injection.packet.player.injector;

import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.PacketLoginInStart;
import net.minecraft.server.v1_8_R1.ServerConnection;
import org.bukkit.entity.Player;

final class Interceptor extends ChannelDuplexHandler {
    // Updated by the login event
    public volatile Player player;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Intercept channel
        final Channel channel = ctx.channel();
        handleLoginStart(channel, msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    private void handleLoginStart(Channel channel, Object packet) {
        if (packet instanceof PacketLoginInStart) {
            GameProfile profile = Reflect.Field(packet, GameProfile.class).getValue();

            channelLookup.put(profile.getName(), channel);
        }
    }
}
