package io.github.seanlego23.customcompetitions.regions;

import org.jetbrains.annotations.Nullable;

public abstract class AbstractRegion implements Region, Cloneable {

    protected World world;

    public AbstractRegion(@Nullable World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
