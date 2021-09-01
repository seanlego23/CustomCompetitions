package io.github.seanlego23.customcompetitions.competitions.events;

public interface CancellableEvent {

    boolean isCancelled();

    void setCancelled(boolean cancel);

}
