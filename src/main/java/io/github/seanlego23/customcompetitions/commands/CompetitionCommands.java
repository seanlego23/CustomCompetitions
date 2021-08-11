package io.github.seanlego23.customcompetitions.commands;

import com.sk89q.intake.Command;
import com.sk89q.intake.Require;
import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.competitions.settings.CompetitionSettings;
import io.github.seanlego23.customcompetitions.user.User;

public class CompetitionCommands {

    public CompetitionCommands() {

    }

    @Command(aliases = "create", desc = "Create a custom competition.")
    @Require("customcompetitions.create")
    public void create(User user, String name, CompetitionSettings settings) {

    }

    @Command(aliases = "info", desc = "Get info about a competition.")
    @Require("customcompetitions.info")
    public void info(User user, Competition competition) {

    }

}
