package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.recipients.user.User;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CompetitionLeaveEvent extends UserEvent {

    private static final HandlerList handlers = new HandlerList();

    protected final Competition competition;

    public CompetitionLeaveEvent(Competition competition, User user) {
        super(user);
        this.competition = competition;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public Competition getCompetition() {
        return competition;
    }
}
