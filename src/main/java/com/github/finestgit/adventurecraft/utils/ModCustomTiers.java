package com.github.finestgit.adventurecraft.utils;

public enum ModCustomTiers {
    WOOD_TIER(40, 60, 1, 5, 1.0, 2.0, 1.0, 2.0);

    private final int minDurability;
    private final int maxDurability;
    private final int minSpeed;
    private final int maxSpeed;
    private final double minMulti;
    private final double maxMulti;
    private final double minWisdom;
    private final double maxWisdom;

    ModCustomTiers(int minDurability, int maxDurability,
                   int minSpeed, int maxSpeed,
                   double minMulti, double maxMulti,
                   double minWisdom, double maxWisdom) {
        this.minDurability = minDurability;
        this.maxDurability = maxDurability;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minMulti = minMulti;
        this.maxMulti = maxMulti;
        this.minWisdom = minWisdom;
        this.maxWisdom = maxWisdom;
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
}
