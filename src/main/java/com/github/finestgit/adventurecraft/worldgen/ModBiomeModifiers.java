package com.github.finestgit.adventurecraft.worldgen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> REMOVE_ALL_TREES = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, "remove_all_trees")
    );

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(REMOVE_ALL_TREES, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_PLAINS),
                        placedFeatures.getOrThrow(VegetationPlacements.DARK_FOREST_VEGETATION),
                        placedFeatures.getOrThrow(VegetationPlacements.FLOWER_FOREST_FLOWERS),
                        placedFeatures.getOrThrow(VegetationPlacements.FOREST_FLOWERS),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_FLOWER_FOREST),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_MEADOW),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_CHERRY),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_TAIGA),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_GROVE),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_BADLANDS),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_SNOWY),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_SWAMP),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_WINDSWEPT_SAVANNA),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_SAVANNA),
                        placedFeatures.getOrThrow(VegetationPlacements.BIRCH_TALL),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_BIRCH),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_WINDSWEPT_FOREST),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_WINDSWEPT_HILLS),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_WATER),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_BIRCH_AND_OAK),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_SPARSE_JUNGLE),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_JUNGLE),
                        placedFeatures.getOrThrow(VegetationPlacements.BAMBOO_VEGETATION),
                        placedFeatures.getOrThrow(VegetationPlacements.MUSHROOM_ISLAND_VEGETATION),
                        placedFeatures.getOrThrow(VegetationPlacements.TREES_MANGROVE)
                ),
                Set.of(
                        GenerationStep.Decoration.RAW_GENERATION,
                        GenerationStep.Decoration.LAKES,
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                        GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        GenerationStep.Decoration.STRONGHOLDS,
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        GenerationStep.Decoration.UNDERGROUND_DECORATION,
                        GenerationStep.Decoration.FLUID_SPRINGS,
                        GenerationStep.Decoration.VEGETAL_DECORATION,
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION
                )
        ));
    }
}
