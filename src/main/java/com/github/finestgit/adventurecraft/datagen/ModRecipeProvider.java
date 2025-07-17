package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOOD_WOODCUTTING_AXE.get())
                .pattern(" BB")
                .pattern(" SB")
                .pattern("S  ")
                .define('B', Items.OAK_LOG)
                .define('S', Items.STICK)
                .unlockedBy("has_oak_logs", has(Items.OAK_LOG))
                .save(recipeOutput);
    }
}
