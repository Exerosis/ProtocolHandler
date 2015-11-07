package me.exerosis.packet.injection.injector.excluded;

import com.google.common.collect.MapMaker;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.nio.channels.Channel;
import java.util.Map;

public class ReadWriteHandler extends ChannelDuplexHandler {



    static {

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
