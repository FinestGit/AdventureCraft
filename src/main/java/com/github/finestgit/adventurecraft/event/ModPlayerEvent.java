package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.attachment.DefaultPlayerData;
import com.github.finestgit.adventurecraft.attachment.PlayerAttachement;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingPerks;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingSkillData;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModPlayerEvent {
    private static final int CHECK_INTERVAL_TICKS = 10;
    private static final int EFFECT_DURATION_TICKS = 15;

    @SubscribeEvent
    public static void oneWithTheTreesEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        WoodcuttingSkillData woodcuttingSkillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);
        if (!woodcuttingSkillData.hasPerk(WoodcuttingPerks.ONE_WITH_THE_TREES.getId())) {
            return;
        }
        if (level.isClientSide()) {
            return;
        }
        if (player.tickCount % CHECK_INTERVAL_TICKS != 0) {
            return;
        }

        BlockPos playerPos = player.blockPosition();
        int oneWithTheTreesRank = woodcuttingSkillData.getPerkRank(WoodcuttingPerks.ONE_WITH_THE_TREES.getId());
        int oneWithTheTreesValue = WoodcuttingPerks.ONE_WITH_THE_TREES.getEffectValue(oneWithTheTreesRank, Integer.class);
        boolean nearbyLogFound = checkBlocksForLog(level, playerPos, oneWithTheTreesValue);

        DefaultPlayerData defaultPlayerData = player.getData(PlayerAttachement.DEFAULT_PLAYER_DATA);
        if (nearbyLogFound) {
            float speedAmplifier = (float) Math.floor(oneWithTheTreesValue * defaultPlayerData.getDefaultSpeed());
            player.setSpeed(speedAmplifier);
        } else {
            player.setSpeed(defaultPlayerData.getDefaultSpeed());
        }
    }

    private static boolean checkBlocksForLog(Level level, BlockPos playerPos, int checkDistance) {
        int playerY = playerPos.getY();
        for (int x = -checkDistance; x <= checkDistance; x++) {
            for (int z = -checkDistance; z <= checkDistance; z++) {
                BlockPos currentCheckPos = new BlockPos(playerPos.getX() + x, playerY, playerPos.getZ() + z);
                BlockState state = level.getBlockState(currentCheckPos);

                if (isLogBlock(state)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLogBlock(BlockState state) {
        return state.is(BlockTags.LOGS);
    }
}
