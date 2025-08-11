package com.github.finestgit.adventurecraft.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;


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
