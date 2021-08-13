package io.github.seanlego23.customcompetitions;

import io.github.seanlego23.customcompetitions.competitions.CompetitionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class CustomCompetitions extends JavaPlugin {

    private final CompetitionManager competitionManager = new CompetitionManager();

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
