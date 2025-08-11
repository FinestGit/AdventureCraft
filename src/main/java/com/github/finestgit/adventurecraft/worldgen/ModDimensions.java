package com.github.finestgit.adventurecraft.worldgen;

import com.github.finestgit.adventurecraft.worldgen.biome.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensions {
    public static final ResourceKey<LevelStem> OVERWORLD = ResourceKey.create(
            Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath("minecraft", "overworld")
    );

    public static final ResourceKey<DimensionType> OVERWORLD_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath("minecraft", "overworld")
    );

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettingsRegistry = context.lookup(Registries.NOISE_SETTINGS);

        Holder<DimensionType> overworldTypeHolder = dimTypes.getOrThrow(OVERWORLD_TYPE);

        Holder.Reference<Biome> overworldBiomes = biomeRegistry.getOrThrow(ModBiomes.TEST_SURFACE_BIOME);

        FixedBiomeSource fixedSource = new FixedBiomeSource(overworldBiomes);

        Holder.Reference<NoiseGeneratorSettings> overworldNoiseSettings = noiseSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD);

        NoiseBasedChunkGenerator chunkGen = new NoiseBasedChunkGenerator(fixedSource, overworldNoiseSettings);

        context.register(OVERWORLD, new LevelStem(overworldTypeHolder, chunkGen));
    }
}
