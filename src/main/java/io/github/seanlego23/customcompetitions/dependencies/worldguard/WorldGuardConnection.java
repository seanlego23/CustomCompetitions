package io.github.seanlego23.customcompetitions.dependencies.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

public class WorldGuardConnection {

    private final Plugin connectingPlugin;

    private WorldGuardPlugin worldGuardPlugin;
    WorldGuard worldGuard;

    public WorldGuardConnection(Plugin plugin) {
        connectingPlugin = plugin;
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

    /**
     * Attempts to connect to the WorldEdit plugin.
     *
     * @return true if the WorldEdit plugin is available and able to be interfaced with.
     */
    boolean connect() {
        if (!isConnected()) {
            worldGuardPlugin = retrieveWorldGuardPluginFromServer();
            if (worldGuardPlugin != null) {
                worldGuard = WorldGuard.getInstance();
                connectingPlugin.getLogger()
                                .info(String.format("Found %s. Using it for selections.", worldGuardPlugin.getName()));
                return true;
            }
        }
        return false;
    }

    void disconnect() {
        worldGuardPlugin = null;
        worldGuard = null;
    }

    /**
     * Tests the connection to the WorldEdit plugin.
     *
     * @return true if current connected to the WorldEdit plugin.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isConnected() {
        return worldGuardPlugin != null;
    }

}
