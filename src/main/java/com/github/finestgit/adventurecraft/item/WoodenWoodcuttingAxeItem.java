package com.github.finestgit.adventurecraft.item;

import com.github.finestgit.adventurecraft.component.ModDataComponents;
import com.github.finestgit.adventurecraft.config.ModItemConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WoodenWoodcuttingAxeItem extends Item {

    public WoodenWoodcuttingAxeItem(Properties properties) {
        super(new Item.Properties().stacksTo(1));
    }

//    @Override
//    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
//        return state.is(BlockTags.LOGS);
//    }
//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level level, Player player) {
//        if (!level.isClientSide) {
//            initializeCustomStats(stack);
//        }
//        super.onCraftedBy(stack, level, player);
//    }
//
//    @Override
//    public ItemStack getDefaultInstance() {
//        ItemStack stack = super.getDefaultInstance();
//        initializeCustomStats(stack);
//        return stack;
//    }
//
//    @Override
//    public float getDestroySpeed(ItemStack stack, BlockState state) {
//        if (state.is(BlockTags.LOGS)) {
//            float baseSpeed = ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_BASE_SPEED.get().floatValue();
//            Optional<ModDataComponents.AxeStatsComponent> axeStats = Optional.ofNullable(stack.get(ModDataComponents.AXE_STATS.get()));
//            if (axeStats.isPresent()) {
//                return baseSpeed * axeStats.get().speed();
//            }
//            return baseSpeed;
//        }
//        return super.getDestroySpeed(stack, state);
//    }
//
//    @Override
//    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
//        return false;
//    }
//
//    public void initializeCustomStats(ItemStack stack) {
//        if (!stack.has(ModDataComponents.AXE_STATS.get()) || !stack.has(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get())) {
//            Random rand = new Random();
//
//            int speed = rand.nextInt(
//                    ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MAX_SPEED.get() - ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_SPEED.get() + 1
//            ) + ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_SPEED.get();
//
//            double multi = ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_MULTI.get() +
//                    (ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MAX_MULTI.get() - ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_MULTI.get()) * rand.nextDouble();
//            multi = Math.round(multi * 100.0) / 100.0;
//
//            double wisdom = ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_WISDOM.get() +
//                    (ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MAX_WISDOM.get() - ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_WISDOM.get()) * rand.nextDouble();
//            wisdom = Math.round(wisdom * 100.0) / 100.0;
//
//            int durability = rand.nextInt(
//                    ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MAX_DURABILITY.get() - ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_DURABILITY.get() + 1
//            ) + ModItemConfigs.CLIENT.WOODEN_WOODCUTTING_AXE_MIN_DURABILITY.get();
//
//            stack.set(ModDataComponents.AXE_STATS.get(), new ModDataComponents.AxeStatsComponent(speed, multi, wisdom));
//            stack.set(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get(), new ModDataComponents.CustomDurabilityComponent(durability, durability));
//
//            stack.setDamageValue(0);
//        }
//    }
//
//    @Override
//    public boolean isDamageable(ItemStack stack) {
//        return stack.has(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get()) &&
//                stack.getOrDefault(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get(), new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability() > 0;
//    }
//
//    @Override
//    public int getMaxDamage(ItemStack stack) {
//        return stack.getOrDefault(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get(), new ModDataComponents.CustomDurabilityComponent(0, 0)).maxDurability();
//    }
//
//    @Override
//    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
//        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0f) {
//            Optional<ModDataComponents.CustomDurabilityComponent> durabilityComponentOpt = Optional.ofNullable(stack.get(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get()));
//            if (durabilityComponentOpt.isPresent()) {
//                ModDataComponents.CustomDurabilityComponent currentDurabilityComponent = durabilityComponentOpt.get();
//                int currentDurability = currentDurabilityComponent.currentDurability();
//                int maxDurability = currentDurabilityComponent.maxDurability();
//
//                if (currentDurability > 0) {
//                    currentDurability--;
//
//                    stack.set(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get(), new ModDataComponents.CustomDurabilityComponent(currentDurability, maxDurability));
//
//                    if (maxDurability > 0) {
//                        stack.setDamageValue(maxDurability - currentDurability);
//                    } else {
//                        stack.setDamageValue(0);
//                    }
//
//                    if (currentDurability <= 0) {
//                        stack.shrink(1);
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        tooltipComponents.add(Component.literal("Speed: " + getSpeed(stack)));
//        tooltipComponents.add(Component.literal("Multi: " + getMulti(stack)));
//        tooltipComponents.add(Component.literal("Wisdom: " + getWisdom(stack)));
//        tooltipComponents.add(Component.literal("Durability: " + getCurrentDurability(stack)));
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//    }
//
//    public int getSpeed(ItemStack stack) {
//        return stack.getOrDefault(ModDataComponents.AXE_STATS.get(), new ModDataComponents.AxeStatsComponent(0, 0, 0)).speed();
//    }
//
//    public double getMulti(ItemStack stack) {
//        return stack.getOrDefault(ModDataComponents.AXE_STATS.get(), new ModDataComponents.AxeStatsComponent(0, 0, 0)).multi();
//    }
//
//    public double getWisdom(ItemStack stack) {
//        return stack.getOrDefault(ModDataComponents.AXE_STATS.get(), new ModDataComponents.AxeStatsComponent(0, 0, 0)).wisdom();
//    }
//
//    public int getCurrentDurability(ItemStack stack) {
//        return stack.getOrDefault(ModDataComponents.CUSTOM_DURABILITY_COMPONENT.get(), new ModDataComponents.CustomDurabilityComponent(0 ,0)).currentDurability();
//    }
}
