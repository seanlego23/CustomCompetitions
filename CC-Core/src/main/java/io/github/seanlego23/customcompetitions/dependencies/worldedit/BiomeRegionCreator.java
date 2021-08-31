package io.github.seanlego23.customcompetitions.dependencies.worldedit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.util.Location;
import io.github.seanlego23.customcompetitions.CustomCompetitions;
import io.github.seanlego23.customcompetitions.recipients.user.User;
import org.bukkit.scheduler.BukkitTask;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BiomeRegionCreator {

    private final CustomCompetitions plugin;
    private final Map<User, BiomeRegionData> biomeRegions = new TreeMap<>(Comparator.comparing(User::getName));
    private final BukkitTask bukkitTask;

    public BiomeRegionCreator(CustomCompetitions plugin) {
        this.plugin = plugin;
        bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, new BiomeRegionStatusChecker(), 1, 1);
    }

    private record BiomeRegionData(long time, BiomeRegionCreatorThread thread) {

        public BiomeRegionData(long time, BiomeRegionCreatorThread thread) {
            this.time = time;
            this.thread = thread;
        }
    }

    public class BiomeRegionStatusChecker implements Runnable {

        @Override
        public void run() {

            for (Map.Entry<User, BiomeRegionData> data : biomeRegions.entrySet()) {
                //TODO: Complete when user sessions are done
            }

        }
    }

    public static class BiomeRegionCreatorThread extends Thread {

        private final Location location;
        private BiomeRegion.BiomeRegionBuilder builder;

        public BiomeRegionCreatorThread(Location loc) {
            location = loc;
        }

        @Override
        public void run() {
            try {
                builder = new BiomeRegion.BiomeRegionBuilder(location);
            } catch (BiomeRegionException e) {
                e.printStackTrace();
            }
        }

        public boolean isDefined() {
            if (builder == null)
                return false;
            return builder.isDefined();
        }

        public BiomeRegion getRegion() throws IncompleteRegionException, BiomeRegionException {
            if (!isDefined())
                throw new IncompleteRegionException();
            if (!builder.isBuilt())
                return builder.build();
            return null;
        }

    }

    public void createBiomeRegion(User user, Location location) {
        BiomeRegionCreatorThread newThread = new BiomeRegionCreatorThread(location);
        newThread.start();
        biomeRegions.put(user, new BiomeRegionData(System.currentTimeMillis(), newThread));
    }

    public void cancelBiomeRegion(User user) {

    }

    public void cancelAll() {
        bukkitTask.cancel();
    }

}
