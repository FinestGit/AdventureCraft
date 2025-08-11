package com.github.finestgit.adventurecraft.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ToolStatsComponent(int speed, double multi, double wisdom) {
    public static final Codec<ToolStatsComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("speed").forGetter(ToolStatsComponent::speed),
                    Codec.DOUBLE.fieldOf("multi").forGetter(ToolStatsComponent::multi),
                    Codec.DOUBLE.fieldOf("wisdom").forGetter(ToolStatsComponent::wisdom)
            ).apply(instance, ToolStatsComponent::new)
    );

    public static final StreamCodec<ByteBuf, ToolStatsComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ToolStatsComponent::speed,
            ByteBufCodecs.DOUBLE, ToolStatsComponent::multi,
            ByteBufCodecs.DOUBLE, ToolStatsComponent::wisdom,
            ToolStatsComponent::new
    );
}