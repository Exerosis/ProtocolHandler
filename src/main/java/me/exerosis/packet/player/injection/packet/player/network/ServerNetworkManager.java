package me.exerosis.packet.player.injection.packet.player.network;

import com.google.common.collect.Lists;
import io.netty.channel.*;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.List;

public class ServerNetworkManager {
    // Injected channel handlers
    private List<Channel> serverChannels = Lists.newArrayList();
    private ChannelInboundHandlerAdapter serverChannelHandler;
    private ChannelInitializer<Channel> beginInitProtocol;
    private ChannelInitializer<Channel> endInitProtocol;

    public ServerNetworkManager() {
        createServerChannelHandler();
        boolean looking = true;
        ReflectClass<Object> minecraftServerClass = Reflect.Class("{nms}.MinecraftServer");
        ReflectClass<Object> serverConnectionClass = Reflect.Class("{nms}.ServerConnection");
        ReflectClass<Server> bukkitClass = Reflect.Class(Bukkit.getServer());
        ReflectClass<Object> minecraftServer = bukkitClass.getField(minecraftServerClass).getReflectType();
        ReflectClass<Object> serverConnection = minecraftServer.getField(serverConnectionClass).getReflectType();
        List value = (List) serverConnection.getMethodByReturn(List.class).callStatic(serverConnection.getInstance());

        System.out.println(value);
        for (int i = 0; looking; i++) {
            List list = serverConnection.getField(List.class, i).getValue();

            for (Object item : list) {
                if (!ChannelFuture.class.isInstance(item))
                    break;
                System.out.println("lol");
                // Channel future that contains the server connection
                ((ChannelFuture) item).channel().pipeline().addFirst(serverChannelHandler);
                looking = false;
            }
        }
    }

    private void createServerChannelHandler() {
        // Handle connected channels
        endInitProtocol = new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
               /* try {
                    // This can take a while, so we need to stop the main thread from interfering
                    synchronized (networkManagers) {
                        // Stop injecting channels
                        if (!closed) {
                            injectChannelInternal(channel);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
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


}
