package me.exerosis.packet.injection.injector;

import io.netty.channel.Channel;

public class PlayerInterceptor extends Interceptor {
    public PlayerInterceptor(Channel channel, String handlerName) {
        super(channel, handlerName);
    }


}