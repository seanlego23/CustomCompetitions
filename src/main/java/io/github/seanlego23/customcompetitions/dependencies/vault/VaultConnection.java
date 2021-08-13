package io.github.seanlego23.customcompetitions.dependencies.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class VaultConnection {

    private final Plugin connectingPlugin;

    private Economy economy;
    private Permission permission;

    public VaultConnection(@NotNull Plugin plugin) {
        connectingPlugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new VaultListener(), plugin);
        setupEconomy();
        setupPermissions();
    }

    private void setupEconomy() {
        if (connectingPlugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> economyProvider =
                    connectingPlugin.getServer().getServicesManager().getRegistration(Economy.class);
            if (economyProvider != null) {
                connectingPlugin.getLogger().finer("Vault economy enabled.");
                economy = economyProvider.getProvider();
            } else {
                connectingPlugin.getLogger().finer("Vault economy not detected.");
                economy = null;
            }
        } else {
            connectingPlugin.getLogger().finer("Vault was not found.");
            economy = null;
        }
    }

    private void setupPermissions() {
        if (connectingPlugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> permissionProvider =
                    connectingPlugin.getServer().getServicesManager().getRegistration(Permission.class);
            if (permissionProvider != null) {
                connectingPlugin.getLogger().finer("Vault permissions enabled.");
                permission = permissionProvider.getProvider();
            } else {
                connectingPlugin.getLogger().finer("Vault permissions not detected.");
                permission = null;
            }
        } else
            permission = null;
    }

    private class VaultListener implements Listener {

        @EventHandler
        private void vaultEnabled(PluginEnableEvent event) {
            setupEconomy();
            setupPermissions();
        }

        @EventHandler
        private void vaultDisabled(PluginDisableEvent event) {
            economy = null;
            permission = null;
        }

    }

    public boolean hasEconomy() {
        return economy != null;
    }

    public boolean hasPermission() {
        return permission != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermission() {
        return permission;
    }

}
