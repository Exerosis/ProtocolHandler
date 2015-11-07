package me.exerosis.packet.display.displayables;

import me.exerosis.packet.display.DisplayUtils;
import me.exerosis.packet.display.Displayable;
import me.exerosis.packet.injection.PacketPlayer;
import me.exerosis.packet.utils.ticker.ExTask;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class ActionBar extends Displayable implements Runnable {
    private String _message;
    private Set<PacketPlayer> _players = new HashSet<>();

    public ActionBar(int priority, String message) {
        super(priority);
        this._message = message;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        this._message = message;
        refresh();
    }

    private void refresh() {
        for (PacketPlayer player : _players)
            player.sendPacket(new PacketPlayOutChat(DisplayUtils.serializeText(_message, ChatColor.WHITE), (byte) 2));
    }

    @Override
    public void show(PacketPlayer player) {
        ExTask.startTask(this, 1, 1);
        _players.add(player);
    }

    @Override
    public void hide(PacketPlayer player) {
        if (_players.size() <= 0)
            ExTask.stopTask(this);
        _players.remove(player);
    }

    @Override
    public void run() {
        refresh();
    }

    @Override
    public String toString() {
        return _message;
    }
}