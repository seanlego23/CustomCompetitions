package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.Biome;
import io.github.seanlego23.customcompetitions.util.Location;

public interface World extends Region {

    String getName();

    int getMaxHeight();

    int getMinHeight();

    Biome getBiome(Location location);

}
