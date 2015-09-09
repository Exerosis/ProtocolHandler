package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityMetadata;

public class EntityMetaData implements PreconPacket {
    private int entityId;
    private int index;
    private Object value;
    private PacketPlayOutEntityMetadata packet;

    public EntityMetaData(int entityId, int index, Object value) {
        super();
        this.entityId = entityId;
        this.index = index;
        this.value = value;
        DataWatcher watcher = new DataWatcher(null);
        watcher.a(index, value);
        packet = new PacketPlayOutEntityMetadata(entityId, watcher, true);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getIndex() {
        return index;
    }

    public Object getValue() {
        return value;
    }
}
