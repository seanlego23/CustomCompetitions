package io.github.seanlego23.customcompetitions.competitions.events;

public interface CancellableEvent {

    boolean isCanceled();

    void setCanceled(boolean cancel);

}
