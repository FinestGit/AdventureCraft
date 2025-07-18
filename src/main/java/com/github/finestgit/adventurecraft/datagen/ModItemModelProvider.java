package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdventureCraftMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.WOOD_WOODCUTTING_AXE.get());
        basicItem(ModItems.OAK_TIMBER.get());
    }
}
