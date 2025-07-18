package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.ModItems;
import com.github.finestgit.adventurecraft.loot.LootReplacementModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AdventureCraftMod.MODID);
    }

    @Override
    protected void start() {
        this.add("oak_timber_to_oak_log",
                new LootReplacementModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.OAK_LOG).build(),
                }, ModItems.OAK_TIMBER.get(), Items.OAK_LOG));
    }
}
