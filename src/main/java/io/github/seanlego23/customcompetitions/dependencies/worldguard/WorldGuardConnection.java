package io.github.seanlego23.customcompetitions.dependencies.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class WorldGuardConnection {

    private final Plugin connectingPlugin;

    private WorldGuardPlugin worldGuardPlugin;
    WorldGuard worldGuard;

    public WorldGuardConnection(@NotNull Plugin plugin) {
        connectingPlugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new WorldGuardListener(), plugin);
    }

    private WorldGuardPlugin retrieveWorldGuardPluginFromServer() {
        Plugin plugin = connectingPlugin.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null)
            return null;
        else if (plugin instanceof WorldGuardPlugin)
            return (WorldGuardPlugin) plugin;
        else {
            connectingPlugin.getLogger().warning("WorldEdit v" + plugin.getDescription().getVersion()
                                                 + " is incompatible with " +
                                                 connectingPlugin.getDescription().getName() + " v"
                                                 + connectingPlugin.getDescription().getVersion());
            return null;
        }
    }

    void connect() {
        if (!isConnected()) {
            worldGuardPlugin = retrieveWorldGuardPluginFromServer();
            if (worldGuardPlugin != null) {
                worldGuard = WorldGuard.getInstance();
                connectingPlugin.getLogger()
                                .info(String.format("Found %s. Using it for selections.", worldGuardPlugin.getName()));
            }
        }
    }

    void disconnect() {
        worldGuardPlugin = null;
        worldGuard = null;
    }

    private class WorldGuardListener implements Listener {

        @EventHandler
        private void worldGuardEnabled(PluginEnableEvent event) {
            if (event.getPlugin().getName().equals("WorldGuard"))
                connect();
        }

        @EventHandler
        private void worldGuardDisabled(PluginDisableEvent event) {
            if (event.getPlugin().getName().equals("WorldGuard"))
                disconnect();
        }

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isConnected() {
        return worldGuardPlugin != null;
    }

}
