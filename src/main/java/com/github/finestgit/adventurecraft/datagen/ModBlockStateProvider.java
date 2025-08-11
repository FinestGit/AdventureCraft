package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AdventureCraftMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(ModBlocks.OAK_LOG.get());
        blockItem(ModBlocks.OAK_LOG);
        saplingBlock(ModBlocks.OAK_SAPLING);
        leavesBlock(ModBlocks.OAK_LEAVES);
        logBlock(ModBlocks.COPPER_OAK_LOG.get());
        blockItem(ModBlocks.COPPER_OAK_LOG);
        saplingBlock(ModBlocks.COPPER_OAK_SAPLING);
        leavesBlock(ModBlocks.COPPER_OAK_LEAVES);
    }

    private void saplingBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                        blockTexture(block.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                        ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(block.get())).renderType("cutout"));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(AdventureCraftMod.MODID + ":block/" + deferredBlock.getId().getPath()));
    }
}
