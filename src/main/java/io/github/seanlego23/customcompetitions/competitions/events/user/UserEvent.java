package io.github.seanlego23.customcompetitions.competitions.events.user;

import io.github.seanlego23.customcompetitions.user.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UserEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    protected final User user;

    public UserEvent(User user) {
        this.user = user;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public User getUser() {
        return user;
    }
}
