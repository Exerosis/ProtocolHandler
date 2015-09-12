package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketWrapperOutConfirmTransaction extends PacketWrapper {

    public PacketWrapperOutConfirmTransaction(Object packet) {
        super(packet);
    }

    public PacketWrapperOutConfirmTransaction(int windowID, short actionNumber, boolean accepted) {
        super(PacketPlay.Out.Transaction(windowID, actionNumber, accepted));
    }

    public int getWindowId() {
        return Reflect.Field(getPacket(), int.class).getValue();
    }

    public void setWindowId(int windowId) {
        Reflect.Field(getPacket(), int.class).setValue(windowId);
    }

    public short getActionNumber() {
        return Reflect.Field(getPacket(), short.class).getValue();
    }

    public void setActionNumber(short actionNumber) {
        Reflect.Field(getPacket(), short.class).setValue(actionNumber);
    }

    public boolean isAccepted() {
        return Reflect.Field(getPacket(), boolean.class).getValue();
    }

    public void setAccepted(boolean accepted) {
        Reflect.Field(getPacket(), boolean.class, 0).setValue(accepted);
    }
}