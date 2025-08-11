package com.github.finestgit.adventurecraft.block.logs;

import com.github.finestgit.adventurecraft.utils.LogMaterialTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class LogBlock extends AbstractWoodcuttingBlock {
    public LogBlock(LogMaterialTier logTier, Properties properties) {
        super(logTier, properties);
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        // 1.0f -> 1 Game Tick to destroy
        // 0.5f -> 2 Game Ticks to destroy
        // 0.1f -> 10 Game Ticks to destroy
        // 0.01f -> 100 Game Ticks to destroy
        // ....
        // return 0.05f / 3; confirmed with testing -> This means it takes 20 game ticks (1 sec) and if we divide that by 3 it means 3 seconds
//        return super.getDestroyProgress(state, player, level, pos);
        return calculateBreakTimeInSeconds(1, 1);
    }
}