package com.github.finestgit.adventurecraft.utils;

public enum LogMaterialTier {
    TIER_1_LOG(1, 1.0f, 1.0f),
    TIER_2_LOG(2, 10.0f, 10.0f);
    private final int tier;
    private final float woodcuttingResistance;
    private final float expectedWoodcuttingSpeed;

    LogMaterialTier(int tier, float woodcuttingResistance, float expectedWoodcuttingSpeed) {
        this.tier = tier;
        this.woodcuttingResistance = woodcuttingResistance;
        this.expectedWoodcuttingSpeed = expectedWoodcuttingSpeed;
    }

    public int getTier() {
        return this.tier;
    }

    public float getWoodcuttingResistance() {
        return this.woodcuttingResistance;
    }

    public float getExpectedWoodcuttingSpeed() {
        return this.expectedWoodcuttingSpeed;
    }

    public static LogMaterialTier getByLevel(int level) {
        for (LogMaterialTier tier : values()) {
            if (tier.getTier() == level) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Invalid tier level: " + level);
    }
}
