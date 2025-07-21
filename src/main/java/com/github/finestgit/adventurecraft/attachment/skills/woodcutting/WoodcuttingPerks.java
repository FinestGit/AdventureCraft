package com.github.finestgit.adventurecraft.attachment.skills.woodcutting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WoodcuttingPerks {
    public static final WoodcuttingPerk TREE_FELLER = new WoodcuttingPerk(
            "tree_feller",
            "Tree Feller",
            "Destroy additional connected logs.",
            5,
            (rank) -> "Destroys " + (rank + 1) + " connected logs.",
            (rank) -> rank + 1
    );

    public static final WoodcuttingPerk TREES_THAT_DEFY_GRAVITY = new WoodcuttingPerk(
            "trees_that_defy_gravity",
            "Trees That Defy Gravity!",
            "When you destroy a log that has logs above it, it won't accidentally destroy the whole tree anymore!",
            1,
            (rank) -> "You figured out how to warp space and time, to allow for trees to defy gravity",
            (rank) -> null
    );

    public static final WoodcuttingPerk ONE_WITH_THE_TREES = new WoodcuttingPerk(
            "one_with_the_tress",
            "One With The Trees",
            "When nearby trees, you gain a slight increase to your movement speed",
            5,
            (rank) -> "Distance to a log for a small speed increase is now " + (rank * 5),
            (rank) -> rank * 5
    );

    public static final WoodcuttingPerk SAVING_THE_PLANET = new WoodcuttingPerk(
            "saving_the_planet",
            "Saving the Planet",
            "When you chop down a tree you will replace that tree with a sapling!",
            1,
            (rank) -> "Automatically plants a sapling on suitable ground.",
            (rank) -> null
    );

    public static final WoodcuttingPerk WISDOM_OF_THE_FOREST = new WoodcuttingPerk(
            "wisdom_of_the_forest",
            "Wisdom of the Forest",
            "When you chop down a log you will gain an increase in experience",
            5,
            (rank) -> "Experience increased by " + (rank * 10) + "%",
            (rank) -> (double) (rank * 0.10)
    );

    public static final WoodcuttingPerk TIMBER_TITAN = new WoodcuttingPerk(
            "timber_titan",
            "Timber Titan",
            "You can deforest the world even faster now",
            5,
            (rank) -> "Increase woodcutting speed by " + (rank + 2),
            (rank) -> (int) (rank + 2)
    );

    public static final WoodcuttingPerk GENEROUS_GROWTH = new WoodcuttingPerk(
            "generous_growth",
            "Generous Growth",
            "Trees yield extra timber when destroyed",
            5,
            (rank) -> "Increases woodcutting multi by " + (rank * 5) + "%",
            (rank) -> (double) (rank * 0.05)
    );

    public static final WoodcuttingPerk ARBORISTS_TOUCH = new WoodcuttingPerk(
            "arborists_touch",
            "Arborist's Touch",
            "Instantly grow a sapling with your axe",
            1,
            (rank) -> "Allows you to use your axe to instantly grow a sapling. (Costs 1 durability)",
            (rank) -> null
    );

    public static final WoodcuttingPerk HEARTWOOD_LORE = new WoodcuttingPerk(
            "heartwood_lore",
            "Heartwood Lore",
            "You have a higher chance of dropping rarer materials",
            5,
            (rank) -> "You have a " + (rank * 10) + "% higher chance to drop a rare material",
            (rank) -> (double) (rank * 0.1)
    );

    public static final WoodcuttingPerk ARBORISTS_FORESIGHT = new WoodcuttingPerk(
            "arborists_foresight",
            "Arborist's Foresight",
            "You have a chance to drop the next tier of items!",
            5,
            (rank) -> "You have a " + (rank * 5) + "% chance to drop the next tier of items",
            (rank) -> (double) (rank * 0.05)
    );

    public static final WoodcuttingPerk FOREST_GUARDIANS_SIGHT = new WoodcuttingPerk(
            "forest_guardians_sight",
            "Forest Guardian's Sight",
            "Gain night vision when near trees",
            1,
            (rank) -> "When nearby trees you have night vision",
            (rank) -> null
    );

    public static final WoodcuttingPerk FOREST_GUARDIANS_RESPITE = new WoodcuttingPerk(
            "forest_guardians_respite",
            "Forest Guardian's Respite",
            "Gain life and hunger when near trees",
            1,
            (rank) -> "When nearby trees you gain health and decreased hunger",
            (rank) -> null
    );

    public static final WoodcuttingPerk BARK_WEAVER = new WoodcuttingPerk(
            "bark_weaver",
            "Bark Weaver",
            "You can get items from a log before chopping it down!",
            5,
            (rank) -> switch (rank) {
                case 2 ->
                        "When you right click a log you are guaranteed the timber and have a chance to get the resin. (Costs 2 durability)";
                case 3 ->
                        "When you right click a log you are guaranteed the timber plus an increased chance to get resin. (Costs 5 durability)";
                case 4 ->
                        "When you right click a log you are guaranteed the timber and the resin (Costs 10 durability)";
                case 5 ->
                        "When you right click a log you are guaranteed the timber and the resin, with a small chance to get the heartwood fiber (Costs 25 durability)";
                default -> "When you right click a log you are guaranteed the timber (Costs 1 durability)";
            },
            (rank) -> null
    );

    public static final WoodcuttingPerk TIMBER_SURGE = new WoodcuttingPerk(
            "timber_surge",
            "Timber Surge",
            "Unleash a burst of energy to convert a single log into many wood resources.",
            5,
            (rank) -> switch (rank) {
                case 2 -> "The burst of timber now has a chance to include Resins.";
                case 3 ->
                        "The burst of timber can now affect multiple logs, causing all of them to turn into timber and resin.";
                case 4 -> "The burst of timber now has a chance to yield Heartwood Fibers";
                case 5 ->
                        "Capstone: Active: Right-click a log with your axe to trigger a massive surge. Transforming many trees into a single piece of World Tree Shard. (Extremely long cooldown, most likely will destroy your axe)";
                default ->
                        "Active: Right-click a log with your axe. Consumes the log and a large amount of durability to give a burst of common timber around you. (Long cooldown)";
            },
            (rank) -> null
    );

    public static final WoodcuttingPerk LUMBERJACKS_EDGE = new WoodcuttingPerk(
            "lumberjacks_edge",
            "Lumberjack's Edge",
            "Increases the stats of your woodcutting worn items",
            5,
            (rank) -> "Increases your woodcutting stats by " + (rank * 5) + "%",
            (rank) -> (double) (rank * 0.05)
    );

    public static final WoodcuttingPerk ARBOREAL_MASTERY = new WoodcuttingPerk(
            "arboreal_mastery",
            "Arboreal Master",
            "Increase your multi, wisdom, and speed to infinity",
            999,
            (rank) -> "Increases woodcutting multi, wisdom, and speed by " + (rank + 1) + "%",
            (rank) -> (double) (rank * 0.01)
    );


    private static final Map<String, WoodcuttingPerk> ALL_PERKS;

    static {
        Map<String, WoodcuttingPerk> perks = new HashMap<>();
        perks.put(TREE_FELLER.getId(), TREE_FELLER);
        perks.put(TREES_THAT_DEFY_GRAVITY.getId(), TREES_THAT_DEFY_GRAVITY);
        perks.put(ONE_WITH_THE_TREES.getId(), ONE_WITH_THE_TREES);
        perks.put(SAVING_THE_PLANET.getId(), SAVING_THE_PLANET);
        perks.put(WISDOM_OF_THE_FOREST.getId(), WISDOM_OF_THE_FOREST);
        perks.put(TIMBER_TITAN.getId(), TIMBER_TITAN);
        perks.put(GENEROUS_GROWTH.getId(), GENEROUS_GROWTH);
        perks.put(ARBORISTS_TOUCH.getId(), ARBORISTS_TOUCH);
        perks.put(HEARTWOOD_LORE.getId(), HEARTWOOD_LORE);
        perks.put(ARBORISTS_FORESIGHT.getId(), ARBORISTS_FORESIGHT);
        perks.put(FOREST_GUARDIANS_SIGHT.getId(), FOREST_GUARDIANS_SIGHT);
        perks.put(FOREST_GUARDIANS_RESPITE.getId(), FOREST_GUARDIANS_RESPITE);
        perks.put(BARK_WEAVER.getId(), BARK_WEAVER);
        perks.put(LUMBERJACKS_EDGE.getId(), LUMBERJACKS_EDGE);
        perks.put(TIMBER_SURGE.getId(), TIMBER_SURGE);
        perks.put(ARBOREAL_MASTERY.getId(), ARBOREAL_MASTERY);

        ALL_PERKS = Collections.unmodifiableMap(perks);
    }

    public static WoodcuttingPerk getPerk(String id) {
        return ALL_PERKS.get(id);
    }
}