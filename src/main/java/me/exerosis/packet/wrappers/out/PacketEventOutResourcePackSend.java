package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventOutResourcePackSend extends PacketWrapper {
    private String URL;
    private String hash;

    public PacketEventOutResourcePackSend(Object packet, PacketPlayer player) {
        super(packet, player);
        URL = Reflect.Field(packet, String.class, "a").getValue();
        hash = Reflect.Field(packet, String.class, "b").getValue();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        Reflect.Field(super.getPacket(), String.class, "a").setValue(URL);
        this.URL = URL;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        Reflect.Field(super.getPacket(), String.class, "b").setValue(hash);
        this.hash = hash;
    }
}
