package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.math.Vector2D;
import io.github.seanlego23.customcompetitions.util.math.Vector3D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlatPolygonRegion extends AbstractRegion {

    private final List<Vector2D> points;
    private Vector2D minimum = null;
    private Vector2D maximum = null;
    private int minY;
    private int maxY;

    public FlatPolygonRegion(@Nullable World world) {
        super(world);
        points = new ArrayList<>();
    }

    public FlatPolygonRegion(@Nullable World world, List<Vector2D> points, int minY, int maxY) {
        super(world);
        this.points = points;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public Vector3D getMinimum() {
        if (minimum != null)
            return minimum.toVector3D(minY);
        return null;
    }

    @Override
    public Vector3D getMaximum() {
        if (maximum != null)
            return maximum.toVector3D(maxY);
        return null;
    }

    @Override
    public void expand(Vector3D... changes) {

    }

    @Override
    public void contract(Vector3D... changes) {

    }

    @Override
    public boolean contains(Vector3D point) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<Vector3D> iterator() {
        return null;
    }

    @Override
    protected FlatPolygonRegion clone() {
        return (FlatPolygonRegion)super.clone();
    }
}
