package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
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
    public static void onHandBreakEvent(PlayerEvent.BreakSpeed event) {
        Entity entity = event.getEntity();
        BlockState blockState = event.getState();
        if (entity instanceof Player player) {
            if (player.getMainHandItem().isEmpty()) {
                // Disable the ability to mine anything by hand
                event.setNewSpeed(0);
            }
        }
    }
}
