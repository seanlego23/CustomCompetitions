package io.github.seanlego23.customcompetitions;

import io.github.seanlego23.customcompetitions.competitions.CompetitionManager;
import io.github.seanlego23.customcompetitions.dependencies.vault.VaultConnection;
import io.github.seanlego23.customcompetitions.dependencies.worldedit.WorldEditConnection;
import io.github.seanlego23.customcompetitions.dependencies.worldguard.WorldGuardConnection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class CustomCompetitions extends JavaPlugin {

    private final CompetitionManager competitionManager = new CompetitionManager();

    private final WorldEditConnection worldEditConnection = new WorldEditConnection(this);
    private final WorldGuardConnection worldGuardConnection = new WorldGuardConnection(this);
    private final VaultConnection vaultConnection = new VaultConnection(this);

    public CustomCompetitions() {

    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CompetitionManager getCompetitionManager() {
        return competitionManager;
    }

    public WorldEditConnection getWorldEditConnection() {
        return worldEditConnection;
    }

    public WorldGuardConnection getWorldGuardConnection() {
        return worldGuardConnection;
    }

    public VaultConnection getVaultConnection() {
        return vaultConnection;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
            @NotNull String[] args) {
        return null;
    }
}
