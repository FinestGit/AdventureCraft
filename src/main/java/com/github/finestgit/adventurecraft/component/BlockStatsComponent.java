package com.github.finestgit.adventurecraft.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BlockStatsComponent(int maxHealth) {
    public static final Codec<BlockStatsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("max_health").forGetter(BlockStatsComponent::maxHealth)
    ).apply(instance, BlockStatsComponent::new));

    public static final StreamCodec<ByteBuf, BlockStatsComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BlockStatsComponent::maxHealth,
            BlockStatsComponent::new
    );
}