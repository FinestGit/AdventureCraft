package com.github.finestgit.adventurecraft.attachment.skills.woodcutting;

import com.github.finestgit.adventurecraft.utils.SkillLevelingUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.jarjar.nio.util.Lazy;

import java.util.HashMap;
import java.util.Map;

public class WoodcuttingSkillData {
    public static final Codec<WoodcuttingSkillData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("total_experience").forGetter(WoodcuttingSkillData::getTotalExperience),
            Codec.INT.fieldOf("spent_perk_points").forGetter(WoodcuttingSkillData::getSpentPerkPoints),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("perk_ranks").forGetter(WoodcuttingSkillData::getPerkRanks)
    ).apply(instance, WoodcuttingSkillData::new));

    public static final Lazy<WoodcuttingSkillData> DEFAULT = Lazy.of(() -> new WoodcuttingSkillData(0, 0, new HashMap<>()));

    private int totalExperience;
    private int spentPerkPoints;
    private Map<String, Integer> perkRanks;

    public WoodcuttingSkillData(int totalExperience, int spentPerkPoints, Map<String, Integer> perkRanks) {
        this.totalExperience = totalExperience;
        this.spentPerkPoints = spentPerkPoints;
        this.perkRanks = new HashMap<>(perkRanks);
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public void addExperience(int amount) {
        if (amount < 0) return;
        this.totalExperience += amount;
    }

    public int getLevel() {
        return SkillLevelingUtil.getLevelFromExperience(totalExperience);
    }

    public int getExperienceToNextLevel() {
        int currentLevel = getLevel();
        return SkillLevelingUtil.getExperienceRequiredForLevelUp(currentLevel);
    }

    public int getExperienceInCurrentLevel() {
        int currentLevel = getLevel();
        return SkillLevelingUtil.getExperienceInCurrentLevel(totalExperience, currentLevel);
    }

    public int getSpentPerkPoints() {
        return spentPerkPoints;
    }

    public Map<String, Integer> getPerkRanks() {
        return new HashMap<>(perkRanks);
    }

    public int getTotalAvailablePerkPoints() {
        return getLevel();
    }

    public int getUnspentPerkPoints() {
        return getTotalAvailablePerkPoints() - spentPerkPoints;
    }

    public boolean upgradePerk(String perkId, int maxRank) {
        int currentRank = perkRanks.getOrDefault(perkId, 0);

        if (currentRank >= maxRank) {
            return false;
        }
        if (getUnspentPerkPoints() <= 0) {
            return false;
        }

        perkRanks.put(perkId, currentRank + 1);
        spentPerkPoints++;
        return true;
    }

    public int getPerkRank(String perkId) {
        return perkRanks.getOrDefault(perkId, 0);
    }

    public boolean hasPerk(String perkId) {
        return getPerkRank(perkId) > 0;
    }

    @Override
    public String toString() {
        return "WoodcuttingSkillData{" +
                "totalExperience=" + totalExperience +
                ", level=" + getLevel() +
                ", spentPerkPoints=" + spentPerkPoints +
                ", unspentPerkPoints=" + getUnspentPerkPoints() +
                ", perkRanks=" + perkRanks +
                "}";
    }
}
