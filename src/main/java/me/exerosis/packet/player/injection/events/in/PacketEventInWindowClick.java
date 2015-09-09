package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventInWindowClick extends PacketEvent {

    private int windowId;
    private int slot;
    private int button;
    private short actionNumber;
    private Object item;
    private int mode;

    public PacketEventInWindowClick(Object packet, PacketPlayer player) {
        super(packet, player);

        windowId = Reflect.Field(packet, int.class, "a").getValue();

        slot = Reflect.Field(packet, int.class, "slot").getValue();

        button = Reflect.Field(packet, int.class, "button").getValue();

        actionNumber = Reflect.Field(packet, short.class, "d").getValue();

        item = Reflect.Field(packet, Object.class, "item").getValue();

        mode = Reflect.Field(packet, int.class, "shift").getValue();
    }

    public int getWindowId() {
        return windowId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
        Reflect.Field(super.getPacket(), int.class, "slot").setValue(slot);
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
        Reflect.Field(super.getPacket(), int.class, "button").setValue(button);
    }

    public short getActionNumber() {
        return actionNumber;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
        Reflect.Field(super.getPacket(), Object.class, "item").setValue(item);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        Reflect.Field(super.getPacket(), int.class, "shift").setValue(mode);
    }

}