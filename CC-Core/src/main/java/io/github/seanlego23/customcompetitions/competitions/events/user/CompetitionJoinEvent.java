package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.competitions.events.CancellableEvent;
import io.github.seanlego23.customcompetitions.recipients.user.User;

/**
 * This event is called just before a User joins a competition.
 */
public class CompetitionJoinEvent extends UserEvent implements CancellableEvent {

    private boolean cancel = false;
    protected final Competition competition;

    public CompetitionJoinEvent(Competition competition, User user) {
        super(user);
        this.competition = competition;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Competition getCompetition() {
        return competition;
    }

}
