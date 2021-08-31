package io.github.seanlego23.customcompetitions.competitions.settings.timer;

public interface CompetitionTimerRunnable extends Runnable {

    /**
     * Returns the {@link CompetitionTimer} associated with this class.
     * @return the CompetitionTimer associated with this class.
     */
    CompetitionTimer getTimer();

    /**
     * Cancels this runnable from being run again.
     */
    void cancel();

    /**
     * Returns whether this runnable is canceled or not.
     * @return true, if {@link #cancel()} has been called.
     */
    boolean isCanceled();

}
