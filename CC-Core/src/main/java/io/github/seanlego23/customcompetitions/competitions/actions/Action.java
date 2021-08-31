package io.github.seanlego23.customcompetitions.competitions.actions;

import io.github.seanlego23.customcompetitions.competitions.Competition;

public interface Action {

    enum ActionResult {

    }

    String name();

    ActionResult perform(Competition competition, Object... objects);

}
