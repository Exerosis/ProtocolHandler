package me.exerosis.packet.injection.injector;

import io.netty.channel.*;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("SynchronizeOnNonFinalField")
public class Injector {
    private static final AtomicInteger ID = new AtomicInteger(0);
    protected volatile boolean closed;
    private List<Object> networkManagers = new ArrayList<>();
    private List<Channel> serverChannels = new ArrayList<>();
    private List<Interceptor> interceptors = new ArrayList<>();
    private ChannelInboundHandlerAdapter serverChannelHandler;
    private ChannelInitializer<Channel> beginInitProtocol;
    private ChannelInitializer<Channel> endInitProtocol;
    private String handlerName = "P-3085_Pipeline_Accelerator-" + ID.incrementAndGet();

    public Injector() {
        registerChannelHandler();
    }

    private void createServerChannelHandler() {
        // Handle connected channels
        endInitProtocol = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                try {
                    synchronized (networkManagers) {
                        if (!closed)
                            injectChannelInternal(channel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Cannot inject incoming channel " + channel);
                }
            }
        };

        // This is executed before Minecraft's channel handler
        beginInitProtocol = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(endInitProtocol);
            }
        };

        serverChannelHandler = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Channel channel = (Channel) msg;
                // Prepare to initialize ths channel
                channel.pipeline().addFirst(beginInitProtocol);
                ctx.fireChannelRead(msg);
            }
        };
    }


    @SuppressWarnings("unchecked")
    private void registerChannelHandler() {
        ReflectClass<Object> minecraftServer = Reflect.Class(Bukkit.getServer()).getField(Reflect.Class("{nms}.MinecraftServer")).getReflectValue();
        ReflectClass<Object> serverConnection = minecraftServer.getField(Reflect.Class("{nms}.ServerConnection"), 0).getReflectValue();

        // We need to synchronize against this list
        networkManagers = (List<Object>) serverConnection.getMethodByReturn(List.class).call(serverConnection.getInstance());
        createServerChannelHandler();

        for (ChannelFuture channelFuture : (List<ChannelFuture>) serverConnection.getField(List.class, 0).getValue()) {
            Channel channel = channelFuture.channel();
            serverChannels.add(channel);
            channel.pipeline().addFirst(serverChannelHandler);
        }
    }

    private void unregisterChannelHandler() {
        if (serverChannelHandler == null)
            return;

        for (Channel serverChannel : serverChannels) {
            serverChannel.eventLoop().execute(() -> {
                try {
                    serverChannel.pipeline().remove(serverChannelHandler);
                } catch (NoSuchElementException ignored) {
                }
            });
        }
    }

    public final void close() {
        if (!closed) {
            closed = true;

            // Remove our handlers
            for (Interceptor interceptor : interceptors) {
                interceptors.getC
            }


            // Clean up Bukkit
            unregisterChannelHandler();
        }
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
                interceptor = new Interceptor(channel);
                channel.pipeline().addBefore("packet_handler", handlerName, interceptor);
                interceptors.add(interceptor);
            }

            return interceptor;
        } catch (IllegalArgumentException e) {
            // Try again
            return (Interceptor) channel.pipeline().get(handlerName);
        }
    }
}
