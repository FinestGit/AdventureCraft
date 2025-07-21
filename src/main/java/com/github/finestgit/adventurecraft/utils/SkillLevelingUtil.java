package com.github.finestgit.adventurecraft.utils;

public class SkillLevelingUtil {
    private static final int BASE_XP_LEVEL_1 = 100;
    private static final double GROWTH_RATE = 1.1;

    public static int getExperienceRequiredForLevelUp(int currentLevel) {
        if (currentLevel <= 0) {
            return BASE_XP_LEVEL_1;
        }

        return (int) (BASE_XP_LEVEL_1 * Math.pow(GROWTH_RATE, currentLevel - 1));
    }

    public static int getTotalExperienceForLevel(int targetLevel) {
        if (targetLevel <= 0) {
            return 0;
        }
        int totalXp = 0;
        for (int i = 0; i < targetLevel; i++) {
            totalXp += getExperienceRequiredForLevelUp(i);
        }
        return totalXp;
    }

    public static int getLevelFromExperience(int totalExperience) {
        int level = 0;
        int xpNeeded = getExperienceRequiredForLevelUp(level);

        while (totalExperience >= xpNeeded) {
            totalExperience -= xpNeeded;
            level++;
            xpNeeded = getExperienceRequiredForLevelUp(level);
        }
        return level;
    }

    public static int getExperienceInCurrentLevel(int totalExperience, int currentLevel) {
        if (currentLevel <= 0) {
            return totalExperience;
        }
        int xpToReachCurrentLevel = getTotalExperienceForLevel(currentLevel);
        return totalExperience - xpToReachCurrentLevel;
    }
}
