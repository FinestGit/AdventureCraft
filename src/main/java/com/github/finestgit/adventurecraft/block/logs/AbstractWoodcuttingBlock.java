package com.github.finestgit.adventurecraft.block.logs;

import com.github.finestgit.adventurecraft.utils.LogMaterialTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractWoodcuttingBlock extends RotatedPillarBlock {
    protected final LogMaterialTier logTier;

    protected static final float BASE_SECONDS_TO_DESTROY = 8.0f;
    protected static final float SINGLE_GAME_TICK = 1.0f;
    protected static final int TICKS_PER_SECOND = 20;

    public AbstractWoodcuttingBlock(LogMaterialTier logTier, Properties properties) {
        super(properties);
        this.logTier = logTier;
    }

    public float calculateBreakTimeInSeconds(float axeSpeed, float axeTier) {
        float woodcuttingResistance = logTier.getWoodcuttingResistance();
        float expectedWoodcuttingSpeed = logTier.getExpectedWoodcuttingSpeed();

        float calculatedBreakTimeInSeconds = (woodcuttingResistance / axeSpeed) * BASE_SECONDS_TO_DESTROY;

        return calculateBreakTime(calculatedBreakTimeInSeconds);
    }

    private float calculateBreakTime(float breakTimeInSeconds) {
        float inverseSecond = SINGLE_GAME_TICK / TICKS_PER_SECOND;
        return inverseSecond / breakTimeInSeconds;
    }
}
