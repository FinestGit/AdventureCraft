package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.block.ModBlocks;
import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class WoodcuttingBlockLootTableProvider extends BlockLootSubProvider {
    protected WoodcuttingBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    @Override
    protected void generate() {
        add(ModBlocks.OAK_LOG.get(),
                block -> createSingleItemTable(ModItems.OAK_TIMBER));
        dropSelf(ModBlocks.OAK_SAPLING.get());
        add(ModBlocks.OAK_LEAVES.get(),
                block -> createLeavesDrops(block, ModBlocks.OAK_SAPLING.get(), 0.001F, 0.001F, 0.001F, 0.001F));
        add(ModBlocks.COPPER_OAK_LOG.get(),
                block -> createSingleItemTable(ModItems.COPPER_OAK_TIMBER));
        dropSelf(ModBlocks.COPPER_OAK_SAPLING.get());
        add(ModBlocks.COPPER_OAK_LEAVES.get(),
                block -> createLeavesDrops(block, ModBlocks.OAK_SAPLING.get(), 0.001F, 0.001F, 0.001F, 0.001F));
    }
}
