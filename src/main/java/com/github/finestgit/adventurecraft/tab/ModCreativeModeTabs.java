package com.github.finestgit.adventurecraft.tab;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.block.ModBlocks;
import com.github.finestgit.adventurecraft.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdventureCraftMod.MODID);

    public static final Supplier<CreativeModeTab> ADVENTURE_CRAFT_ITEMS_TAB = CREATIVE_MODE_TAB.register("adventurecraft_items_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, "adventurecraft_blocks_tab"))
                    .icon(() -> new ItemStack(ModItems.OAK_TIMBER.get()))
                    .title(Component.translatable("creativetab.adventurecraft.adventurecraft_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.OAK_TIMBER);
                        output.accept(ModItems.COPPER_OAK_TIMBER);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> ADVENTURE_CRAFT_TOOLS_TAB = CREATIVE_MODE_TAB.register("adventurecraft_tools_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AdventureCraftMod.MODID, "adventurecraft_items_tab"))
                    .icon(() -> new ItemStack(ModItems.WOOD_WOODCUTTING_AXE.get()))
                    .title(Component.translatable("creativetab.adventurecraft.adventurecraft_tools"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.WOOD_WOODCUTTING_AXE);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> ADVENTURE_CRAFT_BLOCKS_TAB = CREATIVE_MODE_TAB.register("adventurecraft_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.OAK_LOG.get()))
                    .title(Component.translatable("creativetab.adventurecraft.adventurecraft_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.OAK_LOG);
                        output.accept(ModBlocks.OAK_SAPLING);
                        output.accept(ModBlocks.OAK_LEAVES);
                        output.accept(ModBlocks.COPPER_OAK_LOG);
                        output.accept(ModBlocks.COPPER_OAK_LEAVES);
                        output.accept(ModBlocks.COPPER_OAK_SAPLING);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
