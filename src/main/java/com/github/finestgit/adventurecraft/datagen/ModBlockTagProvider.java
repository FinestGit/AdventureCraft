package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.block.ModBlocks;
import com.github.finestgit.adventurecraft.utils.WoodcuttingTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AdventureCraftMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(WoodcuttingTags.Blocks.WOODCUTTING_LOG)
                .add(ModBlocks.OAK_LOG.get())
                .add(ModBlocks.COPPER_OAK_LOG.get());

        this.tag(BlockTags.LOGS)
                .add(ModBlocks.OAK_LOG.get())
                .add(ModBlocks.COPPER_OAK_LOG.get());
    }
}
