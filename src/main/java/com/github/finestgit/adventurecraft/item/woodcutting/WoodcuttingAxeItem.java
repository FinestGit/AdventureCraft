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
        Random rand = new Random();
        if (!stack.has(ModDataComponents.DURABILITY_COMPONENT)) {
            assignDurability(stack, rand);
        }

        if (!stack.has(ModDataComponents.TOOL_STATS_COMPONENT)) {
            int speed = getRandomSpeedValue(rand);
            double multi = getRandomMultiValue(rand);
            double wisdom = getRandomWisdomValue(rand);
            stack.set(ModDataComponents.TOOL_STATS_COMPONENT, new ModDataComponents.ToolStatsComponent(speed, multi, wisdom));
        }
    }

    public void assignDurability(ItemStack stack, Random randomizer) {
        int maximumPossibleDurability = this.itemTier.getMaxDurability();
        int minimumPossibleDurability = this.itemTier.getMinDurability();

        int durability = randomizer.nextInt((maximumPossibleDurability - minimumPossibleDurability) + 1) + minimumPossibleDurability;
        stack.set(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(durability, durability));
    }

    private int getRandomSpeedValue(Random randomizer) {
        int maxSpeed = this.itemTier.getMaxSpeed();
        int minSpeed = this.itemTier.getMinSpeed();

        return randomizer.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
    }

    private double getRandomMultiValue(Random randomizer) {
        double maxMulti = this.itemTier.getMaxMulti();
        double minMulti = this.itemTier.getMinMulti();

        double randomMulti = minMulti + (maxMulti - minMulti) * randomizer.nextDouble();

        return Math.round(randomMulti * 100.0) / 100.0;
    }

    private double getRandomWisdomValue(Random randomizer) {
        double maxWisdom = this.itemTier.getMaxWisdom();
        double minWisdom = this.itemTier.getMinWisdom();

        double randomWisdom = minWisdom + (maxWisdom - minWisdom) * randomizer.nextDouble();

        return Math.round(randomWisdom * 100.0) / 100.0;
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

    private void damageDurability(ItemStack stack, int damageDealt) {
        if (stack.has(ModDataComponents.DURABILITY_COMPONENT)) {
            int maxDurability = stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability();
            int durability = stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).currentDurability();
            if (durability > 0) {
                int newDurability = durability - damageDealt;
                stack.set(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(newDurability, maxDurability));
            }
        }
    }

    public ModCustomTiers getTier() {
        return this.itemTier;
    }

    public int getCurrentDurability(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).currentDurability();
    }

    public int getMaximumDurability(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.DURABILITY_COMPONENT, new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability();
    }

    public int getSpeed(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.TOOL_STATS_COMPONENT, new ModDataComponents.ToolStatsComponent(0, 0, 0)).speed();
    }

    public double getMulti(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.TOOL_STATS_COMPONENT, new ModDataComponents.ToolStatsComponent(0, 0, 0)).multi();
    }

    public double getWisdom(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.TOOL_STATS_COMPONENT, new ModDataComponents.ToolStatsComponent(0, 0, 0)).wisdom();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        appendDurabilityHoverText(stack, tooltipComponents);
        appendSpeedHoverText(stack, tooltipComponents, tooltipFlag);
        appendMultiHoverText(stack, tooltipComponents, tooltipFlag);
        appendWisdomHoverText(stack, tooltipComponents, tooltipFlag);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private void appendDurabilityHoverText(ItemStack stack, List<Component> tooltipComponents) {
        int maximumDurability = getMaximumDurability(stack);
        int currentDurability = getCurrentDurability(stack);
        if (maximumDurability > 0) {
            tooltipComponents.add(Component.literal("Durability: " + currentDurability + "/" + maximumDurability));
        }
    }

    private void appendSpeedHoverText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int speed = getSpeed(stack);
        if (speed > 0) {
            tooltipComponents.add(Component.literal("Speed: " + speed));
            if (tooltipFlag.hasShiftDown()) {
                tooltipComponents.add(Component.literal("Speed effects how fast you can cut logs"));
            } else {
                tooltipComponents.add(Component.literal("<Shift>"));
            }
        }
    }

    private void appendMultiHoverText(ItemStack stack, List<Component> tooltipComponent, TooltipFlag tooltipFlag) {
        double multi = getMulti(stack);
        if (multi > 0.0f) {
            tooltipComponent.add(Component.literal("Multi: " + multi));
            if (tooltipFlag.hasShiftDown()) {
                tooltipComponent.add(Component.literal("Multi multiplies the amount of items dropped from cutting logs"));
            } else {
                tooltipComponent.add(Component.literal("<Shift>"));
            }
        }
    }

    private void appendWisdomHoverText(ItemStack stack, List<Component> tooltipComponent, TooltipFlag tooltipFlag) {
        double wisdom = getWisdom(stack);
        if (wisdom > 0.0f) {
            tooltipComponent.add(Component.literal("Wisdom: " + wisdom));
            if (tooltipFlag.hasShiftDown()) {
                tooltipComponent.add(Component.literal("Wisdom effects how much woodcutting experience you receiving from cutting logs"));
            } else {
                tooltipComponent.add(Component.literal("<Shift>"));
            }
        }
    }
}
