package com.github.finestgit.adventurecraft.item;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.item.woodcutting.WoodcuttingAxeItem;
import com.github.finestgit.adventurecraft.utils.ModCustomTiers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AdventureCraftMod.MODID);

    public static final DeferredItem<Item> OAK_TIMBER = ITEMS.register("oak_timber", () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredItem<Item> WOOD_WOODCUTTING_AXE = ITEMS.register("wood_woodcutting_axe",
            () -> new WoodcuttingAxeItem(ModCustomTiers.WOOD_TIER, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
