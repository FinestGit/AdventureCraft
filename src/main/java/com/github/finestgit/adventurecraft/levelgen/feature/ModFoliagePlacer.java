package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.block.logs.LogBlock;
import com.github.finestgit.adventurecraft.datagen.ModBlockTagProvider;
import com.github.finestgit.adventurecraft.utils.WoodcuttingTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModFoliagePlacer {
    public static final Codec<ModFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT
                            .fieldOf("min_foliage_radius")
                            .forGetter(cfg -> cfg.minFoliageRadius),
                    Codec.INT
                            .fieldOf("max_foliage_radius")
                            .forGetter(cfg -> cfg.maxFoliageRadius)
            ).apply(instance, ModFoliagePlacer::new)
    );
    protected final int minFoliageRadius;
    protected final int maxFoliageRadius;

    public ModFoliagePlacer(int minFoliageRadius, int maxFoliageRadius) {
        this.minFoliageRadius = minFoliageRadius;
        this.maxFoliageRadius = maxFoliageRadius;
    }

    public void placeFoliageBlob(
            WorldGenLevel level,
            RandomSource random,
            BlockPos center,
            BlockStateProvider leavesProvider
    ) {
        int radius = this.minFoliageRadius + random.nextInt(this.maxFoliageRadius - this.minFoliageRadius + 1);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    double distSq = dx * dx + dy * dy + dz * dz;
                    if (distSq <= radius * radius) {
                        if (random.nextFloat() < 0.85f) {
                            BlockPos pos = center.offset(dx, dy, dz);
                            if (level.isEmptyBlock(pos)) {
                                var state = leavesProvider.getState(random, pos);
                                if (state.hasProperty(LeavesBlock.PERSISTENT)) {
                                    state = state.setValue(LeavesBlock.PERSISTENT, true);
                                }
                                level.setBlock(pos, state, 3);
                            }
                        }
                    }
                }
            }
        }
    }
}
