package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventOutConfirmTransaction extends PacketWrapper {
    private int windowId;
    private short actionNumber;
    private boolean accepted;

    public PacketEventOutConfirmTransaction(Object packet, PacketPlayer player) {
        super(packet, player);
        windowId = Reflect.Field(packet, int.class, "a").getValue();
        actionNumber = Reflect.Field(packet, short.class, "b").getValue();
        accepted = Reflect.Field(packet, boolean.class, "c").getValue();
    }

    public int getWindowId() {
        return windowId;
    }

    public void setWindowId(int windowId) {
        Reflect.Field(super.getPacket(), int.class, "a").setValue(windowId);
        this.windowId = windowId;
    }

    public short getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(short actionNumber) {
        Reflect.Field(super.getPacket(), short.class, "b").setValue(actionNumber);
        this.actionNumber = actionNumber;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        Reflect.Field(super.getPacket(), boolean.class, "c").setValue(accepted);
        this.accepted = accepted;
    }
}
