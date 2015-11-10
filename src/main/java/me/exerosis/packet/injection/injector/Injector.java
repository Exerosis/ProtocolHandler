package me.exerosis.packet.injection.injector;

import io.netty.channel.*;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("SynchronizeOnNonFinalField")
public class Injector {
    protected volatile boolean closed;
    private List<Object> networkManagers = new ArrayList<>();
    private List<Channel> serverChannels = new ArrayList<>();

    private ChannelInboundHandlerAdapter serverChannelHandler;
    private ChannelInitializer<Channel> beginInitProtocol;
    private ChannelInitializer<Channel> endInitProtocol;
    private InterceptorManager interceptorManager;

    public Injector() {
        interceptorManager = new InterceptorManager();
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
                            interceptorManager.inject(channel);
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

    public final void close() {
        if (closed)
            return;
        closed = true;
        interceptorManager.uninjectAll();

        if (serverChannelHandler != null)
            for (Channel serverChannel : serverChannels) {
                serverChannel.eventLoop().execute(() -> {
                    try {
                        serverChannel.pipeline().remove(serverChannelHandler);
                    } catch (NoSuchElementException ignored) {
                    }
                });
            }
    }
}