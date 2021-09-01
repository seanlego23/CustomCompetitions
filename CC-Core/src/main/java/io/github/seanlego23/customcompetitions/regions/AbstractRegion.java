package io.github.seanlego23.customcompetitions.regions;

import org.jetbrains.annotations.Nullable;

public abstract class AbstractRegion implements Region {

    protected World world;

    public AbstractRegion(@Nullable World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
