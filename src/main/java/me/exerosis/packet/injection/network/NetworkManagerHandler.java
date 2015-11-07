package me.exerosis.packet.injection.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.reflection.Reflect;

import java.util.concurrent.atomic.AtomicInteger;

public class NetworkManagerHandler extends ChannelDuplexHandler {
    public static final AtomicInteger ID = new AtomicInteger(0);
    private final Channel channel;
    private final String name;

    public NetworkManagerHandler(Object networkManager) {
        channel = Reflect.Field(networkManager, Channel.class).getValue();
        name = getNextHandlerName();
        synchronized (channel) {
            channel.pipeline().addFirst(name, this);
        }
    }

    public static String getNextHandlerName() {
        return "P-3058 Pipeline Accelerator Handler #" + ID.getAndIncrement();
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
        synchronized (channel) {
            ctx.pipeline().remove(this);
        }
        super.disconnect(ctx, future);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    public String getName() {
        return name;
    }
}