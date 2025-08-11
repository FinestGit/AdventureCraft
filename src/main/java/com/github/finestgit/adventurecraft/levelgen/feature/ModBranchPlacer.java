package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.ArrayList;
import java.util.List;

public class ModBranchPlacer {
    public record FoliageAnchor(BlockPos pos) {
    }

    public static List<FoliageAnchor> generateBranches(
            WorldGenLevel level,
            RandomSource random,
            BlockPos trunkBase,
            ModTreeConfiguration config,
            int trunkHeight,
            double trunkRadius,
            List<ModBranchPlan> branchPlans
    ) {
        List<FoliageAnchor> anchors = new ArrayList<>();

        for (ModBranchPlan plan : branchPlans) {
            int startY = plan.startY();

            double trunkRadiusAtHeight = trunkRadius * (1.0 - (startY / (double) trunkHeight));
            if (startY < 2) {
                trunkRadiusAtHeight += 0.5;
            }

            double branchBaseRadius = Math.max(0.5, trunkRadiusAtHeight * 0.6);

            int branchLength = Math.max(3, trunkHeight / 3 + random.nextInt(2));
            int slope = 1 + random.nextInt(2);

            BlockPos branchStart = trunkBase.above(startY);

            List<BlockPos> tips = growBranch(
                    level, random, branchStart,
                    plan.dirX(), plan.dirZ(), branchLength,
                    slope, branchBaseRadius, config, 0,
                    trunkHeight,
                    trunkBase.getY()
            );
            for (BlockPos tip : tips) {
                anchors.add(new FoliageAnchor(tip));
            }
        }

        return anchors;
    }

    private static List<BlockPos> growBranch(
            WorldGenLevel level,
            RandomSource random,
            BlockPos start,
            double dirX,
            double dirZ,
            int length,
            int slope,
            double baseRadius,
            ModTreeConfiguration config,
            int depth,
            int trunkHeight,
            int trunkBaseY
    ) {
        List<BlockPos> tips = new ArrayList<>();
        BlockPos lastPos = start;

        if (depth > 2 || length < 3) {
            tips.add(start);
            return tips;
        }

        for (int step = 0; step < length; step++) {
            double taperFactor = Math.pow(1.0 - (step / (double) length), 1.5);
            double radius = baseRadius * taperFactor;
            radius += (random.nextDouble() - 0.5) * 0.1;
            radius = Math.max(0.5, radius);

            int r = (int) Math.ceil(radius);

            int x = (int) Math.round(dirX * step);
            int z = (int) Math.round(dirZ * step);
            int y = (int) Math.round(step / (double) slope);

            BlockPos centerPos = start.offset(x, y, z);
            lastPos = centerPos;

            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dz * dz <= radius * radius) {
                        BlockPos logPos = centerPos.offset(dx, 0, dz);
                        if (level.isEmptyBlock(logPos) || level.getBlockState(logPos).canBeReplaced()) {
                            level.setBlock(
                                    logPos,
                                    config.trunkProvider.getState(random, logPos)
                                            .setValue(RotatedPillarBlock.AXIS, getAxisFromDirection(dirX, dirZ)),
                                    3
                            );
                        }
                    }
                }
            }

            if (step > length / 3 && random.nextFloat() < 0.1f) {
                double angle = (random.nextDouble() - 0.5) * Math.PI / 2;
                double newDirX = dirX * Math.cos(angle) - dirZ * Math.sin(angle);
                double newDirZ = dirX * Math.sin(angle) + dirZ * Math.cos(angle);
                tips.addAll(growBranch(level, random, centerPos, newDirX, newDirZ,
                        length / 2, slope, radius * 0.6, config,
                        depth + 1, trunkHeight, trunkBaseY
                ));
            }
        }

        BlockPos adjustedTip = lastPos;

        int minFoliageY = trunkBaseY + Math.max((int) (trunkHeight * 0.5), 2);

        if (adjustedTip.getY() < minFoliageY) {
            adjustedTip = new BlockPos(adjustedTip.getX(), minFoliageY, adjustedTip.getZ());
        }

        if (config.foliagePlacer.minFoliageRadius > 2) {
            adjustedTip = adjustedTip.above(1);
        }

        tips.add(adjustedTip);

        return tips;
    }

    private static Direction.Axis getAxisFromDirection(double dirX, double dirZ) {
        if (Math.abs(dirX) > Math.abs(dirZ)) {
            return Direction.Axis.X;
        } else {
            return Direction.Axis.Z;
        }
    }
}
