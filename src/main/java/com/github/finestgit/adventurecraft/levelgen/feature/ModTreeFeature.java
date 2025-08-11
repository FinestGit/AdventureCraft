package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.block.ModBlocks;
import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class ModTreeFeature extends Feature<ModTreeConfiguration> {

    public ModTreeFeature(Codec<ModTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ModTreeConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        ModTreeConfiguration config = context.config();
        RandomSource random = context.random();

        int estimatedHeight = config.trunkPlacer.minTrunkHeight + random.nextInt(config.trunkPlacer.maxTrunkHeight - config.trunkPlacer.minTrunkHeight + 1);
        double estimatedRadius = Math.max(1.0, estimatedHeight / 8.0);
        List<ModBranchPlan> branchPlans = ModBranchPlanner.planBranches(random, estimatedHeight, estimatedRadius);

        ModTrunkPlacer.TrunkData trunkData = config.trunkPlacer.generateTrunk(level, random, pos, config, branchPlans);

        config.rootPlacer.generateRoots(level, random, pos, config, trunkData.height(), trunkData.radius());

        List<ModBranchPlacer.FoliageAnchor> foliageAnchors = ModBranchPlacer.generateBranches(level, random, pos, config, trunkData.height(), trunkData.radius(), branchPlans);

        for (ModBranchPlacer.FoliageAnchor anchor : foliageAnchors) {
            config.foliagePlacer.placeFoliageBlob(level, random, anchor.pos(), config.foliageProvider);
        }

        BlockPos topOfTrunk = pos.above(trunkData.height());
        int originalMin = config.foliagePlacer.minFoliageRadius;
        int originalMax = config.foliagePlacer.maxFoliageRadius;
        ModFoliagePlacer topFoliagePlacer = new ModFoliagePlacer(originalMin + 1, originalMax + 1);
        topFoliagePlacer.placeFoliageBlob(level, random, topOfTrunk, config.foliageProvider);

        return true;
    }
}
