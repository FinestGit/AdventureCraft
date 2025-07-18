package com.github.finestgit.adventurecraft.item.woodcutting;

import com.github.finestgit.adventurecraft.component.ModDataComponents;
import com.github.finestgit.adventurecraft.utils.ModCustomTiers;
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
import java.util.Random;

public class WoodcuttingAxeItem extends Item {
    ModCustomTiers itemTier;

    public WoodcuttingAxeItem(ModCustomTiers itemTier, Properties properties) {
        super(properties);
        this.itemTier = itemTier;
    }

    public void assignStats(ItemStack stack) {
        int maximumPossibleDurability = this.itemTier.getMaxDurability();
        int minimumPossibleDurability = this.itemTier.getMinDurability();
        Random rand = new Random();

        if (!stack.has(ModDataComponents.DURABILITY_COMPONENT)) {
            int durability = rand.nextInt((maximumPossibleDurability - minimumPossibleDurability) + 1) + minimumPossibleDurability;
            stack.set(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(durability, durability));
        }
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide()) {
            assignStats(stack);
        }
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide()) {
            damageDurability(stack, 1);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    public void damageDurability(ItemStack stack, int damageDealt) {
        if (stack.has(ModDataComponents.DURABILITY_COMPONENT)) {
            int maxDurability = stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability();
            int durability = stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).currentDurability();
            if (durability > 0) {
                int newDurability = durability - damageDealt;
                stack.set(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(newDurability, maxDurability));
            }
        }
    }

    public int getCurrentDurability(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).currentDurability();
    }

    public int getMaximumDurability(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Some cool text"));
        int maximumDurability = getMaximumDurability(stack);
        int currentDurability = getCurrentDurability(stack);
        if (getMaximumDurability(stack) > 0) {
            tooltipComponents.add(Component.literal("Durability: " + currentDurability + "/" + maximumDurability));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
