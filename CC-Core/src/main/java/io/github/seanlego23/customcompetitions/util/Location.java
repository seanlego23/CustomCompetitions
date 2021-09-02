package io.github.seanlego23.customcompetitions.util;

import io.github.seanlego23.customcompetitions.regions.World;

public record Location(World world, double x, double y, double z, float yaw, float pitch) {

}
