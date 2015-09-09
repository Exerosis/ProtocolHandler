package me.exerosis.packet.player.injection.packet.player.display;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.display.event.HideDisplayEvent;
import me.exerosis.packet.player.injection.packet.player.display.event.ShowDisplayEvent;
import org.bukkit.Bukkit;

import java.util.TreeSet;

public class DisplayHolder<T extends Displayable> {
    private TreeSet<T> _bars = new TreeSet<>();
    private PacketPlayer _player;

    public DisplayHolder(PacketPlayer player) {
        _player = player;
    }

    public void add(T displayable) {
        if (displayable == null || _bars.contains(displayable))
            return;
        showFirst(displayable, false);
        _bars.add(displayable);
        showFirst(displayable, true);
        Bukkit.getPluginManager().callEvent(new ShowDisplayEvent(_player, _bars.first()));
    }

    public void remove(T displayable) {
        if (displayable == null || !_bars.contains(displayable))
            return;
        showFirst(displayable, false);
        _bars.remove(displayable);
        showFirst(displayable, true);
        Bukkit.getPluginManager().callEvent(new HideDisplayEvent(_player, displayable));
    }

    public boolean contains(T displayable) {
        return _bars.contains(displayable);
    }

    private void showFirst(T displayable, boolean shown) {
        if (_bars.size() > 0)
            if (shown)
                _bars.first().show(_player);
            else
                _bars.first().hide(_player);
    }
}