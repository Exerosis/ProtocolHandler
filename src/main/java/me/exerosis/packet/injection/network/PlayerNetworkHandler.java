package me.exerosis.packet.injection.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.packet.injection.PacketPlayer;

public class PlayerNetworkHandler extends NetworkManagerHandler {

    public PlayerNetworkHandler(PacketPlayer player) {
        super(player.getNetworkManager());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}