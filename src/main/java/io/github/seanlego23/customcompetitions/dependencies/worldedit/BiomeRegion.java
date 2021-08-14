package io.github.seanlego23.customcompetitions.dependencies.worldedit;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.AbstractRegion;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

public class BiomeRegion extends AbstractRegion {

    public BiomeRegion(World world, Location loc) {
        super(world);
    }

    @Override
    public BlockVector3 getMinimumPoint() {
        return null;
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        return null;
    }

    @Override
    public void expand(BlockVector3... changes) throws RegionOperationException {

    }

    @Override
    public void contract(BlockVector3... changes) throws RegionOperationException {

    }

    @Override
    public boolean contains(BlockVector3 position) {
        return false;
    }
}
