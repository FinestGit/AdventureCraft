package com.github.finestgit.adventurecraft.component;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(AdventureCraftMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomDurabilityComponent>> DURABILITY_COMPONENT = register(
            "durability_component",
            builder -> builder
                    .persistent(CustomDurabilityComponent.CODEC)
                    .networkSynchronized(CustomDurabilityComponent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ToolStatsComponent>> TOOL_STATS_COMPONENT = register(
            "tool_stats_component",
            builder -> builder
                    .persistent(ToolStatsComponent.CODEC)
                    .networkSynchronized(ToolStatsComponent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockStatsComponent>> BLOCK_STATS_COMPONENT = register(
            "block_stats_component",
            builder -> builder
                    .persistent(BlockStatsComponent.CODEC)
                    .networkSynchronized(BlockStatsComponent.STREAM_CODEC)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name,
            UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPE.register(eventBus);
    }
}
