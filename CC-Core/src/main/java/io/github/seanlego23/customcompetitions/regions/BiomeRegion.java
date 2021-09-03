package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.Biome;
import io.github.seanlego23.customcompetitions.util.Location;
import io.github.seanlego23.customcompetitions.util.math.Vector2D;
import io.github.seanlego23.customcompetitions.util.math.Vector3D;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class BiomeRegion extends AbstractRegion {

    private final TreeMap<Integer, FlatPolygonRegion> externalBorder;
    private final TreeMap<Integer, List<FlatPolygonRegion>> internalHoles;

    private Vector3D min;
    private Vector3D max;

    private Biome biome;

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
        biome = region.biome;
        externalBorder = region.externalBorder;
        internalHoles = region.internalHoles;
        min = region.min;
        max = region.max;
    }

    private BiomeRegion(Biome type, World world, TreeMap<Integer, FlatPolygonRegion> external,
            TreeMap<Integer, List<FlatPolygonRegion>> internal, Vector3D minimum, Vector3D maximum) {
        super(world);
        biome = type;
        externalBorder = external;
        internalHoles = internal;
        min = minimum;
        max = maximum;
    }

    public static class BiomeRegionBuilder {

        private final Biome biome;
        private final World world;
        private final SortedMap<Integer, FlatPolygonRegion> external = new ConcurrentSkipListMap<>();
        private final SortedMap<Integer, List<FlatPolygonRegion>> internal = new ConcurrentSkipListMap<>();
        private final Vector3D minimum;
        private final Vector3D maximum;

        private final boolean defined;
        private boolean built = false;

        private final Object definedLock = new Object();

        private final Comparator<Vector2D> Vector2DComparator = (o1, o2) -> {
            if (o1.equals(o2))
                return 0;
            double length1 = o1.length();
            double length2 = o2.length();
            if (length1 < length2)
                return -1;
            else if (length1 > length2)
                return 1;
            double angle1 = Math.atan2(o1.getBlockZ(), o1.getBlockX());
            double angle2 = Math.atan2(o2.getBlockZ(), o2.getBlockX());
            if (angle1 < angle2)
                return -1;
            return 1;
        };

        public BiomeRegionBuilder(Location location) throws BiomeRegionException {
            world = location.world();
            biome = world.getBiome(location);

            int maxHeight = world.getMaxHeight();
            int minHeight = world.getMinHeight();
            int startY = location.getBlockY();

            final boolean[] exceptionThrown = {false};
            Thread searchHereAbove = new Thread(() -> {
                for (int i = 0; i + startY < maxHeight; i++) {
                    try {
                        if (!searchLevel(location.withY(startY + i)))
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
                       if (!searchLevel(location.withY(startY - i)))
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
            for (FlatPolygonRegion region : external.values()) {
                Vector3D min = region.getMinimum();
                if (min.getBlockX() < xMin)
                    xMin = min.getBlockX();
                if (min.getBlockZ() < zMin)
                    zMin = min.getBlockZ();
                Vector3D max = region.getMaximum();
                if (max.getBlockX() > xMax)
                    xMax = max.getBlockX();
                if (max.getBlockZ() > zMax)
                    zMax = max.getBlockZ();
            }

            minimum = new Vector3D(xMin, external.firstKey(), zMin);
            maximum = new Vector3D(xMax, external.lastKey(), zMax);

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

        public BiomeRegion build() throws BiomeRegionException/*, IncompleteRegionException*/ {
            synchronized (definedLock) {
                if (!defined)
                    throw new BiomeRegionException("Biome is not defined");
                if (built)
                    throw new BiomeRegionException("Biome has already been built.");
                built = true;
                return new BiomeRegion(biome, world, new TreeMap<>(external), new TreeMap<>(internal),
                        minimum, maximum);
            }
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean searchLevel(Location loc) throws BiomeRegionException {
            SortedSet<Vector2D> points = new ConcurrentSkipListSet<>(Vector2DComparator);
            SortedSet<Vector2D> boundaryPoints = new ConcurrentSkipListSet<>(Vector2DComparator);
            int locY = loc.getBlockY();

            int x = loc.getBlockX();
            int z = loc.getBlockZ();
            Thread searchThread1 = new Thread(() -> searchNext(points, boundaryPoints, loc.withX(x + 1), biome));
            Thread searchThread2 = new Thread(() -> searchNext(points, boundaryPoints, loc.withZ(z + 1), biome));
            Thread searchThread3 = new Thread(() -> searchNext(points, boundaryPoints, loc.withX(x - 1), biome));
            Thread searchThread4 = new Thread(() -> searchNext(points, boundaryPoints, loc.withZ(z - 1), biome));
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

            SortedSet<Vector2D> externalBoundary = new TreeSet<>(Vector2DComparator);
            List<SortedSet<Vector2D>> internalBoundaries = new ArrayList<>();
            createExternalBoundary(points.last(), boundaryPoints, externalBoundary);
            while (boundaryPoints.size() != 0) {
                SortedSet<Vector2D> internalBoundary = new TreeSet<>(Vector2DComparator);
                createInternalBoundary(boundaryPoints.last(), locY, boundaryPoints, internalBoundary,
                        Vector2DComparator);
                internalBoundaries.add(internalBoundary);
            }

            FlatPolygonRegion externalRegion = new FlatPolygonRegion(world, new ArrayList<>(externalBoundary), locY,
                    locY);
            external.put(locY, externalRegion);

            List<FlatPolygonRegion> internalHoleList = new ArrayList<>();
            for (SortedSet<Vector2D> inter : internalBoundaries)
                internalHoleList.add(new FlatPolygonRegion(world, new ArrayList<>(inter), locY, locY));
            internal.put(locY, internalHoleList);

            return true;
        }

        //Basic boundary fill algorithm modified for arbitrary boundary.
        private void searchNext(SortedSet<Vector2D> points, SortedSet<Vector2D> boundary, Location pt, Biome bt) {
            if (world.getBiome(pt) == bt) {
                if (points.add(pt.toVector3D().toVector2D())) {
                    int x = pt.getBlockX();
                    int z = pt.getBlockZ();
                    searchNext(points, boundary, pt.withX(x + 1), bt);
                    searchNext(points, boundary, pt.withZ(z + 1), bt);
                    searchNext(points, boundary, pt.withX(x - 1), bt);
                    searchNext(points, boundary, pt.withZ(z - 1), bt);
                }
            } else
                boundary.add(pt.toVector3D().toVector2D());
        }

        private void createExternalBoundary(Vector2D start, SortedSet<Vector2D> boundary,
                SortedSet<Vector2D> points) {
            /*Vector2D xDirection = start.getX() < 0 ? Direction.WEST.toBlockVector().toVector2D() :
                                      Direction.EAST.toBlockVector().toVector2D();
            Vector2D zDirection = start.getZ() < 0 ? Direction.NORTH.toBlockVector().toVector2D() :
                                      Direction.SOUTH.toBlockVector().toVector2D();
            points.add(start);

            boundary.remove(start.add(xDirection));
            boundary.remove(start.add(zDirection));
            Vector2D boundaryDirection;
            Vector2D insideDirection;
            if (boundary.contains(start.add(zDirection.multiply(-1)))) {
                insideDirection = xDirection.multiply(-1);
                boundaryDirection = zDirection;
            } else {
                insideDirection = zDirection.multiply(-1);
                boundaryDirection = xDirection;
            }
            Vector2D next = start.add(insideDirection);

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
            }*/
        }

        private void createInternalBoundary(Vector2D start, int y, SortedSet<Vector2D> boundary,
                SortedSet<Vector2D> points, Comparator<Vector2D> comp) throws BiomeRegionException {
            /*Location loc = new Location(this.world, start.getX(), y, start.getZ());
            BiomeType internalBiome = BukkitAdapter.adapt(BukkitAdapter.adapt(loc).getBlock().getBiome());
            SortedSet<Vector2D> internal = new ConcurrentSkipListSet<>(comp);
            SortedSet<Vector2D> internalBoundary = new ConcurrentSkipListSet<>(comp);

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
            boundary.removeAll(points);*/
        }

    }

    @Override
    public Vector3D getMinimum() {
        return min;
    }

    @Override
    public Vector3D getMaximum() {
        return max;
    }


    @SuppressWarnings("RedundantThrows")
    @Override
    public void expand(Vector3D... changes) {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void contract(Vector3D... changes) {
    }


/*    private static class BiomeRegionIterator implements Iterator<Vector3D> {

        private final BiomeRegion region;
        private final int maxY;
        private int posY;
        private Iterator<Vector3D> current;
        private List<FlatPolygonRegion> currentHoles;
        private Vector3D nextPoint;

        public BiomeRegionIterator(BiomeRegion region) {
            this.region = region;
            maxY = region.externalBorder.lastKey();
            posY = region.externalBorder.firstKey();

            //current = region.externalBorder.get(posY).iterator();
            currentHoles = region.internalHoles.get(posY);
            nextPoint = findNext();
        }

        @Override
        public boolean hasNext() {
            return nextPoint != null;
        }

        @Override
        public Vector3D next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Vector3D temp = nextPoint;
            nextPoint = findNext();
            return temp;
        }

        private Vector3D findNext() {
            if (!current.hasNext() && posY < maxY) {
                ++posY;
                //current = region.externalBorder.get(posY).iterator();
                currentHoles = region.internalHoles.get(posY);
            }
            if (current.hasNext()) {
                Vector3D nextTry = null;
                while (posY <= maxY) {
                    nextTry = findNextInRegion();
                    if (nextTry == null) {
                        if (posY < maxY) {
                            ++posY;
                            //current = region.externalBorder.get(posY).iterator();
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

        private Vector3D findNextInRegion() {
            Vector3D nextTry = current.next();
            while (nextTry != null) {
                for (FlatPolygonRegion rg : currentHoles) {
                    *//*if (rg.contains(nextTry)) {
                        nextTry = null;
                        break;
                    }*//*
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
    public Iterator<Vector3D> iterator() {
        return new BiomeRegionIterator(this);
    }*/

/*    @Override
    public BiomeRegion clone() {
        return (BiomeRegion)super.clone();
    }*/

    @Override
    public boolean contains(Vector3D position) {
/*        if (!position.containedWithin(min, max))
            return false;
        int posY = position.getY();
        FlatPolygonRegion region = externalBorder.get(posY);
        if (!region.contains(position))
            return false;
        List<FlatPolygonRegion> holes = internalHoles.get(posY);
        for (FlatPolygonRegion hole : holes) {
            if (hole.contains(position))
                return false;
        }*/
        return true;
    }
}
