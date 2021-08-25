package io.github.seanlego23.customcompetitions.config;

@ConfigOptions(name = "config",
               header = """
                        {{pre:-, post:-, flags:ceoh, info:{1, 1, 75}}, id:"CustomCompetitions Config"}
                        Author: {id:author}
                        Version: {id:version}
                        {id:-, flags:p, info:{75}}
                        """ + "\n",
               sectionHeader = """
                               {id:-, flags:p, info:{40}}
                               {{pre:-, post:-, flags:ceoh, info:{1, 1, 40}}, id:section-name}
                               {id:-, flags:p, info:{40}}
                               """)
public class CCConfig {

    @ConfigSection(name = "WorldEdit")
    @Option(alias = "maxSearchTime", section = "WorldEdit", path = "biomeRegion")
    @OptionDescription(desc = "Maximum amount of time to search for an entire biome before aborting.")
    public static long maxBiomeRegionSearchTime;

    @Option(section = "WorldEdit", path = "biomeRegion.maxSearchTime")
    @OptionDescription(desc = "If true and the region is partially defined when the maxSearchTime is reached, the " +
                              "region will continue to be searched, instead of aborting.")
    public static boolean ignoreIfPartiallyDefined;

    private CCConfig() { }

}
