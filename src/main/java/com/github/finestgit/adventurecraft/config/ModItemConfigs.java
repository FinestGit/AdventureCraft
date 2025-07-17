package com.github.finestgit.adventurecraft.config;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModItemConfigs {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = specPair.getLeft();
        CLIENT_SPEC = specPair.getRight();
    }

    public static class Client {
        public final ModConfigSpec.DoubleValue WOODEN_WOODCUTTING_AXE_BASE_SPEED;
        public final ModConfigSpec.IntValue WOODEN_WOODCUTTING_AXE_MIN_SPEED;
        public final ModConfigSpec.IntValue WOODEN_WOODCUTTING_AXE_MAX_SPEED;
        public final ModConfigSpec.DoubleValue WOODEN_WOODCUTTING_AXE_MIN_MULTI;
        public final ModConfigSpec.DoubleValue WOODEN_WOODCUTTING_AXE_MAX_MULTI;
        public final ModConfigSpec.DoubleValue WOODEN_WOODCUTTING_AXE_MIN_WISDOM;
        public final ModConfigSpec.DoubleValue WOODEN_WOODCUTTING_AXE_MAX_WISDOM;
        public final ModConfigSpec.IntValue WOODEN_WOODCUTTING_AXE_MIN_DURABILITY;
        public final ModConfigSpec.IntValue WOODEN_WOODCUTTING_AXE_MAX_DURABILITY;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("wooden_woodcutting_axe_settings");
            WOODEN_WOODCUTTING_AXE_BASE_SPEED = builder
                    .comment("The base block breaking speed of the Wooden Woodcutting Axe (e.g., 6.0 for vanilla wood axe)")
                    .defineInRange("woodenWoodcuttingAxeBaseSpeed", 6.0, 0.1, 1000.0);

            WOODEN_WOODCUTTING_AXE_MIN_SPEED = builder
                    .comment("The minimum random Speed stat for the Wooden Woodcutting Axe (multiplier for base speed)")
                    .defineInRange("woodenWoodcuttingAxeMinSpeed", 1, 1, 100);

            WOODEN_WOODCUTTING_AXE_MAX_SPEED = builder
                    .comment("The maximum random Speed stat for the Wooden Woodcutting Axe (multiplier for base speed)")
                    .defineInRange("woodenWoodcuttingAxeMaxSpeed", 3, 1, 100);

            WOODEN_WOODCUTTING_AXE_MIN_MULTI = builder
                    .comment("The minimum random Multi stat for the Wooden Woodcutting Axe (drop multiplier)")
                    .defineInRange("woodenWoodcuttingAxeMinMulti", 1.0, 0.0, 10.0);

            WOODEN_WOODCUTTING_AXE_MAX_MULTI = builder
                    .comment("The maximum random Multi stat for the Wooden Woodcutting Axe (drop multiplier)")
                    .defineInRange("woodenWoodcuttingAxeMaxMulti", 2.0, 0.0, 10.0);

            WOODEN_WOODCUTTING_AXE_MIN_WISDOM = builder
                    .comment("The minimum random Wisdom stat for the Wooden Woodcutting Axe (XP multiplier)")
                    .defineInRange("woodenWoodcuttingAxeMinWisdom", 1.0, 0.0, 10.0);

            WOODEN_WOODCUTTING_AXE_MAX_WISDOM = builder
                    .comment("The maximum random Wisdom stat for the Wooden Woodcutting Axe (XP multiplier)")
                    .defineInRange("woodenWoodcuttingAxeMaxWisdom", 1.0, 0.0, 10.0);

            WOODEN_WOODCUTTING_AXE_MIN_DURABILITY = builder
                    .comment("The minimum random Durability stat for the Wooden Woodcutting Axe")
                    .defineInRange("woodenWoodcuttingAxeMinDurability", 40, 1, 500);

            WOODEN_WOODCUTTING_AXE_MAX_DURABILITY = builder
                    .comment("The maximum random Durability stat for the Wooden Woodcutting Axe")
                    .defineInRange("woodenWoodcuttingAxeMaxDurability", 60, 1, 500);
            builder.pop();
        }
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        AdventureCraftMod.LOGGER.debug("Loaded mod config: {}", event.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        AdventureCraftMod.LOGGER.debug("Reloaded mod config: {}", event.getConfig().getFileName());
    }
}
