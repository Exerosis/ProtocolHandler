package me.exerosis.packet.injection.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.NoSuchElementException;

class Interceptor extends ChannelDuplexHandler {
    private Channel channel;
    private String handlerName;

    public Interceptor(Channel channel, String handlerName) {
        this.handlerName = handlerName;
        this.channel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    public void inject() {
        if (!isInjected())
            channel.pipeline().addBefore("packet_handler", handlerName, this);
    }

    public void uninject() {
        channel.eventLoop().execute(() -> {
            try {
                channel.pipeline().remove(this);
            } catch (NoSuchElementException ignored) {
            }
        });
    }

    public boolean isInjected() {
        return channel.pipeline().get(handlerName) != null;
    }

    public Channel getChannel() {
        return channel;
    }
}