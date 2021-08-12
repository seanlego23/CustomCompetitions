package io.github.seanlego23.customcompetitions.competitions.settings.timer;

/**
 * A timer to be used in a competition. A competition does not have to use timers.
 */
public class CompetitionTimer {

    private final boolean sysTime;
    private final long setTime;
    private long timeLeft;

    /**
     * Creates a timer with the given {@code time}.
     *
     * @param systemTime whether this timer will use system time or minecraft time.
     * @param time amount of time in seconds (if {@code systemTime} is true), or ticks (if false).
     */
    public CompetitionTimer(boolean systemTime, long time) {
        sysTime = systemTime;
        setTime = time;
        timeLeft = time;
    }

    /**
     * If this timer is using system time.
     *
     * @return true, if this timer is using system time.
     */
    public boolean isSystemTime() {
        return sysTime;
    }

    /**
     * If this timer is using minecraft time.
     *
     * @return true, if this timer is using minecraft time.
     */
    public boolean isMinecraftTime() {
        return !sysTime;
    }

    /**
     * Gets the start time for this timer. This value is set when the timer is created, and never changes.
     *
     * @return the start time for this timer.
     */
    public long getSetTime() {
        return setTime;
    }

    /**
     * Gets the amount of time left on this timer.
     *
     * @return the amount of time left on this timer.
     */
    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * Subtract one from the amount of time left. Timer can never go below 0.
     *
     * @return the new time left.
     */
    public long subtractOne() {
        return timeLeft == 0 ? 0 : (timeLeft -= 1);
    }

    /**
     * Add one to the amount of time left. Timer can never go above {@link Long#MAX_VALUE}.
     *
     * @return the new time left.
     */
    public long addOne() {
        return timeLeft == Long.MAX_VALUE ? Long.MAX_VALUE : (timeLeft += 1);
    }

    /**
     * Sets the time left to {@code time}. If {@code time} is less than 0, then it will be treated as 0.
     *
     * @param time new time left.
     */
    public void setTime(long time) {
        if (time <= 0)
            timeLeft = 0;
        else
            timeLeft = time;
    }

    /**
     * Resets the time left of this timer to the start time of this timer.
     */
    public void reset() {
        timeLeft = setTime;
    }

}
