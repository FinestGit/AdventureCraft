package com.github.finestgit.adventurecraft.datagen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK, 4)
                .requires(ModItems.OAK_TIMBER, 2)
                .unlockedBy("has_oak_timber", has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + getItemName(Items.STICK));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.OAK_PLANKS, 1)
                .requires(ModItems.OAK_TIMBER)
                .unlockedBy("has_oak_timber", has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + getItemName(Items.OAK_PLANKS));
        createFlexibleAxeRecipe(recipeOutput, ModItems.WOOD_WOODCUTTING_AXE.get());
    }

    protected void createFlexibleAxeRecipe(RecipeOutput recipeOutput, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(" BB")
                .pattern(" SB")
                .pattern("S  ")
                .define('B', ModItems.OAK_TIMBER)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.OAK_TIMBER), has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_1");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(" BB")
                .pattern(" SB")
                .pattern(" S ")
                .define('B', ModItems.OAK_TIMBER)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.OAK_TIMBER), has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_2");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("BB ")
                .pattern("BS ")
                .pattern(" S ")
                .define('B', ModItems.OAK_TIMBER)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.OAK_TIMBER), has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_3");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("BB ")
                .pattern("BS ")
                .pattern("  S")
                .define('B', ModItems.OAK_TIMBER)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.OAK_TIMBER), has(ModItems.OAK_TIMBER))
                .save(recipeOutput, AdventureCraftMod.MODID + ":" + "flexible_" + getItemName(result) + "_pattern_4");
    }
}
