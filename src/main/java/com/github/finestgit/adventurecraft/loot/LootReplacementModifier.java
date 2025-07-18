package com.github.finestgit.adventurecraft.loot;

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

public class LootReplacementModifier extends LootModifier {
    public static final MapCodec<LootReplacementModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                            BuiltInRegistries.ITEM.byNameCodec()
                                    .fieldOf("item")
                                    .forGetter(e -> e.item),
                            BuiltInRegistries.ITEM.byNameCodec()
                                    .fieldOf("toReplace")
                                    .forGetter(e -> e.toReplace)
                    ))
                    .apply(inst, LootReplacementModifier::new));
    private final Item item;
    private final Item toReplace;

    public LootReplacementModifier(LootItemCondition[] conditionsIn, Item item, Item toReplace) {
        super(conditionsIn);
        this.item = item;
        this.toReplace = toReplace;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> modifiedLoot = new ObjectArrayList<>();
        for (ItemStack stack : generatedLoot) {
            if (stack.getItem() == toReplace) {
                modifiedLoot.add(new ItemStack(this.item));
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
