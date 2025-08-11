package com.github.finestgit.adventurecraft.utils;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WoodcuttingTags {
    public static class Blocks {
        public static final TagKey<Block> WOODCUTTING_LOG = createTag("woodcutting_log");

        public static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> WOODCUTTING_AXE = createTag("woodcutting_axe");

        public static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, name));
        }
    }
}
