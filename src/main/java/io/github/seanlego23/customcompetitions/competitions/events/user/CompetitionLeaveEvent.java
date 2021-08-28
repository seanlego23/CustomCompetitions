package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.user.User;

public class CompetitionLeaveEvent extends UserEvent {

    public CompetitionLeaveEvent(User user) {
        super(user);
    }
}
