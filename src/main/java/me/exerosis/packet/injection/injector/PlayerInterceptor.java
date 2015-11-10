package me.exerosis.packet.injection.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class PlayerInterceptor extends Interceptor {
    public PlayerInterceptor(Channel channel, String handlerName) {
        super(channel, handlerName);
    }

    public void sendPacket(Object packet) {
        getChannel().writeAndFlush(packet);
    }

    public void receivePacket(Object packet) {
        getChannel().pipeline().context("encoder").fireChannelRead(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void inject() {
        if (!isInjected())
            getChannel().pipeline().addBefore("packet_handler", getHandlerName(), this);
    }

/*    private void fire(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        PacketEvent listener = PacketEventSystem.fire(packet, packetPlayer);

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
    }*/
}