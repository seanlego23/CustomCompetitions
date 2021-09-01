package io.github.seanlego23.customcompetitions.competitions;

import io.github.seanlego23.customcompetitions.competitions.settings.timer.CompetitionTimer;
import io.github.seanlego23.customcompetitions.regions.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public final class CompetitionManager {

    private Map<World, List<Competition>> competitionsMap = new HashMap<>();
    private Map<Competition, List<ScheduledFuture<CompetitionTimer>>> competitionTimers = new HashMap<>();

    private ThreadGroup competitionTimerThreadGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(),
            "CompetitionTimer ThreadGroup");

    private ThreadFactory competitionTimerThreadFactory = new ThreadFactory() {

        private static long threadCount = 0;

        private static long nextThreadNum() {
            return ++threadCount;
        }

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(competitionTimerThreadGroup, r, "CompetitionTimerThread-" + nextThreadNum());
        }
    };

    private ScheduledThreadPoolExecutor competitionTimerScheduler = new ScheduledThreadPoolExecutor(20,
            competitionTimerThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy());

    public CompetitionManager() {

    }

    public boolean addCompetition(Competition competition) {
        return false;
    }

    public boolean removeCompetition(Competition competition) {
        return false;
    }

}
