package io.github.seanlego23.customcompetitions;

import io.github.seanlego23.customcompetitions.competitions.CompetitionManager;
import org.bukkit.plugin.java.JavaPlugin;

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
}
