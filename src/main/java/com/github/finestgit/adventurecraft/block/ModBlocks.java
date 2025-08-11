package com.github.finestgit.adventurecraft.block;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.block.leaves.LeafBlock;
import com.github.finestgit.adventurecraft.block.logs.LogBlock;
import com.github.finestgit.adventurecraft.block.saplings.CustomSaplingBlock;
import com.github.finestgit.adventurecraft.item.ModItems;
import com.github.finestgit.adventurecraft.utils.LogMaterialTier;
import com.github.finestgit.adventurecraft.worldgen.tree.ModTreeGrowers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AdventureCraftMod.MODID);

    public static final DeferredBlock<LogBlock> OAK_LOG = registerBlock("oak_log",
            () -> new LogBlock(LogMaterialTier.TIER_1_LOG,
                    BlockBehaviour.Properties.of()
                            .sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> OAK_LEAVES = registerBlock("oak_leaves",
            () -> new LeafBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .strength(0.2F)
                            .randomTicks()
                            .sound(SoundType.CHERRY_LEAVES)
                            .noOcclusion()
                            .ignitedByLava()
                            .pushReaction(PushReaction.DESTROY)
            ));

    public static final DeferredBlock<Block> OAK_SAPLING = registerBlock("oak_sapling",
            () -> new CustomSaplingBlock(
                    ModTreeGrowers.OAK,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .pushReaction(PushReaction.DESTROY),
                    () -> Blocks.GRASS_BLOCK));

    public static final DeferredBlock<LogBlock> COPPER_OAK_LOG = registerBlock("copper_oak_log",
            () -> new LogBlock(LogMaterialTier.TIER_2_LOG,
                    BlockBehaviour.Properties.of()
                            .sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> COPPER_OAK_LEAVES = registerBlock("copper_oak_leaves",
            () -> new LeafBlock(
                    BlockBehaviour.Properties.ofFullCopy(OAK_LEAVES.get())
            ));

    public static final DeferredBlock<Block> COPPER_OAK_SAPLING = registerBlock("copper_oak_sapling",
            () -> new CustomSaplingBlock(
                    ModTreeGrowers.COPPER_OAK,
                    BlockBehaviour.Properties.ofFullCopy(OAK_SAPLING.get()),
                    () -> Blocks.GRASS_BLOCK
            ));

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
