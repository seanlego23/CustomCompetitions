package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.math.Vector3D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EllipsoidRegion extends AbstractRegion {

    private Vector3D center;
    private Vector3D radius;

    public EllipsoidRegion() {
        super(null);
    }

    public EllipsoidRegion(@Nullable World world) {
        super(world);
    }

    public EllipsoidRegion(@Nullable World world, @NotNull Vector3D center, @NotNull Vector3D radius) {
        super(world);
        this.center = center;
        this.radius = radius;
    }

    public Vector3D getCenter() {
        return center;
    }

    public Vector3D getRadius() {
        return radius;
    }

    @Override
    public Vector3D getMinimum() {
        return null;
    }

    @Override
    public Vector3D getMaximum() {
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
}
