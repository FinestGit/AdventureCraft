package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.attachment.DefaultPlayerData;
import com.github.finestgit.adventurecraft.attachment.PlayerAttachement;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingSkillData;
import com.github.finestgit.adventurecraft.item.ModItems;
import com.github.finestgit.adventurecraft.item.woodcutting.WoodcuttingAxeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerSpawnEvent {
    @SubscribeEvent
    public static void onPlayerSpawnedIn(PlayerEvent.PlayerLoggedInEvent event) {
        String starterItemKey = "adventurecraft.receivedStartItem";
        Player player = event.getEntity();
        CompoundTag playerTag = player.getPersistentData();
        if (!playerTag.getBoolean(starterItemKey)) {
            ItemStack woodWoodcuttingAxe = new ItemStack(ModItems.WOOD_WOODCUTTING_AXE.get());
            ((WoodcuttingAxeItem) woodWoodcuttingAxe.getItem()).assignStats(woodWoodcuttingAxe);
            player.getInventory().add(woodWoodcuttingAxe);
            playerTag.putBoolean(starterItemKey, true);
        }

        WoodcuttingSkillData woodcuttingSkillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);
        player.sendSystemMessage(Component.literal(woodcuttingSkillData.toString()));
        player.setData(PlayerAttachement.DEFAULT_PLAYER_DATA, new DefaultPlayerData(player.getSpeed()));
    }
}
