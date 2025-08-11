package com.github.finestgit.adventurecraft.levelgen.feature;

import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class ModBranchPlanner {
    public static List<ModBranchPlan> planBranches(RandomSource random, int trunkHeight, double trunkRadius) {
        List<ModBranchPlan> plans = new ArrayList<>();

        int branchCount = Math.max(6, trunkHeight / 2 + random.nextInt(5));

        for (int i = 0; i < branchCount; i++) {
            int minStart = Math.max(1, (int) (trunkHeight * 0.4));
            int maxStart = Math.max(minStart, trunkHeight - 4);
            int range = maxStart - minStart + 1;
            if (range <= 0) {
                range = 1;
            }

            int startY = minStart + random.nextInt(range);

            double angle = random.nextDouble() * Math.PI * 2;
            double dirX = Math.cos(angle);
            double dirZ = Math.sin(angle);

            plans.add(new ModBranchPlan(startY, dirX, dirZ));
        }

        return plans;
    }
}
