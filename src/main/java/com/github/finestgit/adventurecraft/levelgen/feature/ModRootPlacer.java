package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class ModRootPlacer {
    public static final Codec<ModRootPlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT
                            .fieldOf("min_root_depth")
                            .forGetter(cfg -> cfg.minRootDepth),
                    Codec.INT
                            .fieldOf("max_root_depth")
                            .forGetter(cfg -> cfg.maxRootDepth)
            ).apply(instance, ModRootPlacer::new)
    );
    protected final int minRootDepth;
    protected final int maxRootDepth;

    public ModRootPlacer(int minRootDepth, int maxRootDepth) {
        this.minRootDepth = minRootDepth;
        this.maxRootDepth = maxRootDepth;
    }

    public void generateRoots(
            WorldGenLevel level,
            RandomSource random,
            BlockPos pos,
            ModTreeConfiguration config,
            int trunkHeight,
            double trunkRadius
    ) {
        // Create at least 2 roots for our min but also attempt to create roots based on the radius
        int minRootCount = Math.max(2, (int) (trunkRadius * 1.5));
        // This allows us to get a count that is based on the trunk height but not too large.
        int baseCount = minRootCount + (trunkHeight / 5);
        // This allows us to also have some variation in different trees having more or less roots based on their size
        int variation = random.nextInt(trunkHeight / 4 + (int) trunkRadius);
        // Get a root count that feels better for all sizes of tree based on the radius of the base
        int rootCount = baseCount + variation;
        for (int i = 0; i < rootCount; i++) {
            // Determine how far out from center we should go to start a root
            double distFromCenter = Math.sqrt(random.nextFloat()) * (trunkRadius + 1);
            // Divide our radius into slices
            double sliceAngle = (Math.PI * 2) / rootCount;
            // Get the angle we are examining
            double baseAngle = i * sliceAngle;
            // Get the arc length between two slices
            double arcLength = (trunkRadius + 1) * sliceAngle;
            // Determine the min spacing between roots
            double minSpacing = Math.max(2, arcLength / 2);
            double maxOffsetFraction = (minSpacing / arcLength);
            // Get our random slice angle
            double randomSliceAngle = -sliceAngle * maxOffsetFraction + random.nextDouble() * (sliceAngle * maxOffsetFraction * 2);
            // Resulting angle
            double angle = baseAngle + randomSliceAngle;
            // Get the starting pos
            BlockPos startPos = pos.below();
            // Determine root length
            int rootLength = minRootDepth + random.nextInt(this.maxRootDepth - this.minRootDepth + 1);

            // Convert our angles to polar coordinates
            double xOffset = Math.cos(angle) * distFromCenter;
            double zOffset = Math.sin(angle) * distFromCenter;

            // Get our starting positions
            int startX = startPos.getX() + (int) Math.round(xOffset);
            int startZ = startPos.getZ() + (int) Math.round(zOffset);

            growRoot(
                    level,
                    random,
                    config,
                    angle,
                    distFromCenter,
                    startPos,
                    startX,
                    startZ,
                    startPos.getY(),
                    rootLength,
                    0,
                    -1,
                    startPos.getY()
            );
        }
    }

    private static void growRoot(
            WorldGenLevel level,
            RandomSource random,
            ModTreeConfiguration config,
            double parentAngle,
            double centerOffset,
            BlockPos startPos,
            int prevX,
            int prevZ,
            double currentY,
            int rootLength,
            int currentDepth,
            double verticalSlope,
            int surfaceY
    ) {
        // Base Case
        if (rootLength <= 0) return;

        // Convert our angles to polar coordinates
        double xOffset = Math.cos(parentAngle) * centerOffset;
        double zOffset = Math.sin(parentAngle) * centerOffset;

        // Get us a step based on our offset to continue in our direction we want
        double stepX = xOffset * currentDepth;
        double stepZ = zOffset * currentDepth;

        // Jiggle the X and Z a little so there is slight randomness
        double jiggleX = (random.nextDouble() - 0.5) * 0.3;
        double jiggleZ = (random.nextDouble() - 0.5) * 0.3;

        // Get our starting positions
        int startX = startPos.getX() + (int) Math.round(xOffset);
        int startZ = startPos.getZ() + (int) Math.round(zOffset);

        // Calculate the new x and z
        int dirX = startX + (int) Math.round(stepX + jiggleX);
        int dirZ = startZ + (int) Math.round(stepZ + jiggleZ);
        int yOffset = (int) Math.round(currentY);

        if (verticalSlope > 0 && currentY >= surfaceY) {
            verticalSlope = -Math.abs(verticalSlope);
        }

        double nextY = currentY + verticalSlope;

        // Handle gaps in the line
        handleBresenhamLine(
                level,
                random,
                config,
                prevX,
                prevZ,
                dirX,
                dirZ,
                yOffset
        );

        BlockPos rootStartPos = new BlockPos(dirX, yOffset, dirZ);
        level.setBlock(
                rootStartPos,
                config.trunkProvider.getState(random, rootStartPos)
                        .setValue(RotatedPillarBlock.AXIS, getAxisFromDirection(dirX - prevX, dirZ - prevZ)),
                3
        );

        // Logic surrounding random branching
        createOffshootRoot(
                level,
                random,
                config,
                currentDepth,
                rootLength,
                0.3,
                parentAngle,
                dirX,
                dirZ,
                (int) currentY,
                surfaceY
        );

        growRoot(
                level,
                random,
                config,
                parentAngle,
                centerOffset,
                startPos,
                dirX,
                dirZ,
                nextY,
                rootLength - 1,
                currentDepth + 1,
                verticalSlope,
                surfaceY
        );
    }

    private static void createOffshootRoot(
            WorldGenLevel level,
            RandomSource random,
            ModTreeConfiguration config,
            int currentDepth,
            int rootLength,
            double baseChance,
            double parentAngle,
            int dirX,
            int dirZ,
            int yOffset,
            int surfaceY
    ) {
        // Get current roots progress down the root
        double progress = currentDepth / (float) rootLength;
        // Bell curve chance
        double spawnChance = baseChance * Math.sin(progress * Math.PI);

        // Check if we can spawn a branch
        if (random.nextDouble() < spawnChance) {
            // Get the new angle we are going to travel in
            double offshootAngle = parentAngle + (random.nextDouble() - 0.5) * Math.toRadians(180);
            // Determine vertical slope of the offshoot
            double verticalSlope = (random.nextDouble() * 2.0) - 1.0;
            growRoot(
                    level,
                    random,
                    config,
                    offshootAngle,
                    1.0,
                    new BlockPos(dirX, yOffset, dirZ),
                    dirX,
                    dirZ,
                    yOffset,
                    rootLength - 1,
                    0,
                    verticalSlope,
                    surfaceY
            );
        }
    }

    private static void handleBresenhamLine(
            WorldGenLevel level,
            RandomSource random,
            ModTreeConfiguration config,
            int startX,
            int startZ,
            int endX,
            int endZ,
            int yOffset
    ) {
        // Calculate deltas
        int deltaX = Math.abs(endX - startX);
        int deltaZ = Math.abs(endZ - startZ);

        if (deltaX > 1 || deltaZ > 1) {
            while (startX != endX || startZ != endZ) {
                if (startX < endX) startX++;
                else if (startX > endX) startX--;
                if (startZ < endZ) startZ++;
                else if (startZ > endZ) startZ--;

                BlockPos gapBlock = new BlockPos(startX, yOffset, startZ);
                level.setBlock(
                        gapBlock,
                        config.trunkProvider.getState(random, gapBlock).setValue(
                                RotatedPillarBlock.AXIS,
                                getAxisFromDirection(endX - startX, endZ - startZ)
                        ),
                        3
                );
            }
        }
    }

    private static Direction.Axis getAxisFromDirection(double dirX, double dirZ) {
        if (Math.abs(dirX) > Math.abs(dirZ)) {
            return Direction.Axis.X;
        } else {
            return Direction.Axis.Z;
        }
    }
}
