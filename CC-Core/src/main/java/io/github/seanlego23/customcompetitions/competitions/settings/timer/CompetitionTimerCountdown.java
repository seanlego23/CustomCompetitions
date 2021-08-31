package io.github.seanlego23.customcompetitions.competitions.settings.timer;

public class CompetitionTimerCountdown extends AbstractCompetitionTimer {

    public CompetitionTimerCountdown(CompetitionTimer timer) {
        super(timer);
    }

    @Override
    public void run() {
        if (timer.subtractOne() == 0)
            cancel();
    }
}
