package com.github.finestgit.adventurecraft.attachment;

import com.github.finestgit.adventurecraft.AdventureCraftMod;
import com.github.finestgit.adventurecraft.attachment.skills.woodcutting.WoodcuttingSkillData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.lwjgl.system.Pointer;

import java.util.function.Supplier;

public class PlayerAttachement {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AdventureCraftMod.MODID);

    public static final Supplier<AttachmentType<WoodcuttingSkillData>> WOODCUTTING_SKILL_DATA = ATTACHMENT_TYPES.register(
            "woodcutting_skill_data", () -> AttachmentType.builder(WoodcuttingSkillData.DEFAULT::get)
                    .serialize(WoodcuttingSkillData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<DefaultPlayerData>> DEFAULT_PLAYER_DATA = ATTACHMENT_TYPES.register(
            "default_player_data", () -> AttachmentType.builder(DefaultPlayerData.DEFAULT::get)
                    .serialize(DefaultPlayerData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
