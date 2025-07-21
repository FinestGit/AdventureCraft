package com.github.finestgit.adventurecraft.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.jarjar.nio.util.Lazy;

public class DefaultPlayerData {
    public static final Codec<DefaultPlayerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT
                    .fieldOf("default_speed").forGetter(DefaultPlayerData::getDefaultSpeed)
    ).apply(instance, DefaultPlayerData::new));

    public static final Lazy<DefaultPlayerData> DEFAULT = Lazy.of(() -> new DefaultPlayerData(1.0f));

    private float defaultSpeed;

    public DefaultPlayerData(float defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public float getDefaultSpeed() {
        return this.defaultSpeed;
    }
}
