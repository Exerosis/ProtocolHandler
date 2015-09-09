package me.exerosis.packet.player.injection.packet.player.injector;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.exerosis.packet.player.injection.events.in.PacketEventInPlayerDigging;

import java.util.Map;

public class ReadWriteHandler extends ChannelDuplexHandler {

    public static Map<Class<?>, EventCaller> events;

    static {

        events.put(PacketEventInPlayerDigging.class, new EventCaller() {

            @Override
            public void call(Object packet) {
                //Event stuff fireing thigns much lots!
            }

        });

    }

    public ReadWriteHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        for (Map.Entry<Class<?>, EventCaller> entry : events.entrySet()) {

            if (entry.getKey().isInstance(msg)) {
                entry.getValue().call(msg);
                super.channelRead(ctx, msg);
            }

        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }


    public interface EventCaller {

        void call(Object packet);

    }

}
