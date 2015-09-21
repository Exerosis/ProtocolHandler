package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketWrapperOutTabComplete extends PacketWrapper {

    public PacketWrapperOutTabComplete(Object packet) {
        super(packet);
    }

    public PacketWrapperOutTabComplete(String... results) {
        super(PacketPlay.Out.TabComplete(results));
    }

    public String[] getResults() {
        return Reflect.Field(getPacket(), String[].class, 0).getValue();
    }

    public void setResults(String... results) {
        Reflect.Field(getPacket(), String[].class, 0).setValue(results);
    }
}