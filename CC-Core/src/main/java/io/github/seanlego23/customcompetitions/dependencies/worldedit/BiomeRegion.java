package io.github.seanlego23.customcompetitions.dependencies.worldedit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.AbstractRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.util.Direction;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.biome.BiomeType;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class BiomeRegion extends AbstractRegion {

    private final TreeMap<Integer, Polygonal2DRegion> externalBorder;
    private final TreeMap<Integer, List<Polygonal2DRegion>> internalHoles;

    private BlockVector3 min;
    private BlockVector3 max;

    private BiomeType biomeType;

    public BiomeRegion() {
        super(null);
        externalBorder = new TreeMap<>();
        internalHoles = new TreeMap<>();
    }

    public BiomeRegion(World world) {
        super(world);
        externalBorder = new TreeMap<>();
        internalHoles = new TreeMap<>();
    }

    public BiomeRegion(BiomeRegion region) {
        super(region.world);
        biomeType = region.biomeType;
        externalBorder = region.externalBorder;
        internalHoles = region.internalHoles;
        min = region.min;
        max = region.max;
    }

    private BiomeRegion(BiomeType type, World world, TreeMap<Integer, Polygonal2DRegion> external,
            TreeMap<Integer, List<Polygonal2DRegion>> internal, BlockVector3 minimum, BlockVector3 maximum) {
        super(world);
        biomeType = type;
        externalBorder = external;
        internalHoles = internal;
        min = minimum;
        max = maximum;
    }

    public static class BiomeRegionBuilder {

        private final BiomeType biomeType;
        private final World world;
        private final SortedMap<Integer, Polygonal2DRegion> external = new ConcurrentSkipListMap<>();
        private final SortedMap<Integer, List<Polygonal2DRegion>> internal = new ConcurrentSkipListMap<>();
        private final BlockVector3 minimum;
        private final BlockVector3 maximum;

        private final boolean defined;
        private boolean built = false;

        private final Object definedLock = new Object();

        private final Comparator<BlockVector2> blockVector2Comparator = (o1, o2) -> {
            if (o1.equals(o2))
                return 0;
            double length1 = o1.length();
            double length2 = o2.length();
            if (length1 < length2)
                return -1;
            else if (length1 > length2)
                return 1;
            double angle1 = Math.atan2(o1.getZ(), o1.getX());
            double angle2 = Math.atan2(o2.getZ(), o2.getX());
            if (angle1 < angle2)
                return -1;
            return 1;
        };

        public BiomeRegionBuilder(Location location) throws BiomeRegionException {
            world = (World)location.getExtent();
            biomeType = BukkitAdapter.adapt(BukkitAdapter.adapt(location).getBlock().getBiome());

            org.bukkit.World bukkitWorld = BukkitAdapter.adapt(world);
            int maxHeight = bukkitWorld.getMaxHeight();
            int minHeight = bukkitWorld.getMinHeight();
            int startY = location.getBlockY();

            final boolean[] exceptionThrown = {false};
            Thread searchHereAbove = new Thread(() -> {
                for (int i = 0; i + startY < maxHeight; i++) {
                    try {
                        if (!searchLevel(location.setY(startY + i)))
                            break;
                    } catch (BiomeRegionException e) {
                        exceptionThrown[0] = true;
                        break;
                    }
                }
            });
            Thread searchBelow = new Thread(() -> {
               for (int i = 1; startY - i >= minHeight; i++) {
                   try {
                       if (!searchLevel(location.setY(startY - i)))
                           break;
                   } catch (BiomeRegionException e) {
                       exceptionThrown[0] = true;
                       break;
                   }
               }
            });
            searchHereAbove.start();
            searchBelow.start();
            try {
                searchHereAbove.join();
                if (exceptionThrown[0]) {
                    searchBelow.interrupt();
                    throw new BiomeRegionException("Biome search failed.");
                } else {
                    searchBelow.join();
                    if (exceptionThrown[0])
                        throw new BiomeRegionException("Biome search failed.");
                }
            } catch (InterruptedException e) {
                throw new BiomeRegionException("Biome search failed.");
            }

            int xMin = Integer.MAX_VALUE;
            int zMin = Integer.MAX_VALUE;
            int xMax = Integer.MIN_VALUE;
            int zMax = Integer.MIN_VALUE;
            for (Polygonal2DRegion region : external.values()) {
                BlockVector3 min = region.getMinimumPoint();
                if (min.getBlockX() < xMin)
                    xMin = min.getBlockX();
                if (min.getBlockZ() < zMin)
                    zMin = min.getBlockZ();
                BlockVector3 max = region.getMaximumPoint();
                if (max.getBlockX() > xMax)
                    xMax = max.getBlockX();
                if (max.getBlockZ() > zMax)
                    zMax = max.getBlockZ();
            }

            minimum = BlockVector3.at(xMin, external.firstKey(), zMin);
            maximum = BlockVector3.at(xMax, external.lastKey(), zMax);

            synchronized (definedLock) {
                defined = true;
            }
        }

        public boolean isDefined() {
            synchronized (definedLock) {
                return defined;
            }
        }

        public boolean isBuilt() {
            return built;
        }

        public BiomeRegion build() throws BiomeRegionException, IncompleteRegionException {
            synchronized (definedLock) {
                if (!defined)
                    throw new IncompleteRegionException();
                if (built)
                    throw new BiomeRegionException("Biome has already been built.");
                built = true;
                return new BiomeRegion(biomeType, world, new TreeMap<>(external), new TreeMap<>(internal),
                        minimum, maximum);
            }
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean searchLevel(Location loc) throws BiomeRegionException {
            SortedSet<BlockVector2> points = new ConcurrentSkipListSet<>(blockVector2Comparator);
            SortedSet<BlockVector2> boundaryPoints = new ConcurrentSkipListSet<>(blockVector2Comparator);
            int locY = loc.getBlockY();

            int x = loc.getBlockX();
            int z = loc.getBlockZ();
            Thread searchThread1 = new Thread(() -> searchNext(points, boundaryPoints, loc.setX(x + 1), biomeType));
            Thread searchThread2 = new Thread(() -> searchNext(points, boundaryPoints, loc.setZ(z + 1), biomeType));
            Thread searchThread3 = new Thread(() -> searchNext(points, boundaryPoints, loc.setX(x - 1), biomeType));
            Thread searchThread4 = new Thread(() -> searchNext(points, boundaryPoints, loc.setZ(z - 1), biomeType));
            searchThread1.start();
            searchThread2.start();
            searchThread3.start();
            searchThread4.start();
            try {
                searchThread1.join();
                searchThread2.join();
                searchThread3.join();
                searchThread4.join();
            } catch (InterruptedException e) {
                throw new BiomeRegionException("Could not complete biome search.", e);
            }

            if (points.size() == 0)
                return false;

            SortedSet<BlockVector2> externalBoundary = new TreeSet<>(blockVector2Comparator);
            List<SortedSet<BlockVector2>> internalBoundaries = new ArrayList<>();
            createExternalBoundary(points.last(), boundaryPoints, externalBoundary);
            while (boundaryPoints.size() != 0) {
                SortedSet<BlockVector2> internalBoundary = new TreeSet<>(blockVector2Comparator);
                createInternalBoundary(boundaryPoints.last(), locY, boundaryPoints, internalBoundary,
                        blockVector2Comparator);
                internalBoundaries.add(internalBoundary);
            }

            Polygonal2DRegion externalRegion = new Polygonal2DRegion(this.world, new ArrayList<>(externalBoundary),
                    locY, locY);
            external.put(locY, externalRegion);

            List<Polygonal2DRegion> internalHoleList = new ArrayList<>();
            for (SortedSet<BlockVector2> inter : internalBoundaries)
                internalHoleList.add(new Polygonal2DRegion(this.world, new ArrayList<>(inter), locY, locY));
            internal.put(locY, internalHoleList);

            return true;
        }

        //Basic boundary fill algorithm modified for arbitrary boundary.
        private void searchNext(SortedSet<BlockVector2> points, SortedSet<BlockVector2> boundary, Location pt,
                BiomeType bt) {
            if (BukkitAdapter.adapt(BukkitAdapter.adapt(pt).getBlock().getBiome()).equals(bt)) {
                if (points.add(pt.toVector().toVector2().toBlockPoint())) {
                    int x = pt.getBlockX();
                    int z = pt.getBlockZ();
                    searchNext(points, boundary, pt.setX(x + 1), bt);
                    searchNext(points, boundary, pt.setZ(z + 1), bt);
                    searchNext(points, boundary, pt.setX(x - 1), bt);
                    searchNext(points, boundary, pt.setZ(z - 1), bt);
                }
            } else
                boundary.add(pt.toVector().toVector2().toBlockPoint());
        }

        private void createExternalBoundary(BlockVector2 start, SortedSet<BlockVector2> boundary,
                SortedSet<BlockVector2> points) {
            BlockVector2 xDirection = start.getX() < 0 ? Direction.WEST.toBlockVector().toBlockVector2() :
                                      Direction.EAST.toBlockVector().toBlockVector2();
            BlockVector2 zDirection = start.getZ() < 0 ? Direction.NORTH.toBlockVector().toBlockVector2() :
                                      Direction.SOUTH.toBlockVector().toBlockVector2();
            points.add(start);

            boundary.remove(start.add(xDirection));
            boundary.remove(start.add(zDirection));
            BlockVector2 boundaryDirection;
            BlockVector2 insideDirection;
            if (boundary.contains(start.add(zDirection.multiply(-1)))) {
                insideDirection = xDirection.multiply(-1);
                boundaryDirection = zDirection;
            } else {
                insideDirection = zDirection.multiply(-1);
                boundaryDirection = xDirection;
            }
            BlockVector2 next = start.add(insideDirection);

            while (!points.contains(next)) {
                if (boundary.remove(next.add(boundaryDirection))) {
                    if (boundary.remove(next.add(insideDirection))) {
                        boundaryDirection = insideDirection;
                        insideDirection = insideDirection.transform2D(-90, 0, 0, 0, 0);
                    }
                } else {
                    boundaryDirection = insideDirection.multiply(-1);
                    insideDirection = insideDirection.transform2D(90, 0, 0, 0, 0);
                }
                points.add(next);
                next = next.add(insideDirection);
            }
        }

        private void createInternalBoundary(BlockVector2 start, int y, SortedSet<BlockVector2> boundary,
                SortedSet<BlockVector2> points, Comparator<BlockVector2> comp) throws BiomeRegionException {
            Location loc = new Location(this.world, start.getX(), y, start.getZ());
            BiomeType internalBiome = BukkitAdapter.adapt(BukkitAdapter.adapt(loc).getBlock().getBiome());
            SortedSet<BlockVector2> internal = new ConcurrentSkipListSet<>(comp);
            SortedSet<BlockVector2> internalBoundary = new ConcurrentSkipListSet<>(comp);

            int x = start.getBlockX();
            int z = start.getBlockZ();
            Thread searchThread1 = new Thread(() -> searchNext(internal, internalBoundary, loc.setX(x + 1), internalBiome));
            Thread searchThread2 = new Thread(() -> searchNext(internal, internalBoundary, loc.setZ(z + 1), internalBiome));
            Thread searchThread3 = new Thread(() -> searchNext(internal, internalBoundary, loc.setX(x - 1), internalBiome));
            Thread searchThread4 = new Thread(() -> searchNext(internal, internalBoundary, loc.setZ(z - 1), internalBiome));
            searchThread1.start();
            searchThread2.start();
            searchThread3.start();
            searchThread4.start();
            try {
                searchThread1.join();
                searchThread2.join();
                searchThread3.join();
                searchThread4.join();
            } catch (InterruptedException e) {
                throw new BiomeRegionException("Could not complete biome search.", e);
            }

            createExternalBoundary(points.last(), internalBoundary, points);
            boundary.removeAll(points);
        }

    }

    @Override
    public BlockVector3 getMinimumPoint() {
        return min;
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        return max;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void expand(BlockVector3... changes) throws RegionOperationException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void contract(BlockVector3... changes) throws RegionOperationException {
    }

    private static class BiomeRegionIterator implements Iterator<BlockVector3> {

        private final BiomeRegion region;
        private final int maxY;
        private int posY;
        private Iterator<BlockVector3> current;
        private List<Polygonal2DRegion> currentHoles;
        private BlockVector3 nextPoint;

        public BiomeRegionIterator(BiomeRegion region) {
            this.region = region;
            maxY = region.externalBorder.lastKey();
            posY = region.externalBorder.firstKey();

            current = region.externalBorder.get(posY).iterator();
            currentHoles = region.internalHoles.get(posY);
            nextPoint = findNext();
        }

        @Override
        public boolean hasNext() {
            return nextPoint != null;
        }

        @Override
        public BlockVector3 next() {
            if (!hasNext())
                throw new NoSuchElementException();

            BlockVector3 temp = nextPoint;
            nextPoint = findNext();
            return temp;
        }

        private BlockVector3 findNext() {
            if (!current.hasNext() && posY < maxY) {
                ++posY;
                current = region.externalBorder.get(posY).iterator();
                currentHoles = region.internalHoles.get(posY);
            }
            if (current.hasNext()) {
                BlockVector3 nextTry = null;
                while (posY <= maxY) {
                    nextTry = findNextInRegion();
                    if (nextTry == null) {
                        if (posY < maxY) {
                            ++posY;
                            current = region.externalBorder.get(posY).iterator();
                            currentHoles = region.internalHoles.get(posY);
                        } else
                            break;
                    } else
                        break;
                }
                return nextTry;
            }
            return null;
        }

        private BlockVector3 findNextInRegion() {
            BlockVector3 nextTry = current.next();
            while (nextTry != null) {
                for (Polygonal2DRegion rg : currentHoles) {
                    if (rg.contains(nextTry)) {
                        nextTry = null;
                        break;
                    }
                }
                if (nextTry == null) {
                    if (current.hasNext())
                        nextTry = current.next();
                } else
                    break;
            }
            return nextTry;
        }

    }

    @Override
    public Iterator<BlockVector3> iterator() {
        return new BiomeRegionIterator(this);
    }

    @Override
    public BiomeRegion clone() {
        return (BiomeRegion)super.clone();
    }

    @Override
    public boolean contains(BlockVector3 position) {
        if (!position.containedWithin(min, max))
            return false;
        int posY = position.getY();
        Polygonal2DRegion region = externalBorder.get(posY);
        if (!region.contains(position))
            return false;
        List<Polygonal2DRegion> holes = internalHoles.get(posY);
        for (Polygonal2DRegion hole : holes) {
            if (hole.contains(position))
                return false;
        }
        return true;
    }
}
