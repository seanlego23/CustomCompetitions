package io.github.seanlego23.customcompetitions.competitions.settings;

import io.github.seanlego23.customcompetitions.competitions.settings.timer.CompetitionTimer;

import java.util.List;

public class CompetitionSettings {

    private CompetitionTimer before;
    private CompetitionTimer between;
    private boolean reoccur;
    private List<CompetitionStage> stages;

    public CompetitionSettings() {

    }

}
