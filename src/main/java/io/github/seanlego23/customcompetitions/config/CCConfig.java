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
    @Option(alias = "searchLength", section = "WorldEdit", path = "biomeRegion")
    @OptionDescription(desc = "How many chunks in each direction to search before giving up.")
    public int maxBiomeRegionChunkSearch;

    @Option(alias = "maximumSize", section = "WorldEdit", path = "biomeRegion")
    @OptionDescription(desc = "Maximum amount of chunks that a biomeRegion can have.") //TODO: Find out good amount
    public int maxBiomeRegionChunks;

    public CCConfig() { }

}
