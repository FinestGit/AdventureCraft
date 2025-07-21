package com.github.finestgit.adventurecraft.event;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.attachment.PlayerAttachement;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingSkillData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Collection;

@EventBusSubscriber(modid = AdventureCraftMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SkillCommandEvent {
    @SubscribeEvent
    public static void registerWoodcuttingCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("woodcutting")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("exp")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("players", EntityArgument.players())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                        .executes(context -> addWoodcuttingExp(context.getSource(),
                                                                EntityArgument.getPlayers(context, "players"),
                                                                IntegerArgumentType.getInteger(context, "amount")))))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("players", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> setWoodcuttingExp(context.getSource(),
                                                        EntityArgument.getPlayers(context, "players"),
                                                        IntegerArgumentType.getInteger(context, "amount"))))))
        );
    }

    private static int addWoodcuttingExp(CommandSourceStack source, Collection<ServerPlayer> players, int amount) throws CommandSyntaxException {
        int successfulUpdates = 0;
        for (ServerPlayer player : players) {
            WoodcuttingSkillData skillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);

            skillData.addExperience(amount);

            source.sendSuccess(() -> Component.literal("Added " + amount + " Woodcutting XP to " + player.getName().getString() + "."), true);

            successfulUpdates++;
        }
        return successfulUpdates;
    }

    private static int setWoodcuttingExp(CommandSourceStack source, Collection<ServerPlayer> players, int amount) throws CommandSyntaxException {
        int successfulUpdates = 0;
        for (ServerPlayer player : players) {
            WoodcuttingSkillData skillData = player.getData(PlayerAttachement.WOODCUTTING_SKILL_DATA);

            skillData = new WoodcuttingSkillData(amount, skillData.getSpentPerkPoints(), skillData.getPerkRanks());
            player.setData(PlayerAttachement.WOODCUTTING_SKILL_DATA, skillData);

            source.sendSuccess(() -> Component.literal("Set Woodcutting XP of " + player.getName().getString() + " to " + amount + "."), true);

            successfulUpdates++;
        }
        return successfulUpdates;
    }
}
