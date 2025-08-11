package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

import java.util.List;

public class ModTrunkPlacer {
    public static final Codec<ModTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT
                            .fieldOf("min_trunk_height")
                            .forGetter(cfg -> cfg.minTrunkHeight),
                    Codec.INT
                            .fieldOf("max_trunk_height")
                            .forGetter(cfg -> cfg.maxTrunkHeight)
            ).apply(instance, ModTrunkPlacer::new)
    );
    protected final int minTrunkHeight;
    protected final int maxTrunkHeight;

    public record TrunkData(int height, double radius) {
    }

    public ModTrunkPlacer(int minTrunkHeight, int maxTrunkHeight) {
        this.minTrunkHeight = minTrunkHeight;
        this.maxTrunkHeight = maxTrunkHeight;
    }

    public TrunkData generateTrunk(
            WorldGenLevel level,
            RandomSource random,
            BlockPos pos,
            ModTreeConfiguration config,
            List<ModBranchPlan> branchPlans
    ) {
        double radiusStep = 8.0;
        double minTrunkRadius = 1.0;
        int trunkHeight = this.minTrunkHeight + random.nextInt(this.maxTrunkHeight - this.minTrunkHeight + 1);
        double trunkRadius = Math.max(minTrunkRadius, trunkHeight / radiusStep);

        for (int y = 0; y < trunkHeight; y++) {
            double taperFactor = Math.pow(1.0 - (y / (double) trunkHeight), 1.5);
            double radius = trunkRadius * taperFactor;
            if (y < 2) {
                radius += 0.5;
            }

            int finalY = y;
            boolean nearBranch = branchPlans.stream().anyMatch(plan -> Math.abs(plan.startY() - finalY) <= 1);
            if (nearBranch) {
                radius += 0.3;
            }

            double noise = (random.nextDouble() - 0.5) * 0.1;
            radius += noise;

            radius = Math.max(0.5, radius);

            int r = (int) Math.ceil(radius);
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dz * dz <= radius * radius) {
                        BlockPos logPos = pos.offset(dx, y, dz);
                        if (level.isEmptyBlock(logPos) || level.getBlockState(logPos).canBeReplaced()) {
                            level.setBlock(logPos, config.trunkProvider.getState(random, logPos), 3);
                        }
                    }
                }
            }
        }

        return new TrunkData(trunkHeight, trunkRadius);
    }
}
