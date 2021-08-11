package io.github.seanlego23.customcompetitions.competitions.settings;

public class CompetitionTimer {

    private final boolean sysTime;
    private final long setTime;
    private long timeLeft;

    public CompetitionTimer(boolean SystemTimeOrTicks, long time) {
        sysTime = SystemTimeOrTicks;
        setTime = time;
        timeLeft = time;
    }

    public boolean isSystemTime() {
        return sysTime;
    }

    public boolean isMinecraftTime() {
        return !sysTime;
    }

    public long getSetTime() {
        return setTime;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public long subtractOne() {
        return timeLeft == 0 ? 0 : (timeLeft -= 1);
    }

    public long addOne() {
        return timeLeft == Long.MAX_VALUE ? Long.MAX_VALUE : (timeLeft += 1);
    }

    public void setTime(long time) {
        if (time <= 0)
            timeLeft = 0;
        else
            timeLeft = time;
    }

    public void reset() {
        timeLeft = setTime;
    }

}
