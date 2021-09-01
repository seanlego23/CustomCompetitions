package io.github.seanlego23.customcompetitions;

import io.github.seanlego23.customcompetitions.competitions.CompetitionManager;

public final class CustomCompetitions {

    private final CompetitionManager competitionManager = new CompetitionManager();

//    private final BiomeRegionCreator biomeRegionCreator = new BiomeRegionCreator(this);

    public CustomCompetitions() {

    }

    public CompetitionManager getCompetitionManager() {
        return competitionManager;
    }

/*    public BiomeRegionCreator getBiomeRegionCreator() {
        return biomeRegionCreator;
    }*/
}
