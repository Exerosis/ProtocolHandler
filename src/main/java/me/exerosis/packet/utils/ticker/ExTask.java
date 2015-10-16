package me.exerosis.packet.utils.ticker;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class ExTask {
    private static Map<Runnable, Integer> _ids = new HashMap<>();

    private ExTask() {
    }

    public static Map<Runnable, Integer> getIDs() {
        return _ids;
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugins()[0], runnable, delay);
    }

    public static void startTask(Runnable runnable, long delay, long time) {
        if (!isRunning(runnable))
            _ids.put(runnable, Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugins()[0], runnable, delay, time).getTaskId());
    }

    public static void stopTask(Runnable runnable) {
        if (isRunning(runnable)) {
            Bukkit.getScheduler().cancelTask(_ids.get(runnable));
            _ids.remove(runnable);
        }
    }

    public static boolean isRunning(Runnable runnable) {
        return _ids.get(runnable) != null && Bukkit.getScheduler().isQueued(_ids.get(runnable));
    }
}