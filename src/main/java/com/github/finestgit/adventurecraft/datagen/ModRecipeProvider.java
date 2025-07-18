package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK, 4)
                .requires(ModItems.OAK_TIMBER)
                .unlockedBy("has_oak_timber", has(ModItems.OAK_TIMBER))
                .save(recipeOutput);

        createFlexibleAxeRecipe(recipeOutput, ModItems.WOOD_WOODCUTTING_AXE.get(), ModItems.OAK_TIMBER, Items.STICK);
    }

    protected void createFlexibleAxeRecipe(RecipeOutput recipeOutput, ItemLike result, ItemLike block, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(" BB")
                .pattern(" SB")
                .pattern("S  ")
                .define('B', block)
                .define('S', stick)
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_1");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(" BB")
                .pattern(" SB")
                .pattern(" S ")
                .define('B', block)
                .define('S', stick)
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_2");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("BB ")
                .pattern("BS ")
                .pattern(" S ")
                .define('B', block)
                .define('S', stick)
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_3");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("BB ")
                .pattern("BS ")
                .pattern("  S")
                .define('B', block)
                .define('S', stick)
                .unlockedBy(getHasName(block), has(block))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_4");
    }
}
