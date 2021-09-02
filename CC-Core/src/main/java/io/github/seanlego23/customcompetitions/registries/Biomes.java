package io.github.seanlego23.customcompetitions.registries;

import io.github.seanlego23.customcompetitions.util.Biome;

import java.util.ArrayList;
import java.util.List;

public class Biomes {

    public static List<String> BIOMES = new ArrayList<>();
    public static List<String> OVERWORLD_BIOMES = new ArrayList<>();
    public static List<String> NETHER_BIOMES = new ArrayList<>();
    public static List<String> THE_END_BIOMES = new ArrayList<>();

    static {
        for (Biome biome : Biome.values()) {
            BIOMES.add(biome.toString());

            if (biome == Biome.NETHER_WASTES ||
                biome == Biome.CRIMSON_FOREST ||
                biome == Biome.WARPED_FOREST ||
                biome == Biome.SOUL_SAND_VALLEY ||
                biome == Biome.BASALT_DELTAS)
                NETHER_BIOMES.add(biome.toString());
            else if (biome == Biome.THE_END ||
                     biome == Biome.SMALL_END_ISLANDS ||
                     biome == Biome.END_MIDLANDS ||
                     biome == Biome.END_HIGHLANDS ||
                     biome == Biome.END_BARRENS)
                THE_END_BIOMES.add(biome.toString());
            else if (biome != Biome.THE_VOID &&
                     biome != Biome.CUSTOM)
                OVERWORLD_BIOMES.add(biome.toString());
        }
    }

}
