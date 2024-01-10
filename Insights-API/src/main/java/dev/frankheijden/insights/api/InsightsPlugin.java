package dev.frankheijden.insights.api;

import me.nahu.scheduler.wrapper.WrappedScheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class InsightsPlugin extends JavaPlugin implements InsightsMain {

    protected static InsightsPlugin instance;

    public static InsightsPlugin getInstance() {
        return instance;
    }

    /**
     * Reloads all configurations.
     */
    public void reloadConfigs() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        reloadSettings();
        reloadMessages();
        reloadNotifications();
        reloadLimits();
    }

    public abstract void reload();

    public boolean isAvailable(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }

    public abstract WrappedScheduler getScheduler();
}
