package com.github.finestgit.adventurecraft.utils;

public enum WoodcuttingAxeTier {
    TIER_1_COMMON(1, 40, 60, 1, 5, 1.0, 2.0, 1.0, 2.0),
    TIER_1_UNCOMMON(1, 50, 70, 3, 10, 1.5, 2.0, 1.5, 2.0),
    TIER_1_RARE(1, 60, 80, 5, 15, 2.0, 2.0, 2.0, 2.0),
    TIER_1_ARCANE(1, 80, 100, 10, 20, 2.0, 2.0, 2.0, 2.0),
    TIER_2_COMMON(1, 80, 100, 10, 20, 1.5, 3.0, 1.5, 3.0),
    TIER_2_UNCOMMON(1, 100, 120, 15, 28, 2.0, 3.0, 2.0, 3.0),
    TIER_2_RARE(1, 120, 140, 20, 40, 2.5, 3.0, 2.5, 3.0),
    TIER_2_ARCANE(1, 150, 200, 25, 50, 3.0, 3.0, 3.0, 3.0);

    private final int tier;
    private final int minDurability;
    private final int maxDurability;
    private final int minSpeed;
    private final int maxSpeed;
    private final double minMulti;
    private final double maxMulti;
    private final double minWisdom;
    private final double maxWisdom;

    WoodcuttingAxeTier(int tier,
                       int minDurability, int maxDurability,
                       int minSpeed, int maxSpeed,
                       double minMulti, double maxMulti,
                       double minWisdom, double maxWisdom) {
        this.tier = tier;
        this.minDurability = minDurability;
        this.maxDurability = maxDurability;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minMulti = minMulti;
        this.maxMulti = maxMulti;
        this.minWisdom = minWisdom;
        this.maxWisdom = maxWisdom;
    }

    public int getTier() {
        return this.tier;
    }

    public int getMinDurability() {
        return this.minDurability;
    }

    public int getMaxDurability() {
        return this.maxDurability;
    }

    public int getMinSpeed() {
        return this.minSpeed;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public double getMinMulti() {
        return this.minMulti;
    }

    public double getMaxMulti() {
        return this.maxMulti;
    }

    public double getMinWisdom() {
        return this.minWisdom;
    }

    public double getMaxWisdom() {
        return this.maxWisdom;
    }

    public static WoodcuttingAxeTier getByLevel(int level) {
        for (WoodcuttingAxeTier tier : values()) {
            if (tier.getTier() == level) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Invalid tier level: " + level);
    }
}
