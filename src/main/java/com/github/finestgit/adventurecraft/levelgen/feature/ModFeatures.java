package com.github.finestgit.adventurecraft.levelgen.feature;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.levelgen.feature.configurations.ModTreeConfiguration;
import com.github.finestgit.adventurecraft.worldgen.ModConfiguredFeatures;
import com.github.finestgit.adventurecraft.worldgen.feature.SmallForestFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public abstract class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, AdventureCraftMod.MODID);

    public static final DeferredHolder<Feature<?>, ModTreeFeature> MOD_TREE = FEATURES.register("mod_tree", () -> new ModTreeFeature(ModTreeConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, SmallForestFeature> SMALL_FOREST_FEATURE = FEATURES.register("small_forest_feature",
            () -> new SmallForestFeature(NoneFeatureConfiguration.CODEC,
                    ModConfiguredFeatures.OAK_KEY,
                    15,
                    4,
                    8
            )
    );
}
