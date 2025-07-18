package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModMineEvent {
    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        Entity entity = event.getEntity();
        BlockState blockState = event.getState();
        if (entity instanceof Player player && player.getMainHandItem().isEmpty()) {
            if (event.getPosition().isPresent()) {
                float baseHarvestSpeed = 1f;
                float blockHardness = blockState.getDestroySpeed(player.level(), event.getPosition().get());
                float breakSpeed = baseHarvestSpeed / blockHardness;
                AdventureCraftMod.LOGGER.debug("Block Hardness is: {}", blockHardness);
                AdventureCraftMod.LOGGER.debug("Break Speed is: {}", breakSpeed);
                AdventureCraftMod.LOGGER.debug("Original Break Speed is: {}", event.getOriginalSpeed());
                event.setNewSpeed(breakSpeed);
            }
        }
    }

    @SubscribeEvent
    public static void onLogDestroyEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = (Level) event.getLevel();

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            AdventureCraftMod.LOGGER.debug("Fired Event on Server Side");
        }
    }
}
