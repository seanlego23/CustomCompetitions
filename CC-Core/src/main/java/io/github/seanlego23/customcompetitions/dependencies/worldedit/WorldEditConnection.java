package io.github.seanlego23.customcompetitions.dependencies.worldedit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class WorldEditConnection {

    private final Plugin connectingPlugin;

    private WorldEditPlugin worldEditPlugin;
    private WorldEdit worldEdit;

    public WorldEditConnection(@NotNull Plugin plugin) {
        this.connectingPlugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new WorldEditListener(), plugin);
    }

    private WorldEditPlugin retrieveWorldEditPluginFromServer() {
        Plugin plugin = connectingPlugin.getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null) {
            plugin = connectingPlugin.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit");
        }

        if (plugin == null)
            return null;
        else if (plugin instanceof WorldEditPlugin)
            return (WorldEditPlugin) plugin;
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
            worldEditPlugin = retrieveWorldEditPluginFromServer();
            if (worldEditPlugin != null) {
                this.worldEdit = worldEditPlugin.getWorldEdit();
                connectingPlugin.getLogger()
                                .info(String.format("Found %s. Using it for selections.", worldEditPlugin.getName()));
            }
        }
    }

    void disconnect() {
        worldEditPlugin = null;
        this.worldEdit = null;
    }

    private class WorldEditListener implements Listener {

        @EventHandler
        private void worldEditEnabled(PluginEnableEvent event) {
            if (event.getPlugin().getName().equals("WorldEdit") || event.getPlugin().getName().equals(
                    "FastAsyncWorldEdit")) {
                connect();
            }
        }

        @EventHandler
        private void worldEditDisabled(PluginDisableEvent event) {
            if (event.getPlugin().getName().equals("WorldEdit") || event.getPlugin().getName().equals(
                    "FastAsyncWorldEdit")) {
                disconnect();
            }
        }

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isConnected() {
        return worldEditPlugin != null;
    }

    private Region getSelection(Player player) {
        if (!isConnected())
            throw new RuntimeException("WorldEdit connection is unavailable.");

        try {
            return worldEdit.getSessionManager().get(new BukkitPlayer(worldEditPlugin, player)).getSelection(
                    new BukkitWorld(player.getWorld()));
        } catch (IncompleteRegionException e) {
            return null;
        }
    }

    public boolean isSelectionAvailable(@NotNull Player player) {
        if (!isConnected())
            throw new RuntimeException("WorldEdit connection is unavailable.");

        return getSelection(player) != null;
    }
}
