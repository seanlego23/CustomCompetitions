package io.github.seanlego23.customcompetitions.regions;

import io.github.seanlego23.customcompetitions.util.math.Vector3D;

public interface Region extends Iterable<Vector3D> {

    World getWorld();

    Vector3D getMinimum();

    Vector3D getMaximum();

    void expand(Vector3D... changes);

    void contract(Vector3D... changes);

    boolean contains(Vector3D point);

}
