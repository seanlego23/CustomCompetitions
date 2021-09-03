package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.math.Vector3D;
import org.jetbrains.annotations.Nullable;

public class CubicRegion extends AbstractRegion {

    private Vector3D min = null;
    private Vector3D max = null;

    public CubicRegion(@Nullable World world) {
        super(world);
    }

    public CubicRegion(@Nullable World world, Vector3D minimum, Vector3D maximum) {
        super(world);
        min = minimum;
        max = maximum;
    }

    @Override
    public Vector3D getMinimum() {
        return min;
    }

    public void setMinimum(Vector3D minimum) {
        min = minimum;
    }

    @Override
    public Vector3D getMaximum() {
        return max;
    }

    public void setMaximum(Vector3D maximum) {
        max = maximum;
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
