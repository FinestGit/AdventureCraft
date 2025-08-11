package com.github.finestgit.adventurecraft.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SmallForestFeature extends Feature<NoneFeatureConfiguration> {
    private final ResourceKey<ConfiguredFeature<?, ?>> treeFeatureKey;
    private final int clusterSize;
    private final int minSpacing;
    private final int maxSpacing;

    public SmallForestFeature(Codec<NoneFeatureConfiguration> codec,
                              ResourceKey<ConfiguredFeature<?, ?>> treeFeatureKey,
                              int clusterSize,
                              int minSpacing,
                              int maxSpacing
    ) {
        super(codec);
        this.treeFeatureKey = treeFeatureKey;
        this.clusterSize = clusterSize;
        this.minSpacing = minSpacing;
        this.maxSpacing = maxSpacing;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos center = context.origin();

        var configuredFeature = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(treeFeatureKey)
                .orElseThrow(() -> new IllegalStateException("Missing tree feature: " + treeFeatureKey));

        List<BlockPos> placedPositions = new ArrayList<>();

        int attempts = clusterSize * 3;
        int placedCount = 0;

        for (int attempt = 0; attempt < attempts && placedCount < clusterSize; attempt++) {
            int dx = random.nextIntBetweenInclusive(minSpacing, maxSpacing) * (random.nextBoolean() ? 1 : -1);
            int dz = random.nextIntBetweenInclusive(minSpacing, maxSpacing) * (random.nextBoolean() ? 1 : -1);
            BlockPos pos = center.offset(dx, 0, dz);

            int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ());
            BlockPos surfacePos = new BlockPos(pos.getX(), surfaceY, pos.getZ());

            if (!level.getFluidState(surfacePos.below()).isEmpty()) {
                continue;
            }

            if (
                    !level.getBlockState(surfacePos.below()).is(BlockTags.DIRT) &&
                            !level.getBlockState(surfacePos.below()).is(Blocks.GRASS_BLOCK)
            ) {
                continue;
            }

            if (!isAreaFlat(level, surfacePos, 3, 1)) {
                continue;
            }

            boolean tooClose = false;

            for (BlockPos existing : placedPositions) {
                if (existing.distSqr(surfacePos) < (minSpacing * minSpacing)) {
                    tooClose = true;
                    break;
                }
            }
            if (tooClose) {
                continue;
            }

            boolean placed = configuredFeature.value().place(level, context.chunkGenerator(), random, surfacePos);

            if (placed) {
                placedPositions.add(surfacePos);
                placedCount++;
            }
        }

        return placedCount > 0;
    }

    private boolean isAreaFlat(WorldGenLevel level, BlockPos pos, int radius, int maxHeightDiff) {
        int baseY = pos.getY();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos checkPos = pos.offset(dx, 0, dz);
                int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, checkPos.getX(), checkPos.getZ());
                if (Math.abs(surfaceY - baseY) > maxHeightDiff) {
                    return false;
                }
            }
        }
        return true;
    }
}
