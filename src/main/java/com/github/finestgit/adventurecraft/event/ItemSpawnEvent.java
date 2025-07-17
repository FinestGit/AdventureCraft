package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.ModItems;
import com.github.finestgit.adventurecraft.item.woodcutting.WoodcuttingAxeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ItemSpawnEvent {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Level level = event.getLevel();
        Entity entity = event.getEntity();

        if (level.isClientSide()) {
            return;
        }

        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();

            if (stack.getItem() instanceof WoodcuttingAxeItem) {
                ((WoodcuttingAxeItem) stack.getItem()).assignStats();
            }
        }
    }
}
