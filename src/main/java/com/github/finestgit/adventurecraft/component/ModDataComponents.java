package com.github.finestgit.adventurecraft.component;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
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

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name,
            UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPE.register(eventBus);
    }

    public record CustomDurabilityComponent(int currentDurability, int maxDurability) {
        public static final Codec<CustomDurabilityComponent> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.INT.fieldOf("current_durability").forGetter(CustomDurabilityComponent::currentDurability),
                        Codec.INT.fieldOf("max_durability").forGetter(CustomDurabilityComponent::maxDurability)
                ).apply(instance, CustomDurabilityComponent::new)
        );

        public static final StreamCodec<ByteBuf, CustomDurabilityComponent> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, CustomDurabilityComponent::currentDurability,
                ByteBufCodecs.INT, CustomDurabilityComponent::maxDurability,
                CustomDurabilityComponent::new
        );
    }
}
