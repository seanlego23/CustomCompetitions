package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.recipients.user.User;

public class CompetitionKickEvent extends UserEvent {

    protected final Competition competition;

    public CompetitionKickEvent(Competition competition, User user) {
        super(user);
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }

}
