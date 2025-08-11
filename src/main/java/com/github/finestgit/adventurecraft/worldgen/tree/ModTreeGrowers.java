package com.github.finestgit.adventurecraft.worldgen.tree;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower OAK = new TreeGrower(AdventureCraftMod.MODID + ":oak",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.OAK_KEY),
            Optional.empty());

    public static final TreeGrower COPPER_OAK = new TreeGrower(AdventureCraftMod.MODID + ":copper_oak",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.COPPER_OAK_KEY),
            Optional.empty());

    public static final TreeGrower MATURE_COPPER_OAK = new TreeGrower(AdventureCraftMod.MODID + ":mature_copper_oak",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.MATURE_COPPER_OAK_KEY),
            Optional.empty());
}
