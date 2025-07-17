package com.github.finestgit.adventurecraft.item.woodcutting;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class WoodcuttingAxeItem extends Item {
    public WoodcuttingAxeItem(Properties properties) {
        super(properties);
    }

    public void assignStats() {
        AdventureCraftMod.LOGGER.debug("Creating Stats");
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide()) {
            assignStats();
        }
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Some cool text"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
