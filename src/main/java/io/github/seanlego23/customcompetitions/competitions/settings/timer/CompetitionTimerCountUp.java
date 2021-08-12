package io.github.seanlego23.customcompetitions.competitions.settings.timer;

public class CompetitionTimerCountUp extends AbstractCompetitionTimer {

    public CompetitionTimerCountUp(CompetitionTimer timer) {
        super(timer);
    }

    @Override
    public void run() {
        if (timer.addOne() == Long.MAX_VALUE)
            cancel();
    }
}
