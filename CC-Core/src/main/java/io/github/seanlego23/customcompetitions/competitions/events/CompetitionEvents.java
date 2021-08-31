package io.github.seanlego23.customcompetitions.competitions.events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompetitionEvents {

    public static Set<CompetitionEvent> EVENTS = new HashSet<>();

    static {
        EVENTS.addAll(Arrays.asList(CompetitionEvent.values()));
    }

}
