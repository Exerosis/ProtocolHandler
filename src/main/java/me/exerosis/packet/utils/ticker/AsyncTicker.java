package me.exerosis.packet.utils.ticker;

import me.exerosis.packet.PacketAPI;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AsyncTicker extends BukkitRunnable {
    private static AsyncTicker instace;
    private ArrayList<AsyncTickListener> listeners = new ArrayList<>();

    private AsyncTicker() {
        this.runTaskTimerAsynchronously(PacketAPI.getPlugin(), 0, (long) 0.001);
    }

    public static AsyncTicker getInstace() {
        if (instace == null)
            instace = new AsyncTicker();
        return instace;
    }

    public static void registerListener(AsyncTickListener tickListener) {
        getInstace().getListeners().add(tickListener);
    }

    public static void unregisterListener(AsyncTickListener tickListener) {
        getInstace().getListeners().remove(tickListener);
    }

    public void run() {
        for (AsyncTickListener listener : listeners) {
            listener.tick();
        }
    }

    private ArrayList<AsyncTickListener> getListeners() {
        return listeners;
    }
}
