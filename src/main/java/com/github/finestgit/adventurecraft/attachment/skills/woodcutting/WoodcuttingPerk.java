package com.github.finestgit.adventurecraft.attachment.skills.woodcutting;

import java.util.function.Function;

public class WoodcuttingPerk {
    private final String id;
    private final String displayName;
    private final String baseDescription;
    private final int maxRank;
    private final Function<Integer, String> rankDescription;
    private final Function<Integer, Object> effectValue;

    public WoodcuttingPerk(String id, String displayName, String baseDescription, int maxRank,
                           Function<Integer, String> rankDescription, Function<Integer, Object> effectValue) {
        this.id = id;
        this.displayName = displayName;
        this.baseDescription = baseDescription;
        this.maxRank = maxRank;
        this.rankDescription = rankDescription;
        this.effectValue = effectValue;
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getBaseDescription() {
        return this.baseDescription;
    }

    public int getMaxRank() {
        return this.maxRank;
    }

    public String getDescriptionForRank(int rank) {
        if (rank < 0 || rank > maxRank) return "Invalid Rank";
        return rankDescription.apply(rank);
    }

    public <T> T getEffectValue(int rank, Class<T> type) {
        if (rank < 0 || rank > maxRank) return null;
        Object value = effectValue.apply(rank);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }
}
