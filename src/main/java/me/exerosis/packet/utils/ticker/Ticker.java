package me.exerosis.packet.utils.ticker;

import io.netty.util.internal.ConcurrentSet;
import me.exerosis.packet.PacketAPI;
import org.bukkit.scheduler.BukkitRunnable;

public class Ticker extends BukkitRunnable {
    private static Ticker instace;
    int x = 0;
    private ConcurrentSet<TickListener> listeners = new ConcurrentSet<>();
    private ConcurrentSet<TickListener> secondListeners = new ConcurrentSet<>();

    private Ticker() {
        this.runTaskTimer(PacketAPI.getPlugin(), 1, 1);
    }

    public static Ticker getInstace() {
        if (instace == null)
            instace = new Ticker();
        return instace;
    }

    public static void registerListener(TickListener tickListener) {
        getInstace().getListeners().add(tickListener);
    }

    public static void registerSecListener(TickListener tickListener) {
        getInstace().getSecListeners().add(tickListener);
    }

    public static void unregisterListener(TickListener tickListener) {
        if (getInstace().getListeners().contains(tickListener))
            getInstace().getListeners().remove(tickListener);
        else if (getInstace().getSecListeners().contains(tickListener))
            getInstace().getSecListeners().remove(tickListener);
    }

    public static boolean isRegistered(TickListener listener) {
        return getInstace().getListeners().contains(listener) || getInstace().getSecListeners().contains(listener);
    }

    public void run() {
        x++;
        for (TickListener listener : listeners) {
            listener.tick();
        }
        if (x % 20 == 0) {
            for (TickListener listener : secondListeners) {
                listener.tick();
            }
        }
    }

    private ConcurrentSet<TickListener> getListeners() {
        return listeners;
    }

    private ConcurrentSet<TickListener> getSecListeners() {
        return secondListeners;
    }
}
