package com.github.finestgit.adventurecraft.loot;

import com.github.finestgit.adventurecraft.context.ModLootContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Random;

public class LootReplacementModifier extends LootModifier {
    public static final MapCodec<LootReplacementModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                            BuiltInRegistries.ITEM.byNameCodec()
                                    .fieldOf("item")
                                    .forGetter(e -> e.item),
                            BuiltInRegistries.ITEM.byNameCodec()
                                    .fieldOf("toReplace")
                                    .forGetter(e -> e.toReplace),
                            Codec.DOUBLE
                                    .fieldOf("lootMultiplier")
                                    .forGetter(e -> e.lootMultiplier)
                    ))
                    .apply(inst, LootReplacementModifier::new));
    private final Item item;
    private final Item toReplace;
    private final double lootMultiplier;

    public LootReplacementModifier(LootItemCondition[] conditionsIn, Item item, Item toReplace, double lootMultiplier) {
        super(conditionsIn);
        this.item = item;
        this.toReplace = toReplace;
        this.lootMultiplier = lootMultiplier;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> modifiedLoot = new ObjectArrayList<>();
        double lootMulti = this.lootMultiplier;

        if (context.hasParam(ModLootContext.WOODCUTTING_MULTI_LOOT_PARAM)) {
            lootMulti = context.getParam(ModLootContext.WOODCUTTING_MULTI_LOOT_PARAM);
        }

        Random rand = new Random();
        // Calculating additional drops ->
        // If lootMulti = 1 -> additionalDrops = lootMulti - this.lootMultiplier -> 0
        // If lootMulti = 2 -> additionalDrops = lootMulti - this.lootMultiplier -> 1
        // If lootMulti = 1.5 -> additionalDrops = lootMulti - this.lootMultiplier -> 0.5
        int additionalDrops = (int) Math.floor(lootMulti - this.lootMultiplier);

        // Now figure out how much remainder there for getting a random extra drop percent
        // lootMulti = 1.5 -> extraPercent = lootMulti - this.lootMultiplier -> 0.5
        // Therefore we have a 50% chance us to get an additional item
        double extraPercent = lootMulti - this.lootMultiplier;

        if (rand.nextDouble() < extraPercent) {
            additionalDrops++;
        }

        for (ItemStack stack : generatedLoot) {
            if (stack.getItem() == toReplace) {
                int baseCount = stack.getCount();
                int finalCount = baseCount + additionalDrops;
                if (finalCount > 0) {
                    modifiedLoot.add(new ItemStack(this.item, finalCount));
                }
            } else {
                modifiedLoot.add(stack);
            }
        }
        return modifiedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
