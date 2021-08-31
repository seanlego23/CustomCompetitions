package io.github.seanlego23.customcompetitions.competitions.settings.timer;

public abstract class AbstractCompetitionTimer implements CompetitionTimerRunnable {

    protected CompetitionTimer timer;
    protected boolean canceled = false;

    public AbstractCompetitionTimer(CompetitionTimer timer) {
        this.timer = timer;
    }

    @Override
    public void cancel() {
        canceled = true;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public CompetitionTimer getTimer() {
        return this.timer;
    }

}
