package com.github.finestgit.adventurecraft.context;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class ModLootContext {
    public static final LootContextParam<Double> WOODCUTTING_MULTI_LOOT_PARAM = new LootContextParam<>(ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, "woodcutting_multi_loot_param"));
}
