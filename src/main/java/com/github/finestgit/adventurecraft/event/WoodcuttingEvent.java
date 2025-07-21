package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.attachment.PlayerAttachement;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingPerks;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingSkillData;
import com.github.finestgit.adventurecraft.context.ModLootContext;
import com.github.finestgit.adventurecraft.item.woodcutting.WoodcuttingAxeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WoodcuttingEvent {
    private static final Set<BlockPos> DESTROYABLE_LOGS = new HashSet<>();
    private static final Set<BlockPos> HARVESTED_LOGS = new HashSet<>();
    private static ServerLevel DESTROYED_ON_LEVEL;
    private static ServerLevel HARVEST_ON_LEVEL;
    private static ServerPlayer HARVESTING_PLAYER;
    private static int DESTROY_TICK_COUNTER = 0;
    private static int HARVEST_TICK_COUNTER = 0;

    @SubscribeEvent
    public static void onLogBreakingEvent(PlayerEvent.BreakSpeed event) {
        // Grab out data we are going to need
        Player player = event.getEntity();
        BlockState blockState = event.getState();
        if (event.getPosition().isPresent()) {
            ItemStack stack = player.getMainHandItem();
            if (blockState.is(BlockTags.LOGS) && stack.getItem() instanceof WoodcuttingAxeItem axe) {
                WoodcuttingSkillData woodcuttingSkillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);
                int axeSpeed = axe.getSpeed(stack);
                if (woodcuttingSkillData.hasPerk(WoodcuttingPerks.TIMBER_TITAN.getId())) {
                    int timberTitanRank = woodcuttingSkillData.getPerkRank(WoodcuttingPerks.TIMBER_TITAN.getId());
                    int timberTitanValue = WoodcuttingPerks.TIMBER_TITAN.getEffectValue(timberTitanRank, Integer.class);
                    axeSpeed += timberTitanValue;
                }
                float baseHarvestSpeed = 0.5f;
                float breakSpeed = axeSpeed * baseHarvestSpeed;
                event.setNewSpeed(breakSpeed);
            } else {
                // We are not using a woodcutting axe then we do not want to let them chop logs
                // This also prevents the player from trying to use the woodcutting axe on non logs
                event.setNewSpeed(0);
            }
        }
    }

    @SubscribeEvent
    public static void onLogDestroyEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = event.getState();
        if (level.isClientSide || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof WoodcuttingAxeItem axe) {
            WoodcuttingSkillData woodcuttingSkillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);
            // Handle Experience Gain
            double wisdomValue = axe.getWisdom(stack);
            if (woodcuttingSkillData.hasPerk(WoodcuttingPerks.WISDOM_OF_THE_FOREST.getId())) {
                int wisdomOfTheForestRank = woodcuttingSkillData.getPerkRank(WoodcuttingPerks.WISDOM_OF_THE_FOREST.getId());
                double wisdomOfTheForestValue = WoodcuttingPerks.WISDOM_OF_THE_FOREST.getEffectValue(wisdomOfTheForestRank, Double.class);
                wisdomValue += wisdomOfTheForestValue;
            }
            // Replace with value coming from logs themselves at some point;
            int exp = 10;
            int totalExperienceGained = (int) (wisdomValue * exp);
            woodcuttingSkillData.addExperience(totalExperienceGained);
            player.sendSystemMessage(Component.literal(woodcuttingSkillData.toString()));

            // Loot Multi handling
            event.setCanceled(true);

            double multiValue = getPlayerMultiValue(serverPlayer);

            if (!woodcuttingSkillData.hasPerk(WoodcuttingPerks.TREES_THAT_DEFY_GRAVITY.getId())) {
                DESTROYED_ON_LEVEL = (ServerLevel) level;
                destroyWholeTreeAboveWithNoLoot(level, pos);
            }

            if (woodcuttingSkillData.hasPerk(WoodcuttingPerks.TREE_FELLER.getId())) {
                int treeFellerRank = woodcuttingSkillData.getPerkRank(WoodcuttingPerks.TREE_FELLER.getId());
                int treeFellerValue = WoodcuttingPerks.TREE_FELLER.getEffectValue(treeFellerRank, Integer.class);
                treeFellerHandling(level, pos, treeFellerValue);
                if (!woodcuttingSkillData.hasPerk(WoodcuttingPerks.TREES_THAT_DEFY_GRAVITY.getId()) && !HARVESTED_LOGS.isEmpty()) {
                    DESTROYED_ON_LEVEL = (ServerLevel) level;
                    for (BlockPos harvestBlock : HARVESTED_LOGS) {
                        destroyWholeTreeAboveWithNoLoot(level, harvestBlock);
                    }
                }
            }
            HARVESTING_PLAYER = serverPlayer;
            handleLootDrop(serverPlayer, pos, multiValue);

            if (woodcuttingSkillData.hasPerk(WoodcuttingPerks.SAVING_THE_PLANET.getId())) {
                BlockPos blockBelowPos = pos.below();

                Block saplingBlock = getSaplingFromLog(blockState.getBlock());

                if (saplingBlock != null && canSustainSapling(level, blockBelowPos, saplingBlock)) {
                    level.setBlockAndUpdate(pos, saplingBlock.defaultBlockState());

                    level.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void harvestLogsOnServerTick(ServerTickEvent.Post event) {
        if (HARVESTED_LOGS.isEmpty()) {
            HARVEST_TICK_COUNTER = 0;
            return;
        }
        HARVEST_TICK_COUNTER++;
        if (HARVEST_TICK_COUNTER % 2 == 0) {
            Iterator<BlockPos> iterator = HARVESTED_LOGS.iterator();
            BlockPos pos = iterator.next();
            ServerPlayer player = HARVESTING_PLAYER;
            double multiValue = getPlayerMultiValue(player);
            handleLootDrop(player, pos, multiValue);
            handleBlockDestruction(player.serverLevel(), pos);
            HARVESTED_LOGS.remove(pos);
        }
    }

    public static double getPlayerMultiValue(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();
        WoodcuttingSkillData woodcuttingSkillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);
        double multi = 1.0;

        if (stack.getItem() instanceof WoodcuttingAxeItem axe) {
            multi = axe.getMulti(stack);
        }

        if (woodcuttingSkillData.hasPerk(WoodcuttingPerks.GENEROUS_GROWTH.getId())) {
            int generousGrowthRank = woodcuttingSkillData.getPerkRank(WoodcuttingPerks.GENEROUS_GROWTH.getId());
            double generousGrowthValue = WoodcuttingPerks.GENEROUS_GROWTH.getEffectValue(generousGrowthRank, Double.class);
            multi += generousGrowthValue;
        }

        return multi;
    }

    public static void handleLootDrop(ServerPlayer player, BlockPos blockPos, double multi) {
        ItemStack stack = player.getMainHandItem();
        BlockState blockState = player.level().getBlockState(blockPos);
        LootParams.Builder lootParamsBuilder = new LootParams.Builder(player.serverLevel())
                .withParameter(LootContextParams.ORIGIN, blockPos.getCenter())
                .withParameter(LootContextParams.TOOL, stack)
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.BLOCK_STATE, blockState)
                .withParameter(ModLootContext.WOODCUTTING_MULTI_LOOT_PARAM, multi);

        List<ItemStack> drops = blockState.getDrops(lootParamsBuilder);

        for (ItemStack itemStack : drops) {
            player.level().addFreshEntity(new ItemEntity(
                    player.level(),
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    itemStack));
        }

        handleBlockDestruction(player.serverLevel(), blockPos);
    }

    public static void handleBlockDestruction(ServerLevel level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(blockPos)),
                blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D,
                10,
                0.2D, 0.2D, 0.2D,
                0.5D);
        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
    }

    @SubscribeEvent
    public static void destroyLogsOnServerTick(ServerTickEvent.Post event) {
        if (DESTROYABLE_LOGS.isEmpty()) {
            DESTROY_TICK_COUNTER = 0;
            return;
        }
        DESTROY_TICK_COUNTER++;
        if (DESTROY_TICK_COUNTER % 2 == 0) {
            Iterator<BlockPos> iterator = DESTROYABLE_LOGS.iterator();
            BlockPos pos = iterator.next();
            handleBlockDestruction(DESTROYED_ON_LEVEL, pos);
            DESTROYABLE_LOGS.remove(pos);
            DESTROY_TICK_COUNTER = 0;
        }
    }

    private static boolean isLogBlock(BlockState state) {
        return state.is(BlockTags.LOGS);
    }

    private static void treeFellerHandling(Level level, BlockPos startPos, int distanceToTravel) {
        Direction[] directionsToCheck = {
                Direction.UP,
                Direction.DOWN,
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
        };
        getLogsToHarvest(level, startPos, directionsToCheck, distanceToTravel);
    }

    private static void getLogsToHarvest(Level level, BlockPos startPos, Direction[] directionsToCheck, int distanceToTravel) {
        Stack<BlockPos> blocksToCheckFrom = new Stack<>();

        blocksToCheckFrom.add(startPos);

        while (!blocksToCheckFrom.isEmpty()) {
            BlockPos currentBlockToCheck = blocksToCheckFrom.pop();
            for (Direction direction : directionsToCheck) {
                BlockPos neighborBlock = currentBlockToCheck.relative(direction);
                BlockState neighborState = level.getBlockState(neighborBlock);
                if (isLogBlock(neighborState)) {
                    HARVESTED_LOGS.add(neighborBlock);
                    blocksToCheckFrom.push(neighborBlock);
                }
            }
        }
    }

    private static void destroyWholeTreeAboveWithNoLoot(Level level, BlockPos startPos) {
        Direction[] directionsToCheck = {
                Direction.UP,
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
        };
        getLogsToBeDestroyed(level, startPos, directionsToCheck);
    }

    private static void getLogsToBeDestroyed(Level level, BlockPos startPos, Direction[] directionsToCheck) {
        Stack<BlockPos> blocksToCheckFrom = new Stack<>();
        blocksToCheckFrom.add(startPos);

        while (!blocksToCheckFrom.isEmpty()) {
            BlockPos currentBlockToCheck = blocksToCheckFrom.pop();
            for (Direction direction : directionsToCheck) {
                BlockPos neighborBlock = currentBlockToCheck.relative(direction);
                BlockState neighborState = level.getBlockState(neighborBlock);
                if (neighborBlock.getY() == startPos.getY()) {
                    continue;
                }
                // This means that we are going to harvest that block, so no need to destroy it
                if (HARVESTED_LOGS.contains(neighborBlock)) {
                    continue;
                }
                if (isLogBlock(neighborState)) {
                    DESTROYABLE_LOGS.add(neighborBlock);
                    blocksToCheckFrom.push(neighborBlock);
                }
            }
        }
    }

    private static Block getSaplingFromLog(Block logBlock) {
        if (logBlock == Blocks.OAK_LOG) return Blocks.OAK_SAPLING;
        if (logBlock == Blocks.SPRUCE_LOG) return Blocks.SPRUCE_SAPLING;
        if (logBlock == Blocks.BIRCH_LOG) return Blocks.BIRCH_SAPLING;
        if (logBlock == Blocks.JUNGLE_LOG) return Blocks.JUNGLE_SAPLING;
        if (logBlock == Blocks.ACACIA_LOG) return Blocks.ACACIA_SAPLING;
        if (logBlock == Blocks.DARK_OAK_LOG) return Blocks.DARK_OAK_SAPLING;
        if (logBlock == Blocks.CHERRY_LOG) return Blocks.CHERRY_SAPLING;
        return null;
    }

    private static boolean canSustainSapling(Level level, BlockPos blockPos, Block saplingBlock) {
        BlockState saplingState = saplingBlock.defaultBlockState();
        return saplingState.canSurvive(level, blockPos);
    }
}
