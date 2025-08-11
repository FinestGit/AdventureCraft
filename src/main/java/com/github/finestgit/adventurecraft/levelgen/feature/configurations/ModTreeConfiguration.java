package com.github.finestgit.adventurecraft.levelgen.feature.configurations;

import com.github.finestgit.adventurecraft.levelgen.feature.ModFoliagePlacer;
import com.github.finestgit.adventurecraft.levelgen.feature.ModRootPlacer;
import com.github.finestgit.adventurecraft.levelgen.feature.ModTrunkPlacer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModTreeConfiguration implements FeatureConfiguration {
    public static final Codec<ModTreeConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.CODEC
                            .fieldOf("trunk_provider")
                            .forGetter(cfg -> cfg.trunkProvider),
                    ModTrunkPlacer.CODEC
                            .fieldOf("trunk_placer")
                            .forGetter(cfg -> cfg.trunkPlacer),
                    ModRootPlacer.CODEC
                            .fieldOf("root_placer")
                            .forGetter(cfg -> cfg.rootPlacer),
                    BlockStateProvider.CODEC
                            .fieldOf("foliage_provider")
                            .forGetter(cfg -> cfg.foliageProvider),
                    ModFoliagePlacer.CODEC
                            .fieldOf("foliage_placer")
                            .forGetter(cfg -> cfg.foliagePlacer)
            ).apply(instance, ModTreeConfiguration::new)
    );

    public final BlockStateProvider trunkProvider;
    public final ModTrunkPlacer trunkPlacer;
    public final ModRootPlacer rootPlacer;
    public final BlockStateProvider foliageProvider;
    public final ModFoliagePlacer foliagePlacer;

    protected ModTreeConfiguration(
            BlockStateProvider trunkProvider,
            ModTrunkPlacer trunkPlacer,
            ModRootPlacer rootPlacer,
            BlockStateProvider foliageProvider,
            ModFoliagePlacer foliagePlacer
    ) {
        this.trunkProvider = trunkProvider;
        this.trunkPlacer = trunkPlacer;
        this.rootPlacer = rootPlacer;
        this.foliageProvider = foliageProvider;
        this.foliagePlacer = foliagePlacer;
    }

    public static class ModTreeConfigurationBuilder {
        public final BlockStateProvider trunkProvider;
        public final ModTrunkPlacer trunkPlacer;
        public final ModRootPlacer rootPlacer;
        public final BlockStateProvider foliageProvider;
        public final ModFoliagePlacer foliagePlacer;

        public ModTreeConfigurationBuilder(
                BlockStateProvider trunkProvider,
                ModTrunkPlacer trunkPlacer,
                ModRootPlacer rootPlacer,
                BlockStateProvider foliageProvider,
                ModFoliagePlacer foliagePlacer
        ) {
            this.trunkProvider = trunkProvider;
            this.trunkPlacer = trunkPlacer;
            this.rootPlacer = rootPlacer;
            this.foliageProvider = foliageProvider;
            this.foliagePlacer = foliagePlacer;
        }

        public ModTreeConfiguration build() {
            return new ModTreeConfiguration(
                    this.trunkProvider,
                    this.trunkPlacer,
                    this.rootPlacer,
                    this.foliageProvider,
                    this.foliagePlacer
            );
        }
    }
}
