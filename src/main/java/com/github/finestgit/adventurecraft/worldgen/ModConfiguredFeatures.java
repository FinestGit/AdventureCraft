package com.github.finestgit.adventurecraft.worldgen;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.block.ModBlocks;
import com.github.finestgit.adventurecraft.levelgen.feature.ModFeatures;
import com.github.finestgit.adventurecraft.levelgen.feature.ModFoliagePlacer;
import com.github.finestgit.adventurecraft.levelgen.feature.ModRootPlacer;
import com.github.finestgit.adventurecraft.levelgen.feature.ModTrunkPlacer;
import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_KEY = registerKey("oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_SMALL_FOREST_KEY = registerKey("oak_small_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_OAK_KEY = registerKey("copper_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MATURE_COPPER_OAK_KEY = registerKey("mature_copper_oak");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, OAK_KEY, ModFeatures.MOD_TREE.get(), new ModTreeConfiguration.ModTreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.OAK_LOG.get()),
                new ModTrunkPlacer(7, 12),
                new ModRootPlacer(7, 12),
                BlockStateProvider.simple(ModBlocks.OAK_LEAVES.get()),
                new ModFoliagePlacer(2, 3)
        ).build());

        register(context, OAK_SMALL_FOREST_KEY, ModFeatures.SMALL_FOREST_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);

        register(context, COPPER_OAK_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.COPPER_OAK_LOG.get()),
                new FancyTrunkPlacer(6, 4, 4),
                BlockStateProvider.simple(ModBlocks.COPPER_OAK_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), 3),
                new TwoLayersFeatureSize(4, 2, 4))
                .build()
        );

        register(context, MATURE_COPPER_OAK_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.COPPER_OAK_LOG.get()),
                new FancyTrunkPlacer(10, 8, 8),
                BlockStateProvider.simple(ModBlocks.COPPER_OAK_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), 3),
                new TwoLayersFeatureSize(8, 6, 8))
                .build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key,
                                                                                          F feature,
                                                                                          FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
