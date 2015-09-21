package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public final class PacketWrapperInTabComplete extends PacketWrapper {

    public PacketWrapperInTabComplete(Object packet) {
        super(packet);
    }

    public PacketWrapperInTabComplete(String text, Object blockPosition) {
        super(PacketPlay.In.TabComplete(text, blockPosition));
    }

    public PacketWrapperInTabComplete(String text, Vector vector) {
        super(PacketPlay.In.TabComplete(text, vector));
    }

    public PacketWrapperInTabComplete(String text, Location location) {
        super(PacketPlay.In.TabComplete(text, location));
    }

    public PacketWrapperInTabComplete(String text, Block block) {
        super(PacketPlay.In.TabComplete(text, block));
    }

    public PacketWrapperInTabComplete(String text, int x, int y, int z) {
        super(PacketPlay.In.TabComplete(text, x, y, z));
    }

    public String getText() {
        return Reflect.Field(getPacket(), String.class, 0).getValue();
    }

    public void setText(String text) {
        Reflect.Field(getPacket(), String.class, 0).setValue(text);
    }

    public Vector getBlockPosition() {
        return PacketUtil.fromBlockPosition(Reflect.Field(getPacket(), Object.class, 1).getValue());
    }

    public void setBlockPosition(int x, int y, int z) {
        setBlockPosition(new Vector(x, y, z));
    }

    public void setBlockPosition(Block block) {
        setBlockPosition(block.getLocation());
    }

    public void setBlockPosition(Location location) {
        setBlockPosition(location.toVector());
    }

    public void setBlockPosition(Vector vector) {
        setBlockPosition(PacketUtil.toBlockLocation(vector));
    }

    public void setBlockPosition(Object blockPosition) {
        Reflect.Field(getPacket(), Object.class, 1).setValue(blockPosition);
    }
}