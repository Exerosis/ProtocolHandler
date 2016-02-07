package me.exerosis.packet.display.displayables.tab;

import me.exerosis.event.EventListener;
import me.exerosis.packet.display.Displayable;
import me.exerosis.packet.injection.PacketPlayer;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class TabListManager extends Displayable {
    private final List<TabListEntry> tabList = new ArrayList<>();
    private final Set<PacketPlayer> players = new HashSet<>();

    public TabListManager(int priority) {
        super(priority);

        new EventListener<PacketPlayOutPlayerInfo, PacketPlayOutPlayerInfo>() {
            @Override
            public void onEvent(PacketPlayOutPlayerInfo event) {
                //if(players.contains(event.getPlayer()))
                try {
                    for (Field field : event.getClass().getFields()) {
                        if (!field.getType().equals(List.class))
                            continue;
                        field.setAccessible(true);
                        List<Object> list = (List<Object>) field.get(event);
                        list.clear();
                        list.addAll(tabList.stream().map(TabListEntry::getPlayerDataInfo).collect(Collectors.toList()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }.register(PacketPlayOutPlayerInfo.class);
    }

    @Override
    public void show(PacketPlayer player) {
        players.add(player);

    }

    @Override
    public void hide(PacketPlayer player) {
        players.remove(player);
    }

    private void createPacket() {
        Reflect.Class("{nms}.PacketPlayOutPlayerInfo")
    }
}