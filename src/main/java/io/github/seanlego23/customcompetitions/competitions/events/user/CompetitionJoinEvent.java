package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.competitions.Competition;
import io.github.seanlego23.customcompetitions.user.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called just before a User joins a competition.
 */
public class CompetitionJoinEvent extends UserEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    protected final Competition competition;

    public CompetitionJoinEvent(Competition competition, User user) {
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
