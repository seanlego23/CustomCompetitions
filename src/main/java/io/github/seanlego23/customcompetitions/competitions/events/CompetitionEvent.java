package io.github.seanlego23.customcompetitions.competitions.events;

public enum CompetitionEvent {
    ;

    private String name;

    CompetitionEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
